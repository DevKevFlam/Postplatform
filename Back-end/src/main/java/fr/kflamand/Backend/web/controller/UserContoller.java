package fr.kflamand.Backend.web.controller;

import fr.kflamand.Backend.Exceptions.UserTokenNotFound;
import fr.kflamand.Backend.entities.User;
import fr.kflamand.Backend.services.UserServiceInterface;
import fr.kflamand.Backend.web.exception.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserContoller {

    @Autowired
    private UserServiceInterface userService;

    @CrossOrigin
    @PostMapping("/getUser")
    public ResponseEntity<?> getUserFromToken(@RequestBody String token) {
        try {
            User user = userService.findUserWithToken(token);
            return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
        } catch (UserTokenNotFound e) {
            return new ResponseEntity(new CustomErrorType("User not Found"),HttpStatus.NOT_FOUND);
        }
    }

}
