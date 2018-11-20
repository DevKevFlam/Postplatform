package fr.kflamand.Backend.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "User")
//@JsonFilter("UserFilter")
public class User implements UserDetails {

    // ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String fullName;

    // Boolean de validation du compte sert a bloqué le login si false
    private Boolean Enabled;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Transient
    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE ,fetch = FetchType.LAZY)
    private RegistrationToken registrationToken;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // OVERRIDE METHODS
    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.getName()));
        return authorities;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS AND SETTERS

    // ROLE
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // USERNAME
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getUsername() {
        return  this.username;
    }

    // PASSWORD
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getPassword() {
        return  this.password;
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
