package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.services.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.COMPETITION_BASE)
@PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    @PreAuthorize(Authorize.HAS_ANY_ROLE)
    public List<Competition> getAll()
    {
        return competitionService.getAll();
    }

    @GetMapping("{id}")
    @PreAuthorize(Authorize.HAS_ANY_ROLE)
    public Competition getById(@PathVariable("id")Integer id)
    {
        return competitionService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Competition> insert(@RequestBody Competition competition)
    {
        return competitionService.insert(competition);
    }

    @PostMapping("invite")
    public ResponseEntity<Competition> inviteMembers(@RequestBody Competition competition)
    {
        return competitionService.inviteMembers(competition);
    }

    @PutMapping
    public ResponseEntity<Competition> update(@RequestBody Competition competition)
    {
        return this.competitionService.update(competition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Competition> delete(@PathVariable("id")Integer id)
    {
        return competitionService.delete(id);
    }

}
