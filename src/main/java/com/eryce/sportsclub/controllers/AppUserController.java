package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/users/members")
    public List<AppUser> getAllMembers()
    {
        return appUserService.getAllMembers();
    }

    @GetMapping("/users/group/{id}")
    public List<AppUser> getAllInMemberGroup(@PathVariable("id")Integer id)
    {
        return appUserService.getAllInMemberGroup(id);
    }

    @GetMapping("/users/{id}")
    public AppUser getById(@PathVariable("id")Integer id)
    {
        return appUserService.getById(id);
    }

    @GetMapping("users/search/username")
    public List<AppUser> getByUsername(@RequestParam String username)
    {
        return appUserService.getByUsername(username);
    }

    @GetMapping("users/search/jmbg")
    public List<AppUser> getByJmbg(@RequestParam String jmbg)
    {
        return appUserService.getByJmbg(jmbg);
    }

    @PostMapping("/users")
    public ResponseEntity<AppUser> insert(@RequestBody AppUser appUser)
    {
        return appUserService.insert(appUser);
    }

    @PutMapping("/users")
    public ResponseEntity<AppUser> update(@RequestBody AppUser appUser)
    {
        return appUserService.update(appUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<AppUser>delete(@PathVariable ("id") Integer id)
    {
        return appUserService.delete(id);
    }
}
