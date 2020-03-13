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

    @GetMapping(Routes.TERM_BASE)
    public List<Term> getAll()
    {
        return termService.getAll();
    }

    @PostMapping(Routes.TERM_BASE)
    public ResponseEntity<Term> insert(@RequestBody Term term)
    {
        return termService.insert(term);
    }
}
