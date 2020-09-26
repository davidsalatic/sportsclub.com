package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.TrainingSessionDto;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.services.TrainingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.TRAINING_SESSIONS_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(TRAINING_SESSIONS_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class TrainingSessionController {

    private TrainingSessionService trainingSessionService;

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<TrainingSessionDto>> getAllByMemberGroup(@PathVariable("groupId") Integer groupId) {
        try {
            return ok(trainingSessionService.getAllByMemberGroup(groupId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/group/{groupId}/per/{periodId}")
    public ResponseEntity<List<TrainingSessionDto>> getAllByMemberGroupAndPeriod(@PathVariable("groupId") Integer groupId, @PathVariable("periodId") Integer periodId) {
        try {
            return ok(trainingSessionService.getAllByMemberGroupAndPeriod(groupId, periodId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(trainingSessionService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new TrainingSessionDto());
        }
    }

    @PostMapping
    public ResponseEntity<TrainingSessionDto> insert(@RequestBody TrainingSessionDto trainingSessionDto) {
        return ok(trainingSessionService.insert(trainingSessionDto));
    }

    @PostMapping("/generate/period/{periodId}/day/{day}")
    public ResponseEntity<List<TrainingSessionDto>> generateInTerms(@RequestBody Term[] terms,
                                          @PathVariable("periodId") Integer periodId, @PathVariable("day") Integer day) {
        try {
            return ok(trainingSessionService.generateInTerms(terms, periodId, day));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        try {
            trainingSessionService.delete(id);
            return ok(id);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(id);
        }
    }

    @DeleteMapping("/group/{groupId}/{periodId}")
    public ResponseEntity<Integer> deleteByMemberGroupAndPeriod(@PathVariable("groupId") Integer groupId, @PathVariable("periodId") Integer periodId) {
        try {
            trainingSessionService.deleteByMemberGroupAndPeriod(groupId, periodId);
            return ok(groupId);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(groupId);
        }
    }
}
