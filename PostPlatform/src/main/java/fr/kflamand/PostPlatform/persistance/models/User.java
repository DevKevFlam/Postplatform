package fr.kflamand.PostPlatform.persistance.models;

import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;


    private String password;

    @NotNull
    private String email;

    @NotNull
    private String pseudo;

    @ManyToOne
    private RoleUser roleUser;

    @OneToMany(mappedBy = "poster")
    private Collection<Post> posts;

    ///////////////////////////////////////////////////////////////////////
    //AUTH Attribute

    private boolean enabled;

    private boolean isUsing2FA;

    private String secret;

    ///////////////////////////////////////////////////////////////////////
    //Constructors

    public User() {
        super();
        this.secret = Base32.random();
        this.enabled = false;
    }

    public User(String email, String pseudo, String mdp) {
        this.email = email;
        this.pseudo = pseudo;
        this.password = mdp;
        this.secret = Base32.random();
        this.enabled = false;
    }

    public User(long id) {
        this.id = id;
    }

    /////////////////////////////////////////////////////////////////////////
    //Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public RoleUser getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(RoleUser roleUser) {
        this.roleUser = roleUser;
    }

    /////////////////////////////////////////////////////////////////////////
    //AUTH Getters and Setters

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(boolean isUsing2FA) {
        this.isUsing2FA = isUsing2FA;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /////////////////////////////////////////////////////////////////////////
    //Override methods

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mdp='" + password + '\'' +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
