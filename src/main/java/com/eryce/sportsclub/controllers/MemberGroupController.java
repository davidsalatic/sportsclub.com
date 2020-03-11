package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.services.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.Collection;

@CrossOrigin
@RestController
public class MemberGroupController {

    @Autowired
    private MemberGroupService memberGroupService;

    @GetMapping(Routes.MEMBER_GROUPS_BASE)
    public Collection<MemberGroup> getAll() {
        return memberGroupService.getAll();
    }

    @GetMapping(Routes.MEMBER_GROUPS_BASE+"/{id}")
    public MemberGroup getById(@PathVariable("id")Integer id)
    {
        return memberGroupService.getById(id);
    }

    @PermitAll
    @PostMapping(Routes.MEMBER_GROUPS_BASE)
    public ResponseEntity<MemberGroup>insert(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.insert(memberGroup);
    }

    @PutMapping(Routes.MEMBER_GROUPS_BASE)
    public ResponseEntity<MemberGroup>update(@RequestBody MemberGroup memberGroup)
    {
        return memberGroupService.update(memberGroup);
    }

    @DeleteMapping(Routes.MEMBER_GROUPS_BASE+"/{id}")
    public ResponseEntity<MemberGroup>delete(@PathVariable Integer id)
    {
        return memberGroupService.delete(id);
    }
}
