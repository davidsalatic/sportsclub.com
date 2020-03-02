package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
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

    @GetMapping("/users/group/{id}")
    public List<AppUser> getUsersInGroup(@PathVariable("id")Integer id)
    {
        return appUserService.getUsersInGroup(id);
    }

    @GetMapping("/users/search")
    public List<AppUser> searchUsers(@RequestParam String name,@RequestParam String surname)
    {
        return appUserService.findAllByNameOrSurnameContainingIgnoreCase(name,surname);
    }

    @PostMapping("/users")
    public ResponseEntity<AppUser> insertUser(@RequestBody AppUser appUser)
    {
        return appUserService.insertUserIfNotExists(appUser);
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
