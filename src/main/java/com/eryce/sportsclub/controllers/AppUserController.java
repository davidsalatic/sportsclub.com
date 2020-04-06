package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.AppUserRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.APP_USERS_BASE)
@PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
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
    @PreAuthorize(Authorize.HAS_ANY_ROLE)
    public AppUser getByUsername(@RequestParam String username)
    {
        // the '+' symbol is automatically replaced with ' ' when sending a request
        // therefor we must replace it back to '+' here.
        username=username.replace(' ','+');
        return appUserService.getByUsername(username);
    }

    @GetMapping("/search/jmbg")
    @PreAuthorize(Authorize.HAS_ANY_ROLE)
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
    @PreAuthorize(Authorize.HAS_ANY_ROLE)
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