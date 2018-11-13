package fr.kflamand.Backend.web.controller;

import fr.kflamand.Backend.Exceptions.UserAlreadyExistException;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.services.UserService;
import fr.kflamand.Backend.web.exception.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("auth")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    // request method to create a new account by a guest
    @CrossOrigin
    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {/*
        if (userService.find(newUser.getUsername()) != null) {
            logger.error("username Already exist " + newUser.getUsername());
            return new ResponseEntity(
                    new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
                    HttpStatus.CONFLICT);
        }*/

        logger.info("user register "+ newUser);
        try {
            return new ResponseEntity<User>(userService.register(newUser), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            logger.error("username Already exist " + newUser.getUsername());
            return new ResponseEntity( new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
                    HttpStatus.CONFLICT);
        }

    }


    // this is the login api/service
    @CrossOrigin
    @GetMapping("/login")
    public Principal user(Principal principal) {

        logger.info("user logged "+ principal);

        logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return principal;
    }


}
