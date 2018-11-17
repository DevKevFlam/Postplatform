package fr.kflamand.Backend.web.controller;

import fr.kflamand.Backend.Exceptions.UserAlreadyExistException;
import fr.kflamand.Backend.Exceptions.UserTokenNotFound;
import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.services.MailService;
import fr.kflamand.Backend.services.RegistrationTokenService;
import fr.kflamand.Backend.services.UserService;
import fr.kflamand.Backend.web.exception.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    // request method to create a new account by a guest
    @CrossOrigin
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        logger.info("user register " + newUser);
        try {
            User user = userService.register(newUser);

           mailService.sendSimpleMessage(newUser.getUsername(), mailService.subjectRegistrationMail(user), mailService.messageRegistrationMail(user));

            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            logger.error("username Already exist " + newUser.getUsername());
            return new ResponseEntity(new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
                    HttpStatus.CONFLICT);
        }

    }


    // this is the login api/service
    @CrossOrigin
    @GetMapping("/login")
    public Principal user(Principal principal) {

        logger.info("user logged " + principal);

        logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return principal;
    }

    // Enable Account after mail verification
    @CrossOrigin
    @GetMapping("/Enabled/{Token}")
    public ResponseEntity<?> enableUser( @PathVariable("Token") String token) {

        try {

            User user = registrationTokenService.enableUser(token);

            return new ResponseEntity<User>( user , HttpStatus.ACCEPTED);
        } catch (UserTokenNotFound e) {
            logger.error("User TOken not Found //////////////  " + e.getMessage());
            return new ResponseEntity(new CustomErrorType("User TOken not Found"),
                    HttpStatus.NOT_FOUND);
        }

    }

    // Reset password form
    @CrossOrigin
    @PostMapping("/ResetPassword/{Token}")
    public ResponseEntity<?> resetPasswordUser( @PathVariable("Token") String token ,User userForm) {

        try {
            User user = registrationTokenService.resetPassword(token,userForm);
            return new ResponseEntity<User>( user , HttpStatus.ACCEPTED);
        } catch (UserTokenNotFound e) {
            logger.error("User TOken not Found //////////////  " + e.getMessage());
            return new ResponseEntity(new CustomErrorType("User TOken not Found"),
                    HttpStatus.NOT_FOUND);
        }

    }

    //Send a mail for reseting the user's password
    @CrossOrigin
    @GetMapping("/ResetPassword/{username}")
    public ResponseEntity<?> getMailForResetPasswordUser( @PathVariable("username") String username, Locale locale ) {

        try {
            System.out.println(username);
            logger.debug(username);
            User user = userService.find(username);
            System.out.println(user.toString());
            RegistrationToken token = registrationTokenService.createNewRegistrationToken(user , locale);

            System.out.println(token.toString());
            user.setRegistrationToken(token);


            registrationTokenService.saveTokenForResetPassword(user.getRegistrationToken());
            mailService.sendSimpleMessage(user.getUsername(),mailService.subjectResetPassword(user),mailService.messageResetPassword(user));
            return new ResponseEntity<User>( user , HttpStatus.ACCEPTED);
        } catch (UserTokenNotFound e) {
            return new ResponseEntity(new CustomErrorType("User not Found"),
                    HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin
    @PostMapping("/getUser")
    public ResponseEntity<?> getUserFromToken(@RequestBody String token){
      try{
       User user = userService.findUserWithToken(token);
        return new ResponseEntity<User>( user , HttpStatus.ACCEPTED);
    } catch (UserTokenNotFound e) {
        return new ResponseEntity(new CustomErrorType("User not Found"),
                HttpStatus.NOT_FOUND);
    }
    }
}
