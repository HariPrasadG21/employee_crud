package com.sc.security;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthResponse {
    private String token;


    public JwtAuthResponse() {
    }
    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
