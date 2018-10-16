package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Exception.EmailExistsException;
import fr.kflamand.PostPlatform.Exception.EmailNotFoundException;
import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.persistance.Dao.RoleDao;
import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.persistance.models.User;
import fr.kflamand.PostPlatform.security.ActiveUserStore;
import fr.kflamand.PostPlatform.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
public class UserController {

    // Private fields

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    private RoleDao roleDao;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/loggedUsers", method = RequestMethod.GET)
    public String getLoggedUsers(final Locale locale, final Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }

    @RequestMapping(value = "/loggedUsersFromSessionRegistry", method = RequestMethod.GET)
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Method Perso
    // TODO Verif Double avec UserService

    // Affiche la liste de tous les Users
    @GetMapping(value = "/Users")
    public List<User> listDesUsers() {

        List<User> users = userDao.findAll();
        if (users.isEmpty()) {

            log.info("Users not found");
            //TODO Throw exception
            throw new UserNotFoundException("List des Users Vide");
        }
        return users;
    }

    //Récuperer un user par son id
    @GetMapping(value = "/Users/{id}")
    @ResponseBody
    public User getOneUserById(@PathVariable long id) {

        Optional<User> userOp = userDao.findById(id);
        if (!userOp.isPresent()) {
            throw new UserNotFoundException("Le user correspondant à l'id " + id + " n'existe pas");
        }
        User user = userOp.get();
        if (user == null) {
            log.info("User not found");
            //TODO Throw exception
            throw new UserNotFoundException("Le user correspondant à l'id " + id + " n'existe pas");
        }
        return user;
    }

    @PostMapping("/Users")
    @ResponseBody
    public void registerNewUserAccount(@RequestBody User userDto) throws EmailExistsException {

        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + userDto.getEmail());
        }

        User user = new User();

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setEmail(userDto.getEmail());
        user.setPseudo(userDto.getPseudo());

        // TODO Re-création du role
        user.setRoleUser(user.getRoleUser());

        log.info(user.toString());
        userDao.save(user);

    }


    @PatchMapping("/Users")
    @ResponseBody
    public void updateUser(@RequestBody User user) {

        log.info(user.toString());
        userDao.save(user);

    }

    @DeleteMapping("/Users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable long id) {

        User user = userDao.findUserByIdEquals(id);
        userDao.delete(user);

    }


    private boolean emailExist(String email) {
        String existingMail = userDao.findByEmail(email).getEmail();
        if (email != existingMail) {
            return false;
        } else if (email == existingMail) {
            return true;
        } else {
            throw new EmailNotFoundException("mail fail recherche: UserController.emailExist");
        }
    }
}

