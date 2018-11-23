package fr.kflamand.Backend.services;

import fr.kflamand.Backend.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Locale;

public interface UserServiceInterface extends UserDetailsService {

    // Get a User
        // By Username
    User findByUsername(String username);
        // By Id
    User findById(Long id);
        // By Token
    User findUserWithToken(String token);

    // Create a User and signup
    User register(User newUser);

    // Modify User
    User updateUser(User user);

    // Delete User
    Boolean deleteUser (User user);

    // Enable User Acount
    User enableUser(String userToken);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Get the User create a token and send mail
    User getMailForResetPasswordUser(String username, Locale locale);

    // Get token Avec new password et change les pass en base
    User resetPasswordUser(String token , User user);

}
