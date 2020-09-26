package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.AppUserDto;
import com.eryce.sportsclub.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_ANY_ROLE;
import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.APP_USERS_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(APP_USERS_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class AppUserController {

    private AppUserService appUserService;

    @GetMapping("/members")
    public ResponseEntity<List<AppUserDto>> getAllMembers() {
        return ok(appUserService.getAllMembers());
    }

    @GetMapping("/members/ungrouped")
    public ResponseEntity<List<AppUserDto>> getUngroupedMembers() {
        return ok(appUserService.getUngroupedMembers());
    }

    @GetMapping("/staff")
    public ResponseEntity<List<AppUserDto>> getAllStaff() {
        return ok(appUserService.getAllStaff());
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<AppUserDto>> getAllInMemberGroup(@PathVariable("id") Integer memberGroupId) {
        try {
            return ok(appUserService.getAllInMemberGroup(memberGroupId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(appUserService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new AppUserDto());
        }
    }

    @GetMapping("/search/username")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<AppUserDto> getByUsername(@RequestParam String username) {
        // the '+' symbol is automatically replaced with ' ' when sending a request
        // therefore we must replace it back to '+' here.
        username = username.replace(' ', '+');
        try {
            return ok(appUserService.getByUsername(username));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new AppUserDto());
        }
    }

    @GetMapping("/search/jmbg")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<AppUserDto> getByJmbg(@RequestParam String jmbg) {
        try {
            return ok(appUserService.getByJmbg(jmbg));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new AppUserDto());
        }
    }

    @PostMapping
    public ResponseEntity<AppUserDto> insert(@RequestBody AppUserDto appUserDto) {
        try {
            return ok(appUserService.insert(appUserDto));
        } catch (EntityExistsException exception) {
            return badRequest().body(appUserDto);
        }
    }

    @PutMapping
    public ResponseEntity<AppUserDto> update(@RequestBody AppUserDto appUserDto) {
        try {
            return ok(appUserService.update(appUserDto));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(appUserDto);
        }
    }

    @PutMapping("/update-self")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<AppUserDto> updateSelf(@RequestBody AppUserDto appUserDto) {
        try {
            return ok(appUserService.updateSelf(appUserDto));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(appUserDto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        try {
            appUserService.delete(id);
            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}