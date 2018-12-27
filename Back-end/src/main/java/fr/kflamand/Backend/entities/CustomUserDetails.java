package fr.kflamand.Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails extends User implements UserDetails {

    public CustomUserDetails() {
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
        authorities.add(new SimpleGrantedAuthority(this.getRole().getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return  this.getUserName();
    }

    @Override
    public String getPassword() {
        return  this.getPassword();
    }

}
