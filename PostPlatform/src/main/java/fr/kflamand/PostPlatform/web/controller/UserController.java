package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Exception.EmailNotFoundException;
import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.persistance.Dao.RoleDao;
import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.persistance.models.User;
import fr.kflamand.PostPlatform.security.ActiveUserStore;
import fr.kflamand.PostPlatform.security.LoggedUser;
import fr.kflamand.PostPlatform.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
public class UserController {

    // Private fields

    Logger log = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    //private ApplicationEventPublisher eventPublisher;

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
    public List<String> getLoggedUsers(final Locale locale, final Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        //return "users";
        return activeUserStore.getUsers();
    }
/*
    @RequestMapping(value = "/loggedUsersFromSessionRegistry", method = RequestMethod.GET)
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }*/

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(value = "/user/signIn")
    @ResponseBody
    public String SignIn(@RequestHeader final HttpServletRequest request, final HttpHeaders header) {

        HttpSession session = request.getSession();

        String emailS = session.getAttribute("email").toString();
        String passwordS = session.getAttribute("password").toString();
        log.debug("//////////// Header test SESSION email--" + emailS + "--  MDP --" + passwordS + "--");
        System.out.println("//////////// Header test SESSION email--" + emailS + "--  MDP --" + passwordS + "--");

        String email = request.getHeader("email");
        String password = request.getHeader("password");
        log.debug("//////////// Header test ReqHead email--" + email + "--  MDP --" + password + "--");
        System.out.println("//////////// Header test ReqHead email--" + email + "--  MDP --" + password + "--");
/*
        String emailH = header.getValuesAsList().("email");
        String passwordH = request.getHeader("password");
        log.debug("//////////// Header test HTTPHead email--" + emailH + "--  MDP --" + passwordH + "--");
        */
        /*
        User user = userService.findUserByEmail(email);

        if (user.getEmail() != email) {
            //TODO throw exception

        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            //TODO throw new SignInException();
            System.out.println("////////////////////////////  AUTH FAIL  ////////////////////////////");
        } else {
            System.out.println("////////////////////////////  AUTH OK!!!  ////////////////////////////");
            LoggedUser userLog = new LoggedUser(user.getEmail(), activeUserStore);
            userLog.valueBound(new HttpSessionBindingEvent(session, user.getEmail()));
            // eventPublisher.publishEvent();
            // TODO Renvois du token de co
            // HttpServletResponse
        }
        return activeUserStore.toString();*/
        return "";
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


    //Récuperer un user par son id
    @GetMapping(value = "/Users/{email}")
    @ResponseBody
    public User getOneUserByEmail(@PathVariable String email) {

        User userOp = userDao.findByEmail(email);

        if (userOp == null) {
            log.info("User not found");
            //TODO Throw exception
            throw new UserNotFoundException("Le user correspondant à l'email " + email + " n'existe pas");
        }

        if (userOp.getEmail() == email && (userOp.getPseudo() == "" || userOp.getPseudo() == null)) {
            throw new UserNotFoundException("Le user correspondant à l'email" + email + " n'existe pas");
        }

        return userOp;
    }
/*
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
*/

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

