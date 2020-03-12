package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.security.jwt.JWT;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String getToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            AppUser appUser = ((AppUser)principal);
            return JWT.generateToken(appUser);
        } else {
            return null;
        }
    }
}
