package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.services.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
public class MemberGroupController {

    @Autowired
    private MemberGroupService memberGroupService;

    @GetMapping(Routes.MEMBER_GROUPS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public Collection<MemberGroup> getAll() {
        return memberGroupService.getAll();
    }

    @GetMapping(Routes.MEMBER_GROUPS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public MemberGroup getById(@PathVariable("id")Integer id)
    {
        return memberGroupService.getById(id);
    }

    @GetMapping(Routes.MEMBER_GROUPS_BASE+"/search/name")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public MemberGroup getByName(@RequestParam String name)
    {
        return memberGroupService.getByName(name);
    }

    @PostMapping(Routes.MEMBER_GROUPS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<MemberGroup>insert(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.insert(memberGroup);
    }

    @PutMapping(Routes.MEMBER_GROUPS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<MemberGroup>update(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.update(memberGroup);
    }

    @DeleteMapping(Routes.MEMBER_GROUPS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<MemberGroup>delete(@PathVariable Integer id)
    {
        return memberGroupService.delete(id);
    }
}
