package com.sc.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "username")
    private String username;
    private String password;
    private int enabled;
    private String authority;

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

    public int isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        if(enabled==true)
        {
            this.enabled = 1;
        }
        else {
            this.enabled = 0;
        }
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
