package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Membership;
import com.eryce.sportsclub.services.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.MEMBERSHIPS_BASE)
@PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public List<Membership> getAll()
    {
        return membershipService.getAll();
    }

    @GetMapping("/{id}")
    public Membership getById(@PathVariable ("id")Integer id)
    {
        return membershipService.getById(id);
    }

    @GetMapping("/period/{periodId}")
    public Membership getByPeriod(@PathVariable ("periodId")Integer periodId)
    {
        return membershipService.getByPeriod(periodId);
    }

    @PostMapping
    public ResponseEntity<Membership> insert(@RequestBody Membership membership)
    {
        return membershipService.insert(membership);
    }

    @PutMapping
    public ResponseEntity<Membership> update(@RequestBody Membership membership)
    {
        return membershipService.update(membership);
    }
}