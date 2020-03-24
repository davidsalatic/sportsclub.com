package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping(Routes.TERM_BASE+"/group/{groupId}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public List<Term> getAllByMemberGroup(@PathVariable("groupId")Integer memberGroupId)
    {
        return termService.getAllByMemberGroup(memberGroupId);
    }

    @PostMapping(Routes.TERM_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<Term> insert(@RequestBody Term term)
    {
        return termService.insert(term);
    }

    @DeleteMapping(Routes.TERM_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<Term> delete(@PathVariable("id")Integer id)
    {
        return termService.delete(id);
    }
}
