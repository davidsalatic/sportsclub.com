package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public Collection<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser getById(Integer id) {
        return appUserRepository.getOne(id);
    }

    public ResponseEntity<AppUser> insertUserIfNotExists(AppUser appUser) {
        if(exists(appUser))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        appUserRepository.save(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean exists(AppUser appUser) {
        return existsByUsername(appUser.getUsername()) || appUserRepository.existsById(appUser.getUserId());
    }

    private boolean existsByUsername(String username) {
        return !(appUserRepository.findAllByUsernameContainingIgnoreCase(username).isEmpty());
    }

    public ResponseEntity<AppUser> updateUserIfExists(AppUser appUser) {
        if(exists(appUser))
        {
            appUserRepository.save(appUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<AppUser> deleteUserIfExists(AppUser appUser) {
        if(exists(appUser))
        {
            appUserRepository.delete(appUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
