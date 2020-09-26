package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.LoginRequestDto;
import com.eryce.sportsclub.dto.JwtTokenDto;
import com.eryce.sportsclub.dto.RegistrationDto;
import com.eryce.sportsclub.services.AuthService;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static com.eryce.sportsclub.constants.Routes.AUTH_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(AUTH_BASE)
public class AuthController {

    private AuthService authService;

    @GetMapping("/claims")
    public ResponseEntity<Claims> extractAllClaims(@RequestParam String token) {
        try {
            return ok(authService.extractAllClaims(token));
        } catch (MalformedJwtException exception) {
            return badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationDto> register(@RequestBody RegistrationDto registrationDto) {
        try {
            authService.register(registrationDto);
            return ok(registrationDto);
        } catch (EntityNotFoundException | JwtException | IllegalArgumentException exception) {
            return badRequest().body(registrationDto);
        }
    }

    @PostMapping("/check-token")
    public ResponseEntity<Boolean> checkToken(@RequestBody JwtTokenDto tokenDto) {
        return ok(authService.canRegister(tokenDto));
    }
}
