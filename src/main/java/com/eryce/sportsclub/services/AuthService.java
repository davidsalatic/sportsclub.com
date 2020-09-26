package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.LoginRequestDto;
import com.eryce.sportsclub.dto.JwtTokenDto;
import com.eryce.sportsclub.dto.RegistrationDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthService {

    private AppUserRepository appUserRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public Claims extractAllClaims(String token) {
        try {
            return jwtTokenProvider.extractAllClaims(token);
        } catch (Exception exception) {
            throw new MalformedJwtException("Wrong token format");
        }
    }

    public JwtTokenDto login(LoginRequestDto loginRequestDTO) {
        try {
            AppUser appUser = appUserRepository.findByUsernameIgnoreCase(loginRequestDTO.getUsername());
            if (appUser != null) {
                String username = loginRequestDTO.getUsername();
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequestDTO.getPassword(), appUser.getAuthorities()));
                String token = jwtTokenProvider.createToken(this.appUserRepository.findByUsernameIgnoreCase(username));

                return new JwtTokenDto(token);
            }
            return null;
        } catch (AuthenticationException e) {
            return null;
        }
    }

    public void register(RegistrationDto registrationDto) {
        String username = jwtTokenProvider.getUsername(registrationDto.getToken());
        AppUser appUser = appUserRepository.findByUsernameIgnoreCase(username);
        if (appUser == null) {
            throw new EntityNotFoundException();
        }
        appUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        appUserRepository.save(appUser);
    }

    public boolean canRegister(JwtTokenDto tokenDto) {
        try {
            if (jwtTokenProvider.validateToken(tokenDto.getToken())) {
                String username = jwtTokenProvider.getUsername(tokenDto.getToken());
                AppUser appUser = appUserRepository.findByUsernameIgnoreCase(username);
                return appUser.getPassword() == null;
            }
            return false;
        } catch (Exception exception) {
            return false;
        }
    }
}
