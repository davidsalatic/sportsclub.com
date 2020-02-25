package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/users")
    public Collection<AppUser> getAll()
    {
        return appUserService.getAll();
    }

    @GetMapping("/users/{id}")
    public AppUser getAppUser(@PathVariable("id")Integer id)
    {
        return appUserService.getById(id);
    }

    @GetMapping("/users/{id}/permissions")
    public List<Permission> getUserPermissions(@PathVariable("id")Integer id)
    {
        return appUserService.getUserPermissions(id);
    }

    @PostMapping("/users")
    public ResponseEntity<AppUser> insertUser(@RequestBody AppUser appUser)
    {
        return appUserService.insertUserIfNotExists(appUser);
    }

    @PutMapping("/users")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser appUser)
    {
        return appUserService.updateUserIfExists(appUser);
    }

    @DeleteMapping("/users")
    public ResponseEntity<AppUser>deleteUser(@RequestBody AppUser appUser)
    {
        return appUserService.deleteUserIfExists(appUser);
    }
}
