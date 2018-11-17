package fr.kflamand.Backend.services;

import fr.kflamand.Backend.Exceptions.UserAlreadyExistException;
import fr.kflamand.Backend.dao.RegistrationTokenRepository;
import fr.kflamand.Backend.dao.UserRepository;
import fr.kflamand.Backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService implements UserDetailsService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        return  user;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationTokenRepository registrationTokenDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    public User register(User user) throws UserAlreadyExistException {
        User userToSave = null;
        if (emailExist(user.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + user.getUsername());
        } else {
            Locale locale = LocaleContextHolder.getLocale();
            userToSave = new User();
            userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
            userToSave.setFullName(user.getFullName());
            userToSave.setRole("ROLE_USER");
            userToSave.setEnabled(false);
            userToSave.setUsername(user.getUsername());

            userToSave.setRegistrationToken(registrationTokenService.createNewRegistrationToken(userToSave, locale));

        }

        // TODO Re-création du role + Authorities
        //user.setRoleUser(roleDao.findByName("USER"));

        return registrationTokenDao.saveAndFlush(userToSave.getRegistrationToken()).getUser();
    }

    public User changePassword(User user)  {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User userSave = userRepository.save(user);
            return userSave;
    }

    ///////////////////////////////////////////////////////////////////
    // UTIL
    private boolean emailExist(final String email) {
        return userRepository.findByUsername(email) != null;
    }

    ///////////////////////////////////////////////////////////////////
    public User update(User user) {
        return userRepository.save(user);
    }

    public User find(String userName) {
        logger.debug(userName);

        logger.debug(userRepository.findUserByUsername(userName).toString());

        return userRepository.findByUsername(userName);
    }

    public User findUserWithToken(String token) {
        return userRepository.findOne(registrationTokenDao.findByToken(token).getId());
    }

    public User find(Long id) {
        return userRepository.findOne(id);
    }
}