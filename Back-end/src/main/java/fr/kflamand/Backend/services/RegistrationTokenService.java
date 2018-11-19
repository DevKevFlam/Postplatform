package fr.kflamand.Backend.services;

import fr.kflamand.Backend.Exceptions.UserTokenNotFound;
import fr.kflamand.Backend.dao.RegistrationTokenRepository;
import fr.kflamand.Backend.dao.UserRepository;
import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Locale;

public class RegistrationTokenService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationTokenRepository registrationTokenDao;

    public RegistrationToken createNewRegistrationToken(User user, Locale locale) {

        RegistrationToken newRegistrationToken = new RegistrationToken();

        newRegistrationToken.setUser(user);
        Calendar expireDate = Calendar.getInstance(locale);
        expireDate.add(Calendar.DATE, 1);
        newRegistrationToken.setExpire(expireDate);
        newRegistrationToken.setToken(RandomString.generateRandomString(32));

        return newRegistrationToken;
    }

    public RegistrationToken createPasswordResetTokenForUser(String username, Locale locale) {

        //RegistrationToken newRegistrationToken = new RegistrationToken();
        RegistrationToken newRegistrationToken = registrationTokenDao.findOne(userService.find(username).getId());
        ////
        //Expiration date
        Calendar expireDate = Calendar.getInstance(locale);
        expireDate.add(Calendar.DATE, 1);
        newRegistrationToken.setExpire(expireDate);
        ////
        //Création du token
        newRegistrationToken.setToken(RandomString.generateRandomString(32));
        //Liaison au User
        //newRegistrationToken.setUser(userService.find(username));

        this.saveTokenForResetPassword(newRegistrationToken);
        return newRegistrationToken;
    }


    public User enableUser(String token) {

        RegistrationToken userRT = registrationTokenDao.findByToken(token);
        if (userRT == null) {
            throw new UserTokenNotFound("Token Introuvable");
        } else {

            User userAMod = userRT.getUser();

            if (userAMod == null) {
                throw new UserTokenNotFound("Empty User");
            } else {
                // Modif de Enabled
                userAMod.setEnabled(true);
                //User userSave = userRepository.save(userAMod);
                userRT.setToken(null);
                userRT.setExpire(null);
                User userSave = registrationTokenDao.save(userRT).getUser();

                return userSave;
            }
        }

    }

    public User resetPassword(String token, User user) {

        RegistrationToken userRT = registrationTokenDao.findByToken(token);

        if (userRT == null) {
            throw new UserTokenNotFound("Token Introuvable");
        } else {
            User userAMod = userRT.getUser();

            if (userAMod == null) {
                throw new UserTokenNotFound("Empty User");
            } else {
                userAMod.setPassword(user.getPassword());

                User userSave = userService.changePassword(userAMod);
                registrationTokenDao.delete(userRT.getId());
                return userSave;
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public RegistrationToken saveTokenForResetPassword(RegistrationToken token) {
        return registrationTokenDao.save(token);

    }
}
