package fr.kflamand.Backend.services;

import fr.kflamand.Backend.Exceptions.UserAlreadyExistException;
import fr.kflamand.Backend.dao.UserRepository;
import fr.kflamand.Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User register(User user) throws UserAlreadyExistException{
        User userToSave = null;
        if (emailExist(user.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + user.getUsername());
        } else {
            userToSave = new User();

            userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
            userToSave.setFullName(user.getFullName());
            userToSave.setRole("ROLE_USER");
            userToSave.setUsername(user.getUsername());
        }

        // TODO Re-création du role + Authorities
        //user.setRoleUser(roleDao.findByName("USER"));

        return userRepository.saveAndFlush(userToSave);
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
        return userRepository.findOneByUsername(userName);
    }

    public User find(Long id) {
        return userRepository.findOne(id);
    }
}