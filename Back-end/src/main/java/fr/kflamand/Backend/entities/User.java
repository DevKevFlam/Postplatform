package fr.kflamand.Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "User")
public class User  {


    //public static enum Role {USER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;

    private String fullName;

    // Boolean de validation du compte sert a bloqué le login si false
    private Boolean Enabled;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private RegistrationToken registrationToken;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ROLE
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // USERNAME
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    // PASSWORD
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // FULLNAME
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // ENABLED
    public void setEnabled(Boolean verified) {
        Enabled = verified;
    }

    public Boolean getEnabled() {
       return this.Enabled;
    }

    // TOKEN
    public RegistrationToken getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(RegistrationToken registrationToken) {
        this.registrationToken = registrationToken;
    }

    // ID
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
