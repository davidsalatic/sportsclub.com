package com.eryce.sportsclub.auth;

import com.eryce.sportsclub.auth.jwt.JWT;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    public String authenticate(AppUser appUser) {
       AppUser existingUser = appUserRepository.findByUsernameAndPassword(appUser.getUsername(),appUser.getPassword());
       if(existingUser!=null)
       {
           return JWT.generateToken(existingUser);
       }
       else
           return null;
    }
}
