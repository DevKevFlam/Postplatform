package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Exception.EmailNotFoundException;
import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.persistance.Dao.RoleDao;
import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.persistance.models.User;
import fr.kflamand.PostPlatform.security.ActiveUserStore;
import fr.kflamand.PostPlatform.security.LoggedUser;
import fr.kflamand.PostPlatform.services.UserService;
import fr.kflamand.PostPlatform.web.dto.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@CrossOrigin/*("http://localhost:4200")*/
@RestController
public class UserController {

    // Private fields

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    UserService userService;

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

    @RequestMapping(value = "/loggedUsersFromSessionRegistry", method = RequestMethod.GET)
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SIGNIN //OK
    @PostMapping(value = "/user/signIn")
    public void SignIn(@RequestBody LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
/*
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword(password);
*/
        //Verification de l'existance du compte par le mail
        if (!userService.emailExist(email)) {
            throw new EmailNotFoundException("Le compte n'existe pas!");
        } else {

            //Reconstruction du User
            User user = userService.findUserByEmail(email);

            //Verification du password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                //TODO event bad SignIN
                // eventPublisher.publishEvent(new AuthenticationFailureBadCredentialsEvent());
                System.out.println("////////////////////////////  AUTH FAIL  ////////////////////////////");


            } else {
                System.out.println("////////////////////////////  AUTH OK!!!  ////////////////////////////");

                // Get the current session object, create one if necessary
                HttpSession session = request.getSession(false);
                if (session == null) {
                    System.out.println("-- creating new session in the servlet --");
                    session = request.getSession(true);
                    System.out.println("-- session created in the servlet --");
                }

                String activeToken = (String) session.getAttribute("Token");
                if (activeToken == null || activeToken == "") {
                    activeToken = user.getSecret();
                    session.setAttribute("Token", new LoggedUser(user.getSecret(), activeUserStore));
                }
                response.setContentType("application/json");
                session.removeAttribute("name");

                //TODO Good Auth EVENT
            }
        }
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

