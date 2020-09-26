package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.RoleDto;
import com.eryce.sportsclub.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.ROLES_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(ROLES_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        return ok(roleService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<RoleDto> getByName(@RequestParam String name) {
        try {
            return ok(roleService.getByName(name));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new RoleDto());
        }
    }

    @PostMapping
    public ResponseEntity<RoleDto> insert(@RequestBody RoleDto role) {
        return ok(roleService.insert(role));
    }
}
