package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.security.jwt.JWT;
import com.eryce.sportsclub.services.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/auth/token")
    public String getToken()
    {
        return authService.getToken();
    }

    @GetMapping("/auth/claims")
    public Claims extractAllClaims(@RequestParam String token )
    {
        if(token.length()>0)
            return JWT.extractAllClaims(token);
        return null;
    }
}
