package fr.kflamand.Authserver.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;

public class UserModel {

    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String fullName;

    // Boolean de validation du compte sert a bloqué le login si false
    private Boolean Enabled;



    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
        return grantedAuthoritiesList;
    }

    public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
        this.grantedAuthoritiesList = grantedAuthoritiesList;
    }
}
