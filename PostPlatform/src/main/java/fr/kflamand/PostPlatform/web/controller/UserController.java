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

    @PostMapping("/create")
    public String create(@RequestBody User user ) {

        try {

            log.info("\n"+"\n"+user.toString()+"\n"+"\n");
            userDao.save(user);

        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created! (id = " + user.getId() + ")";
    }

    /**
     * GET /delete  --> Delete the user having the passed id.
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            User user = new User(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user:" + ex.toString();
        }
        return "User succesfully deleted!";
    }

    /**
     * GET /get-by-email  --> Return the id for the user having the passed
     * email.
     */
    @RequestMapping("/get-by-email")
    @ResponseBody
    public String getByEmail(String email) {
        String userId = "";
        try {
            User user = userDao.findByEmail(email);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "User not found";
        }
        return "The user id is: " + userId;
    }

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
     * GET /update  --> Update the email and the name for the user in the
     * database having the passed id.
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(long id, String email, String pseudo) {
        try {
            Optional<User> user = userDao.findById(id);
            user.get().setEmail(email);
            user.get().setPseudo(pseudo);
            userDao.save(user.get());
        }
        catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User succesfully updated!";
    }


}

