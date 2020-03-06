package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.services.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
public class MemberGroupController {

    @Autowired
    private MemberGroupService memberGroupService;

    @GetMapping("/groups")
    public Collection<MemberGroup> getAll() {
        return memberGroupService.getAll();
    }

    @GetMapping("/groups/{id}")
    public MemberGroup getById(@PathVariable("id")Integer id)
    {
        return memberGroupService.getById(id);
    }

    @PostMapping("/groups")
    public ResponseEntity<MemberGroup>insert(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.insert(memberGroup);
    }

    @PutMapping("/groups")
    public ResponseEntity<MemberGroup>update(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.update(memberGroup);
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<MemberGroup>delete(@PathVariable Integer id)
    {
        return memberGroupService.delete(id);
    }
}
