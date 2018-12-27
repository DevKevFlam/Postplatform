package fr.kflamand.Backend.services;

import fr.kflamand.Backend.Exceptions.TokenExpiredException;
import fr.kflamand.Backend.Exceptions.UserTokenNotFound;
import fr.kflamand.Backend.dao.RegistrationTokenRepository;
import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.util.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Locale;

public class RegistrationTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationTokenService.class);

    //SERVICE
    @Autowired
    private UserServiceInterface userService;

    // REPOSITORY
    @Autowired
    private RegistrationTokenRepository registrationTokenDao;

    //UTIL
    @Autowired
    private PasswordEncoder passwordEncoder;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public RegistrationToken createNewRegistrationToken(User user, Locale locale) {

        RegistrationToken newRegistrationToken = new RegistrationToken();

        newRegistrationToken.setUser(user);
        Calendar expireDate = Calendar.getInstance(locale);
        expireDate.add(Calendar.HOUR, 12);
        newRegistrationToken.setExpire(expireDate);
        newRegistrationToken.setToken(RandomString.generateRandomString(32));

        return newRegistrationToken;
    }

    public RegistrationToken createPasswordResetTokenForUser(User user, Locale locale) {

        //RegistrationToken newRegistrationToken = new RegistrationToken();
        RegistrationToken newRegistrationToken = registrationTokenDao.findOne(user.getUserId());
        ////
        //Expiration date
        Calendar expireDate = Calendar.getInstance(locale);
        expireDate.add(Calendar.HOUR, 12);
        newRegistrationToken.setExpire(expireDate);
        ////
        //Création du token
        newRegistrationToken.setToken(RandomString.generateRandomString(32));
        //Liaison au User
        newRegistrationToken.setUser(user);

        this.saveRegistrationToken(newRegistrationToken);
        return newRegistrationToken;
    }

    public User enableUser(String token) {

        RegistrationToken userRT = registrationTokenDao.findByToken(token);
        if (userRT == null) {
            logger.error("Token Introuvable");
            throw new UserTokenNotFound("Token Introuvable");
        } else if ( this.isExpired(userRT) ) {
            logger.error("Token expirer");
            throw new TokenExpiredException("Token expirer");
        } else {

            User userAMod = userRT.getUser();

            if (userAMod == null) {
                logger.error("Empty User");
                throw new UserTokenNotFound("Empty User");
            } else {
                // Modif de Enabled
                userAMod.setEnabled(true);
                // Destruction du token
                this.destroyToken(userRT);
                // sauvegarde User
                return userService.updateUser(userAMod);
            }
        }

    }

    public User resetPassword(String token, User user) {

        RegistrationToken userRT = registrationTokenDao.findByToken(token);

        if (userRT == null) {
            logger.error("Token Introuvable");
            throw new UserTokenNotFound("Token Introuvable");
        } else if ( this.isExpired(userRT) ) {
            logger.error("Token expirer");
            throw new TokenExpiredException("Token expirer");
        } else {

            User userAMod = userRT.getUser();

            if (userAMod == null) {
                logger.error("Empty User");
                throw new UserTokenNotFound("Empty User");
            } else {
                userAMod.setPassword(passwordEncoder.encode(user.getPassword()));
                // Destruction du token
                this.destroyToken(userRT);
                // sauvegarde User
                return userService.updateUser(userAMod);
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public RegistrationToken saveRegistrationToken(RegistrationToken token) {
        return registrationTokenDao.save(token);

    }

    public RegistrationToken getRegistrationTokenFromToken (String token) {
        return registrationTokenDao.findByToken(token);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private RegistrationToken destroyToken(RegistrationToken token) {
        token.setToken(null);
        token.setExpire(null);
        return registrationTokenDao.save(token);
    }

    private Boolean isExpired(RegistrationToken token) {
        return token.getExpire().before(Calendar.getInstance());
    }
}
