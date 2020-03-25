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
@RequestMapping(Routes.APP_USERS_BASE)
@PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/members")
    public List<AppUser> getAllMembers()
    {
        return appUserService.getAllMembers();
    }

    @GetMapping("/{id}")
    public AppUser getById(@PathVariable("id")Integer id)
    {
        return appUserService.getById(id);
    }

    @GetMapping("/staff")
    public List<AppUser>getAllStaff(){
        return appUserService.getAllStaff();
    }

    @GetMapping("/members/ungrouped")
    public List<AppUser> getUngroupedMembers()
    {
        return appUserService.getUngroupedMembers();
    }

    @GetMapping("/group/{id}")
    public List<AppUser> getAllInMemberGroup(@PathVariable("id")Integer id)
    {
        return appUserService.getAllInMemberGroup(id);
    }

    @GetMapping("/search/username")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_SELF+"')")
    public AppUser getByUsername(@RequestParam String username)
    {
        return appUserService.getByUsername(username);
    }

    @GetMapping("/search/jmbg")
    public AppUser getByJmbg(@RequestParam String jmbg)
    {
        return appUserService.getByJmbg(jmbg);
    }

    @PostMapping
    public ResponseEntity<AppUser> insert(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.insert(appUserRequestDTO);
    }

    @PutMapping
    public ResponseEntity<AppUser> update(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.update(appUserRequestDTO);
    }

    @PutMapping("/update-self")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_SELF+"')")
    public ResponseEntity<AppUser> updateSelf(@RequestBody AppUserRequestDTO appUserRequestDTO)
    {
        return appUserService.updateSelf(appUserRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppUser>delete(@PathVariable ("id") Integer id)
    {
        return appUserService.delete(id);
    }
}