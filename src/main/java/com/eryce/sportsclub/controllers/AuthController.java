package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.LoginRequestDTO;
import com.eryce.sportsclub.dto.LoginResponseDTO;
import com.eryce.sportsclub.dto.RegisterRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.security.jwt.JwtTokenProvider;
import com.eryce.sportsclub.services.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(Routes.AUTH_BASE)
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/claims")
    public Claims extractAllClaims(@RequestParam String token )
    {
        return authService.extractAllClaims(token);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return this.authService.login(loginRequestDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        return this.authService.register(registerRequestDTO);
    }

    @PostMapping("/check-token")
    public boolean checkToken(@RequestBody LoginResponseDTO tokenDTO)
    {
        return this.authService.canRegister(tokenDTO);
    }

}
