package fr.kflamand.Backend.services;

import fr.kflamand.Backend.dao.RegistrationTokenRepository;
import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrationTokenService {

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

}
