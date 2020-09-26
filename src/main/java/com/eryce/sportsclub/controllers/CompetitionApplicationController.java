package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.CompetitionApplicationDto;
import com.eryce.sportsclub.services.CompetitionApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Authorize.HAS_MEMBER_ROLE;
import static com.eryce.sportsclub.constants.Routes.COMPETITION_APPLICATION_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(COMPETITION_APPLICATION_BASE)
@PreAuthorize(HAS_MEMBER_ROLE)
public class CompetitionApplicationController {

    private CompetitionApplicationService competitionApplicationService;

    @GetMapping("/competitions/{competitionId}")
    @PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
    public ResponseEntity<List<CompetitionApplicationDto>> getByCompetition(@PathVariable("competitionId") Integer competitionId) {
        try {
            return ok(competitionApplicationService.getByCompetition(competitionId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/competitions/{competitionId}/user/{userId}")
    public ResponseEntity<CompetitionApplicationDto> getByCompetitionAndUser(@PathVariable("competitionId") Integer competitionId
            , @PathVariable("userId") Integer userId) {
        try {
            return ok(competitionApplicationService.getByCompetitionAndUser(competitionId, userId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new CompetitionApplicationDto());
        }
    }

    @PostMapping
    public ResponseEntity<CompetitionApplicationDto> insert(@RequestBody CompetitionApplicationDto competitionApplication) {
        try {
            return ok(competitionApplicationService.insert(competitionApplication));
        } catch (EntityExistsException exception) {
            return badRequest().body(competitionApplication);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        try {
            competitionApplicationService.delete(id);
            return ok(id);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(id);
        }
    }
}
