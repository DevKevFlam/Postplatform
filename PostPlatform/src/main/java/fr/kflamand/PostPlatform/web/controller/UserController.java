package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Exception.AuthException;
import fr.kflamand.PostPlatform.Exception.EmailNotFoundException;
import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.persistance.Dao.RoleDao;
import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.persistance.models.User;
import fr.kflamand.PostPlatform.security.ActiveUserStore;
import fr.kflamand.PostPlatform.security.LoggedUser;
import fr.kflamand.PostPlatform.security.MyUserDetailsService;
import fr.kflamand.PostPlatform.services.UserService;
import fr.kflamand.PostPlatform.web.dto.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    MyUserDetailsService detailsService;

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
    public Boolean SignIn(@RequestBody LoginForm loginForm, HttpServletRequest request,
                                 HttpServletResponse response) throws AuthenticationException {

        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        //Verification de l'existance du compte par le mail
        if (!this.emailExist(email)) {
            throw new EmailNotFoundException("Le compte n'existe pas!");
        } else {

            //Reconstruction du User
            User user = userService.findUserByEmail(email);

            UserDetails detailsUser = detailsService.loadUserByUsername(user.getEmail());

            //TODO Verif creation Authentification
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSecret(), detailsUser.getAuthorities() );

            //Verification du password
            if (!passwordEncoder.matches(password, user.getPassword())) {

                //TODO bad Auth SignIN EVENT
                 eventPublisher.publishEvent(new AuthenticationFailureBadCredentialsEvent(authentication , new AuthException("Wrong passWord!!!")));
                System.out.println("////////////////////////////  AUTH FAIL  ////////////////////////////");
                return false;

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
                    session.setAttribute("Token", new LoggedUser(activeToken, activeUserStore));
                }

                response.setContentType("application/json");

                //TODO Good Auth SignIN EVENT
                eventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));

                session.removeAttribute("Token");


                return true;
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
        return userDao.findByEmail(email) != null;
    }
}

