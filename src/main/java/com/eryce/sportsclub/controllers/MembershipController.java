package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.MembershipDto;
import com.eryce.sportsclub.services.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.MEMBERSHIPS_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(MEMBERSHIPS_BASE)
@PreAuthorize(HAS_MANAGER_ROLE)
@AllArgsConstructor
public class MembershipController {

    private MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<MembershipDto>> getAll() {
        return ok(membershipService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(membershipService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new MembershipDto());
        }
    }

    @GetMapping("/period/{periodId}")
    public ResponseEntity<MembershipDto> getByPeriod(@PathVariable("periodId") Integer periodId) {
        try {
            return ok(membershipService.getByPeriod(periodId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new MembershipDto());
        }
    }

    @PutMapping
    public ResponseEntity<MembershipDto> update(@RequestBody MembershipDto membershipDto) {
        return ok(membershipService.update(membershipDto));
    }
}