package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.security.jwt.JWT;
import com.eryce.sportsclub.services.AppUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping(Routes.APP_USERS_BASE+"/members")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public List<AppUser> getAllMembers()
    {
        return appUserService.getAllMembers();
    }

    @GetMapping(Routes.APP_USERS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public AppUser getById(@PathVariable("id")Integer id)
    {
        return appUserService.getById(id);
    }

    @GetMapping(Routes.APP_USERS_BASE+"/staff")
    public List<AppUser>getAllStaff(){
        return appUserService.getAllStaff();
    }

    @GetMapping(Routes.APP_USERS_BASE+"/members/ungrouped")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public List<AppUser> getUngroupedMembers()
    {
        return appUserService.getUngroupedMembers();
    }

    @GetMapping(Routes.APP_USERS_BASE+"/group/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public List<AppUser> getAllInMemberGroup(@PathVariable("id")Integer id)
    {
        return appUserService.getAllInMemberGroup(id);
    }

    @GetMapping(Routes.APP_USERS_BASE+"/search/username")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public AppUser getByUsername(@RequestParam String username)
    {
        return appUserService.getByUsername(username);
    }

    @GetMapping(Routes.APP_USERS_BASE+"/search/jmbg")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public AppUser getByJmbg(@RequestParam String jmbg)
    {
        return appUserService.getByJmbg(jmbg);
    }

    @PostMapping(Routes.APP_USERS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<AppUser> insert(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.insert(appUserRequestDTO);
    }

    @PutMapping(Routes.APP_USERS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<AppUser> update(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.update(appUserRequestDTO);
    }

    @PutMapping(Routes.APP_USERS_BASE+"/update-self")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_SELF+"')")
    public ResponseEntity<AppUser> updateSelf(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.updateSelf(appUserRequestDTO);
    }

    @DeleteMapping(Routes.APP_USERS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<AppUser>delete(@PathVariable ("id") Integer id)
    {
        return appUserService.delete(id);
    }
}