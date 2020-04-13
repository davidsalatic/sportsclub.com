package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
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
@RequestMapping(Routes.TERM_BASE)
@PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping("/group/{groupId}")
    public List<Term> getAllByMemberGroup(@PathVariable("groupId")Integer memberGroupId)
    {
        return termService.getAllByMemberGroup(memberGroupId);
    }

    @GetMapping("{id}")
    public Term getById(@PathVariable("id")Integer id)
    {
        return termService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Term> insert(@RequestBody Term term)
    {
        return termService.insert(term);
    }

    @PutMapping
    public ResponseEntity<Term> update(@RequestBody Term term)
    {
        return termService.update(term);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Term> delete(@PathVariable("id")Integer id)
    {
        return termService.delete(id);
    }
}
