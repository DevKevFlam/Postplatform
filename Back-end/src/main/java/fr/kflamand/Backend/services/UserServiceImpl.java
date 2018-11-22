package fr.kflamand.Backend.services;

import fr.kflamand.Backend.Exceptions.UserAlreadyExistException;
import fr.kflamand.Backend.dao.RoleRepository;
import fr.kflamand.Backend.dao.UserRepository;
import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserServiceImpl implements UserServiceInterface {

    // REPO
    @Autowired
    private UserRepository userDao;

    // TODO RoleService
    @Autowired
    private RoleRepository roleDao;

    // SERVICES
    @Autowired
    private RegistrationTokenService registrationTokenService;
    @Autowired
    private MailService mailService;

    //UTIL
    @Autowired
    private PasswordEncoder passwordEncoder;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // OVERRIDE METHODS UserServiceInterface
    // CRUD
    //FIND
    @Override
    public User findByUsername(String username) {
        logger.info("On vas chercher: ---" + username + "---");
        User user = userDao.findByUsername(username);
        if (user == null) {
            logger.error("User = NULL !!!! Pas de Récup en base ");
        } else {
            logger.info("On a trouvé:" + user.toString());
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        return userDao.findOne(id);
    }

    @Override
    public User findUserWithToken(String token) {
        return userDao.findOne(registrationTokenService.getRegistrationTokenFromToken(token).getId());
    }
    /////////////////////////////////////////////////
    // MODIFY
    @Override
    public User update(User user) {
        return userDao.save(user);
    }
    /////////////////////////////////////////////////
    //SIGN UP // ADD
    @Override
    public User register(User newUser) throws UserAlreadyExistException {
        User userToSave = null;
        if (emailExist(newUser.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + newUser.getUsername());
        } else {

            Locale locale = LocaleContextHolder.getLocale();
            userToSave = new User();
            userToSave.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userToSave.setFullName(newUser.getFullName());
            userToSave.setRole(roleDao.findByName("ROLE_USER"));

            userToSave.setEnabled(false);
            userToSave.setUsername(newUser.getUsername());

            userToSave.setRegistrationToken(registrationTokenService.createNewRegistrationToken(userToSave, locale));

        }

        userDao.save(userToSave);

        registrationTokenService.saveRegistrationToken(userToSave.getRegistrationToken());

        mailService.sendSimpleMessage(userToSave.getUsername(), mailService.subjectRegistrationMail(userToSave), mailService.messageRegistrationMail(userToSave));

        return userToSave;
    }
    /////////////////////////////////////////////////
    //TODO ? DELETE

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SERVICE METHODS
    // Verify mail
    @Override
    public User enableUser(String userToken) {

        User user = registrationTokenService.enableUser(userToken);

        return user;

    }
    /////////////////////////////////////////////////
    // Forgotten passWord
    @Override
    public User getMailForResetPasswordUser(String username, Locale locale) {

        User user = this.findByUsername(username);

        logger.info("--------RESET PASSWORD MAIL----------- USERNAME -----" + username + "--------------------------");

        if (user == null) {

            logger.error("Impossible de find username from user service");
            throw new UsernameNotFoundException("User : " + username + " introuvable");

        } else {
            logger.info("FIND!!!   ----" + this.findByUsername(username).toString());

            // Création du token
            RegistrationToken token = registrationTokenService.createPasswordResetTokenForUser(user, locale);
            // Envoi du mail
            mailService.sendSimpleMessage(user.getUsername(), mailService.subjectResetPassword(user), mailService.messageResetPassword(user, token));
        }
        logger.info("----------------------------------------------------------------------------------------------");
        return user;
    }

    @Override
    public User resetPasswordUser(String token, User user) {

        User userToSave = registrationTokenService.resetPassword(token, user);
        return userToSave;
    }
    /////////////////////////////////////////////////
    // OVERRIDE METHODS UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Load user from the database (throw exception if not found)
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UTIL
    private boolean emailExist(final String email) {
        return userDao.findByUsername(email) != null;
    }

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
}
