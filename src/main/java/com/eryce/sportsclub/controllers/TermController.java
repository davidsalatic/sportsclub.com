package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping(Routes.TERM_BASE+"/group/{groupId}")
    public List<Term> getAllByMemberGroup(@PathVariable("groupId")Integer memberGroupId)
    {
        return termService.getAllByMemberGroup(memberGroupId);
    }

    @PostMapping(Routes.TERM_BASE)
    public ResponseEntity<Term> insert(@RequestBody Term term)
    {
        return termService.insert(term);
    }

    @DeleteMapping(Routes.TERM_BASE+"/{id}")
    public ResponseEntity<Term> delete(@PathVariable("id")Integer id)
    {
        return termService.delete(id);
    }
}
