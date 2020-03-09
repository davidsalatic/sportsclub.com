package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser authenticate(AppUser appUser) {
       return appUserRepository.findByUsernameAndPassword(appUser.getUsername(),appUser.getPassword());
    }
}
