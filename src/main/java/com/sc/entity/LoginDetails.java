package com.sc.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class LoginDetails implements UserDetails {
    private String username;
    private String password;
    private boolean enabled;
    private String authority;
    private Users users;

    public LoginDetails(Users user) {
        username = user.getUsername();
        password = user.getPassword();
        authority = user.getAuthority();
        if(user.isEnabled()==1)
        {
            enabled = true;
        }
        else {
            enabled=false;
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.authority));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    // Other necessary fields and methods...

    // Implement UserDetails methods
}