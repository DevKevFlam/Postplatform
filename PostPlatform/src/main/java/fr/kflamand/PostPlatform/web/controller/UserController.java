package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Dao.UserDao;
import fr.kflamand.PostPlatform.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    // Private fields

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    /**
     * Envoi List User
     * @return
     */
    @GetMapping("/get-all")
    @ResponseBody
    public List<User>getAllUser( ) {
        List<User> users ;
        users = userDao.findAll();
        if(users.isEmpty()) {

            log.info("Users not found");
            //TODO Throw exception
        }
        return users;
    }

    /**
     * User.id = id
     * @param id
     * @return
     */
    @GetMapping("/get-one/{id}")
    @ResponseBody
    public User getOneUserById(@PathVariable long id ) {
        User user ;
       Optional<User> userOp = userDao.findById(id);
       user = userOp.get();
       log.info(user.toString());
        if(user == null ) {

            log.info("User not found");
            //TODO Throw exception
        }
        return user;
    }

    @PostMapping("/add-one")
    public void addUser( @RequestBody User user ) {
log.info(user.toString());
        userDao.save(user);

    }




}

