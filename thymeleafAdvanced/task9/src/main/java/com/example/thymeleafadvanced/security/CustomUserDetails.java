package com.example.thymeleafadvanced.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    
    private final String displayName;
    private final String email;
    private final String lastLoginAt;

    public CustomUserDetails(
            String username, 
            String password, 
            Collection<? extends GrantedAuthority> authorities,
            String displayName,
            String email,
            String lastLoginAt) {
        super(username, password, authorities);
        this.displayName = displayName;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getLastLoginAt() {
        return lastLoginAt;
    }
}