package fr.kflamand.Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "User")
@Scope("session")
public class User implements UserDetails {


    public static enum Role {USER}

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

    @Transient
    @JsonIgnore
    @OneToOne(mappedBy = "user")
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

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.Enabled;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role +
                ",]";
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setVerified(Boolean verified) {
        Enabled = verified;
    }

    public RegistrationToken getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(RegistrationToken registrationToken) {
        this.registrationToken = registrationToken;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
