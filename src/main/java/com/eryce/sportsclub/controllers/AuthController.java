package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
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

    @GetMapping(Routes.AUTH_BASE+"/token")
    public String getToken()
    {
        return authService.getToken();
    }

    @GetMapping(Routes.AUTH_BASE+"/claims")
    public Claims extractAllClaims(@RequestParam String token )
    {
        return authService.extractAllClaims(token);
    }
}
