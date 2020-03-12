package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
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

    @GetMapping(Routes.APP_USERS_BASE+"/members")
    public List<AppUser> getAllMembers()
    {
        return appUserService.getAllMembers();
    }

    @GetMapping(Routes.APP_USERS_BASE+"/members/ungrouped")
    public List<AppUser> getUngroupedMembers()
    {
        return appUserService.getUngroupedMembers();
    }

    @GetMapping(Routes.APP_USERS_BASE+"/group/{id}")
    public List<AppUser> getAllInMemberGroup(@PathVariable("id")Integer id)
    {
        return appUserService.getAllInMemberGroup(id);
    }

    @GetMapping(Routes.APP_USERS_BASE+"/{id}")
    public AppUser getById(@PathVariable("id")Integer id)
    {
        return appUserService.getById(id);
    }

    @GetMapping(Routes.APP_USERS_BASE+"/search/username")
    public AppUser getByUsername(@RequestParam String username)
    {
        return appUserService.getByUsername(username);
    }

    @GetMapping(Routes.APP_USERS_BASE+"/search/jmbg")
    public AppUser getByJmbg(@RequestParam String jmbg)
    {
        return appUserService.getByJmbg(jmbg);
    }

    @GetMapping("/logged/details")
    public String getLoggedInUser()
    {
        return appUserService.getLoggedInUser();
    }

    @PostMapping(Routes.APP_USERS_BASE)
    public ResponseEntity<AppUser> insert(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.insert(appUserRequestDTO);
    }

    @PutMapping(Routes.APP_USERS_BASE)
    public ResponseEntity<AppUser> update(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.update(appUserRequestDTO);
    }

    @DeleteMapping(Routes.APP_USERS_BASE+"/{id}")
    public ResponseEntity<AppUser>delete(@PathVariable ("id") Integer id)
    {
        return appUserService.delete(id);
    }
}