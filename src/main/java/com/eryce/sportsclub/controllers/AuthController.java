package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    private AppUser authenticate(@RequestBody AppUser appUser)
    {
        return authService.authenticate(appUser);
    }

}
