package com.eryce.sportsclub.auth;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.AppUser;
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

    @PostMapping(Routes.AUTHENTICATE_BASE)
        private String authenticate(@RequestBody AppUser appUser)
        {
            return authService.authenticate(appUser);
        }
}
