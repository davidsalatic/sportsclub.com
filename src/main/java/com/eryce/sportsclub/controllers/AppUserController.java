package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Permission;
import com.eryce.sportsclub.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/users")
    public List<AppUser> getAllMembers()
    {
        return appUserService.getAllMembers();
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

    @GetMapping("users/search/username")
    public List<AppUser> getUsersByUsername(@RequestParam String username)
    {
        return appUserService.getUsersByUsername(username);
    }

    @GetMapping("users/search/jmbg")
    public List<AppUser> getUsersByJmbg(@RequestParam String jmbg)
    {
        return appUserService.getUsersByJmbg(jmbg);
    }

    @GetMapping("/users/group/{id}")
    public List<AppUser> getUsersInGroup(@PathVariable("id")Integer id)
    {
        return appUserService.getUsersInGroup(id);
    }

    @PostMapping("/users")
    public ResponseEntity<AppUser> insertUser(@RequestBody AppUser appUser)
    {
        return appUserService.insertUser(appUser);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<AppUser> removeMemberGroup(@PathVariable Integer id) {
        return appUserService.removeMemberGroup(id);
    }

    @PutMapping("/users")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser appUser)
    {
        return appUserService.updateUserIfExists(appUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<AppUser>deleteUser(@PathVariable ("id") Integer id)
    {
        return appUserService.deleteUserIfExists(id);
    }
}
