package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
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
@PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_SELF+"')")
    public List<Competition> getAll()
    {
        return competitionService.getAll();
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

        @DeleteMapping("/{id}")
    public ResponseEntity<Competition> delete(@PathVariable("id")Integer id)
    {
        return competitionService.delete(id);
    }

}
