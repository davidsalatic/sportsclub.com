package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.LoginRequestDTO;
import com.eryce.sportsclub.dto.LoginResponseDTO;
import com.eryce.sportsclub.dto.RegisterRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.security.jwt.JWT;
import com.eryce.sportsclub.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.Principal;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public Claims extractAllClaims(String token) {
        if(token.length()>0)
            return jwtTokenProvider.extractAllClaims(token);
        return null;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
        AppUser appUser = appUserRepository.findByUsernameIgnoreCase(loginRequestDTO.getUsername());
        if(appUser!=null)//entered username correctly
        {
            String username = loginRequestDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequestDTO.getPassword(),appUser.getAuthorities()));
            String token = jwtTokenProvider.createToken(username, this.appUserRepository.findByUsernameIgnoreCase(username));

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken(token);
            return loginResponseDTO;
        }
        else
            return null;
        }
        catch (AuthenticationException e)
        {
            return null;
        }

    }

    public ResponseEntity register(RegisterRequestDTO registerRequestDTO) {
        String username = jwtTokenProvider.getUsername(registerRequestDTO.getToken());
        AppUser appUser = appUserRepository.findByUsernameIgnoreCase(username);
        appUser.setPassword(registerRequestDTO.getPassword());
        appUserRepository.save(appUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    public boolean canRegister(LoginResponseDTO tokenDTO) {
        try{
            if(jwtTokenProvider.validateToken(tokenDTO.getToken()))
            {
                String username = jwtTokenProvider.getUsername(tokenDTO.getToken());
                AppUser appUser = appUserRepository.findByUsernameIgnoreCase(username);
                if(appUser.getPassword()==null)
                    return true;//okay to register
                else
                    return false;//don't register,user already exists
            }
            return false;
        } catch (ExpiredJwtException expiredException)
        {
            return false;
        }
    }
}
