package fr.kflamand.Backend.services;

import fr.kflamand.Backend.dao.UserRepository;
import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.entities.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PrincipalUserService implements UserDetailsService {

    public static final Logger logger = LoggerFactory.getLogger(PrincipalUserService.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.find(username);
        UserPrincipal userDetails = new UserPrincipal(user);
        return  userDetails;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public User register(User user) {

        User newUser = userService.register(user);

        mailService.sendSimpleMessage(newUser.getUsername(), mailService.subjectRegistrationMail(newUser), mailService.messageRegistrationMail(newUser));

        return newUser;
    }

    public User enableUser(String userToken) {

        User user = registrationTokenService.enableUser(userToken);

        return user;

    }

    public User resetPasswordUser(String token , User user) {

        User userToSave = registrationTokenService.resetPassword(token,user);
        return userToSave;
    }

    public User getMailForResetPasswordUser(String username , Locale locale) {

        logger.info("USERNAME -----"+username+"-----");
        logger.info("----------------------------------------------------------------------------------------------");
        if (this.loadUserByUsername(username).getUser() != null) {logger.info(this.loadUserByUsername(username).getUser().toString());}
        else {logger.error("Impossible de loadUserByUsername from userPrincipal");}

        if (userRepository.findByUsername(username) != null) {logger.info(userRepository.findByUsername(username).toString());}
        else {logger.error("Impossible de find By username from DAO");}

        if (userService.find(username) != null) {logger.info(userService.find(username).toString());}
        else {logger.error("Impossible de find username from user service");}
        logger.info("----------------------------------------------------------------------------------------------");



        // Création du token
        RegistrationToken token = registrationTokenService.createPasswordResetTokenForUser(username , locale);

        // Envoi du mail
        mailService.sendSimpleMessage( token.getUser().getUsername(),mailService.subjectResetPassword( token.getUser()),mailService.messageResetPassword( token.getUser()));
        return  token.getUser();
    }

}
