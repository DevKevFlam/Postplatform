package fr.kflamand.Authserver.Service;

import fr.kflamand.Authserver.DAO.UserDAO;
import fr.kflamand.Authserver.models.CustomUser;
import fr.kflamand.Authserver.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userDAO.getUserDetails(username);
        CustomUser customUser = new CustomUser(userModel);
        return customUser;
    }

}