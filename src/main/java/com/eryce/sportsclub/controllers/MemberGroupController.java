package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.services.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MemberGroupController {

    @Autowired
    private MemberGroupService memberGroupService;

    @GetMapping("/groups")
    public Collection<MemberGroup> getAll() {
        return memberGroupService.getAll();
    }

    @GetMapping("/groups/{id}")
    public MemberGroup getGroup(@PathVariable("id")Integer id)
    {
        return memberGroupService.getById(id);
    }

    @PostMapping("/groups")
    public ResponseEntity<MemberGroup>insertGroup(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.insertGroupIfNotExists(memberGroup);
    }

    @PutMapping("/groups")
    public ResponseEntity<MemberGroup>updateGroup(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.updateGroupIfExists(memberGroup);
    }

    @DeleteMapping("/groups")
    public ResponseEntity<MemberGroup>deleteGroup(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.deleteGroupIfExists(memberGroup);
    }
}
