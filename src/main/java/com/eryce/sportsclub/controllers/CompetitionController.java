package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.CompetitionDto;
import com.eryce.sportsclub.services.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_ANY_ROLE;
import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.COMPETITION_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(COMPETITION_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class CompetitionController {

    private CompetitionService competitionService;

    @GetMapping
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<List<CompetitionDto>> getAll() {
        return ok(competitionService.getAll());
    }

    @GetMapping("{/id}")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<CompetitionDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(competitionService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new CompetitionDto());
        }
    }

    @PostMapping
    public ResponseEntity<CompetitionDto> insert(@RequestBody CompetitionDto competitionDto) {
        return ok(competitionService.insert(competitionDto));
    }

    @PostMapping("/invite")
    public ResponseEntity<CompetitionDto> inviteMembers(@RequestBody CompetitionDto competitionDto) {
        competitionService.inviteMembers(competitionDto);
        return ok(competitionDto);
    }

    @PutMapping
    public ResponseEntity<CompetitionDto> update(@RequestBody CompetitionDto competitionDto) {
        try {
            return ok(competitionService.update(competitionDto));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new CompetitionDto());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        try {
            competitionService.delete(id);
            return ok(id);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(id);
        }
    }
}
