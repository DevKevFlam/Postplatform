package fr.kflamand.Authserver.models;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    public CustomUser(UserModel userModel) {
        super(userModel.getUsername(), userModel.getPassword(), userModel.getGrantedAuthoritiesList());
    }

}
