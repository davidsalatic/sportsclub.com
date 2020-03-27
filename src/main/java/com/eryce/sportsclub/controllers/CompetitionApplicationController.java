package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.CompetitionApplicationRequestDTO;
import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.services.CompetitionApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.COMPETITION_APPLICATION_BASE)
@PreAuthorize(Authorize.HAS_MEMBER_ROLE)
public class CompetitionApplicationController {

    @Autowired
    private CompetitionApplicationService competitionApplicationService;

    @GetMapping("competition/{competitionId}")
    @PreAuthorize(Authorize.HAS_COACH_OR_MANAGER_ROLE)
    public List<CompetitionApplication> getByCompetition(@PathVariable("competitionId")Integer competitionId) {
        return competitionApplicationService.getByCompetition(competitionId);
    }

    @GetMapping("competition/{competitionId}/user/{appUserId}")
    public CompetitionApplication getByCompetitionAndAppUser(@PathVariable("competitionId")Integer competitionId
    ,@PathVariable ("appUserId")Integer appUserId)
    {
        return competitionApplicationService.getByCompetitionAndAppUser(competitionId,appUserId);
    }

    @PostMapping
    public ResponseEntity<CompetitionApplication>insert(@RequestBody CompetitionApplicationRequestDTO competitionApplication)
    {
        return competitionApplicationService.insert(competitionApplication);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CompetitionApplication>delete(@PathVariable("id")Integer id)
    {
        return competitionApplicationService.delete(id);
    }


}
