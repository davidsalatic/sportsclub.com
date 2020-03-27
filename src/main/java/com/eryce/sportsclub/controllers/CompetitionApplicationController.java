package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.services.CompetitionApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(Routes.COMPETITION_APPLICATION_BASE)
@PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
public class CompetitionApplicationController {

    @Autowired
    private CompetitionApplicationService competitionApplicationService;

    @PostMapping
    @PreAuthorize(Authorize.HAS_MEMBER_ROLE)
    public ResponseEntity<CompetitionApplication>insert(@RequestBody CompetitionApplication competitionApplication)
    {
        return competitionApplicationService.insert(competitionApplication);
    }

}
