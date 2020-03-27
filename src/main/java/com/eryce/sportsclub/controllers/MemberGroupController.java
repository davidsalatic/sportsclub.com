package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.services.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.MEMBER_GROUPS_BASE)
@PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
public class MemberGroupController {

    @Autowired
    private MemberGroupService memberGroupService;

    @GetMapping
    public List<MemberGroup> getAll() {
        return memberGroupService.getAll();
    }

    @GetMapping("/{id}")
    public MemberGroup getById(@PathVariable("id")Integer id)
    {
        return memberGroupService.getById(id);
    }

    @GetMapping("/search/name")
    public MemberGroup getByName(@RequestParam String name)
    {
        return memberGroupService.getByName(name);
    }

    @PostMapping
    public ResponseEntity<MemberGroup>insert(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.insert(memberGroup);
    }

    @PutMapping
    public ResponseEntity<MemberGroup>update(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.update(memberGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberGroup>delete(@PathVariable Integer id)
    {
        return memberGroupService.delete(id);
    }
}
