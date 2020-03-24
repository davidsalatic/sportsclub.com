package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.LoginRequestDTO;
import com.eryce.sportsclub.dto.LoginResponseDTO;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.security.jwt.JwtTokenProvider;
import com.eryce.sportsclub.services.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(Routes.AUTH_BASE+"/claims")
    public Claims extractAllClaims(@RequestParam String token )
    {
        return authService.extractAllClaims(token);
    }

    @PostMapping(Routes.AUTH_BASE+"/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return this.authService.login(loginRequestDTO);
    }

}
