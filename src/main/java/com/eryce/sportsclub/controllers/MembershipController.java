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
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping(Routes.MEMBERSHIPS_BASE)
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public List<Membership> getAll()
    {
        return membershipService.getAll();
    }

    @GetMapping(Routes.MEMBERSHIPS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public Membership getById(@PathVariable ("id")Integer id)
    {
        return membershipService.getById(id);
    }

    @GetMapping(Routes.MEMBERSHIPS_BASE+"/period/{periodId}")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public Membership getByPeriod(@PathVariable ("periodId")Integer periodId)
    {
        return membershipService.getByPeriod(periodId);
    }

    @PostMapping(Routes.MEMBERSHIPS_BASE)
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public ResponseEntity<Membership> insert(@RequestBody Membership membership)
    {
        return membershipService.insert(membership);
    }

    @PutMapping(Routes.MEMBERSHIPS_BASE)
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public ResponseEntity<Membership> update(@RequestBody Membership membership)
    {
        return membershipService.update(membership);
    }
}