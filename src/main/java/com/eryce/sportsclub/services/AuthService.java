package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.security.jwt.JWT;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;

@Service
public class AuthService {

    public String getToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            AppUser appUser = ((AppUser)principal);
            return JWT.generateToken(appUser);
        } else
            return null;
    }

    public Claims extractAllClaims(String token) {
        if(token.length()>0)
            return JWT.extractAllClaims(token);
        return null;
    }
}
