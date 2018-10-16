package fr.kflamand.PostPlatform.services;

import fr.kflamand.PostPlatform.Exception.UserAlreadyExistException;
import fr.kflamand.PostPlatform.persistance.Dao.PasswordResetTokenDao;
import fr.kflamand.PostPlatform.persistance.Dao.RoleDao;
import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.persistance.Dao.VerificationTokenDao;
import fr.kflamand.PostPlatform.persistance.models.PasswordResetToken;
import fr.kflamand.PostPlatform.persistance.models.RoleUser;
import fr.kflamand.PostPlatform.persistance.models.User;
import fr.kflamand.PostPlatform.persistance.models.VerificationToken;
import fr.kflamand.PostPlatform.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VerificationTokenDao tokenDao;

    @Autowired
    private PasswordResetTokenDao passwordTokenDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    public static final String TOKEN_INVALID = "Token invalide";
    public static final String TOKEN_EXPIRED = "Token expiré";
    public static final String TOKEN_VALID = "Token Valide";

    /*
    //QR CODE
    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";
    */

    // API

    @Override
    public User registerNewUserAccount(final UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        //Double Auth
        //user.setUsing2FA(accountDto.isUsing2FA());


        // TODO Re-création du role
        user.setRoleUser(/*roleDao.findByName("ROLE_USER")*/ new RoleUser("USER"));

        return userDao.save(user);
    }

    @Override
    public User getUser(final String verifiToken) {
        final VerificationToken token = tokenDao.findByToken(verifiToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public User findUserByEmail(final String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenDao.findByToken(token)
                .getUser();
    }

    @Override
    public Optional<User> getUserByID(final long id) {
        return userDao.findById(id);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        userDao.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        final VerificationToken verificationToken = tokenDao.findByUser(user);

        if (verificationToken != null) {
            tokenDao.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenDao.findByUser(user);

        if (passwordToken != null) {
            passwordTokenDao.delete(passwordToken);
        }

        userDao.delete(user);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals()
                .stream()
                .filter((u) -> !sessionRegistry.getAllSessions(u, false)
                        .isEmpty())
                .map(o -> {
                    if (o instanceof User) {
                        return ((User) o).getEmail();
                    } else {
                        return o.toString();
                    }
                })
                .collect(Collectors.toList());

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Test Methods
    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    private boolean emailExist(final String email) {
        return userDao.findByEmail(email) != null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tokens Methods

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenDao.save(myToken);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenDao.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken verifToken = tokenDao.findByToken(existingVerificationToken);
        verifToken.updateToken(UUID.randomUUID()
                .toString());
        verifToken = tokenDao.save(verifToken);
        return verifToken;
    }

    @Override
    public VerificationToken getVerificationToken(final String verifiToken) {
        return tokenDao.findByToken(verifiToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenDao.findByToken(token);
    }

    @Override
    public String validateVerificationToken(String token) {

        //TOKEN INVALIDE
        final VerificationToken verificationToken = tokenDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        //TOKEN EXPIRE
        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            tokenDao.delete(verificationToken);
            return TOKEN_EXPIRED;
        }


        //TOKEN VALID
        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userDao.save(user);
        return TOKEN_VALID;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options d'Auth
    /*
    //QR CODE
    @Override
    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
    }
    */
    /*
    // Double Identification
    @Override
    public User updateUser2FA(boolean use2FA) {
        final Authentication curAuth = SecurityContextHolder.getContext()
                .getAuthentication();
        User currentUser = (User) curAuth.getPrincipal();
        currentUser.setUsing2FA(use2FA);
        currentUser = userDao.save(currentUser);
        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
        SecurityContextHolder.getContext()
                .setAuthentication(auth);
        return currentUser;
    }
    */

}
