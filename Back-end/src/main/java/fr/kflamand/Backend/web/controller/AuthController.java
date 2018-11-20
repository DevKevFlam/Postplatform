package fr.kflamand.Backend.web.controller;

import fr.kflamand.Backend.Exceptions.UserAlreadyExistException;
import fr.kflamand.Backend.Exceptions.UserTokenNotFound;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.services.UserServiceInterface;
import fr.kflamand.Backend.web.exception.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Locale;

@RestController
@RequestMapping("auth")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserServiceInterface userService;

    // SIGN UP
    // request method to create a new account by a guest
    @CrossOrigin
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        logger.info("user register " + newUser);
        try {

            User user = userService.register(newUser);

            return new ResponseEntity<User>(user, HttpStatus.CREATED);

        } catch (UserAlreadyExistException e) {
            logger.error("username Already exist " + newUser.getUsername());
            return new ResponseEntity(new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
                    HttpStatus.CONFLICT);
        }

    }

    // SIGN IN
    // this is the login api/service
    @CrossOrigin
    @GetMapping("/login")
    public Principal user(Principal principal) {

        logger.info("user logged " + principal);

        logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return principal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Enable Account after mail verification
    @CrossOrigin
    @GetMapping("/Enabled/{Token}")
    public ResponseEntity<?> enableUser(@PathVariable("Token") String token) {

        try {

            User user = userService.enableUser(token);

            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);

        } catch (UserTokenNotFound e) {
            logger.error("User TOken not Found //////////////  " + e.getMessage());
            return new ResponseEntity(new CustomErrorType("User TOken not Found"),
                    HttpStatus.NOT_FOUND);
        }

    }


    //Send a mail for reseting the user's password
    @CrossOrigin
    @PostMapping("/ResetPassword/User")
    public ResponseEntity<?> getMailForResetPasswordUser(@RequestBody String username, Locale locale) {

        try {
            logger.info("Debut de la création du mail+token pour ---" + username + "----");
            User user = userService.getMailForResetPasswordUser(username, locale);

            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        } catch (UserTokenNotFound e) {
            return new ResponseEntity(new CustomErrorType("User not Found"),
                    HttpStatus.NOT_FOUND);
        }

    }


    // Reset password form
    @CrossOrigin
    @PostMapping("/ResetPassword/{Token}")
    public ResponseEntity<?> resetPasswordUser( @RequestBody User userForm, @PathVariable("Token") String token) {

        try {
            User user = userService.resetPasswordUser(token, userForm);
            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        } catch (UserTokenNotFound e) {
            logger.error("User TOken not Found //////////////  " + e.getMessage());
            return new ResponseEntity(new CustomErrorType("User TOken not Found"),
                    HttpStatus.NOT_FOUND);
        }

    }

}
