package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @GetMapping(Routes.TRAINING_SESSIONS_BASE+"/group/{groupId}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public List<TrainingSession>getAllByMemberGroup(@PathVariable("groupId")Integer groupId)
    {
        return trainingSessionService.getAllByMemberGroup(groupId);
    }

    @GetMapping(Routes.TRAINING_SESSIONS_BASE+"/group/{groupId}/period/{periodId}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public List<TrainingSession>getAllByMemberGroupAndPeriod(@PathVariable("groupId")Integer groupId,@PathVariable("periodId")Integer periodId)
    {
        return trainingSessionService.getAllByMemberGroupAndPeriod(groupId,periodId);
    }

    @GetMapping(Routes.TRAINING_SESSIONS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public TrainingSession getById(@PathVariable("id")Integer id){
        return trainingSessionService.getById(id);
    }

    @PostMapping(Routes.TRAINING_SESSIONS_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public ResponseEntity<TrainingSession> insert(@RequestBody TrainingSession trainingSession)
    {
        return trainingSessionService.insert(trainingSession);
    }

    @PostMapping(Routes.TRAINING_SESSIONS_BASE+"/generate/period/{periodId}/day/{day}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public ResponseEntity<TrainingSession> generateInTerms(@RequestBody Term[] terms,
                                                           @PathVariable("periodId")Integer periodId, @PathVariable("day")Integer day)
    {
        return this.trainingSessionService.generateInTerms(terms,periodId,day);
    }

    @DeleteMapping(Routes.TRAINING_SESSIONS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public ResponseEntity<TrainingSession> delete(@PathVariable Integer id)
    {
        return trainingSessionService.delete(id);
    }

    @DeleteMapping(Routes.TRAINING_SESSIONS_BASE+"/group/{groupId}/{periodId}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public ResponseEntity<TrainingSession> deleteByMemberGroupAndPeriod(@PathVariable("groupId")Integer groupId,@PathVariable("periodId")Integer periodId)
    {
        return trainingSessionService.deleteByMemberGroupAndPeriod(groupId,periodId);
    }
}
