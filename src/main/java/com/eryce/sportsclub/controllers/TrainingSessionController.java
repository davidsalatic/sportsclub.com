package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @GetMapping("/sessions/group/{groupId}")
    public List<TrainingSession>getAllByMemberGroup(@PathVariable("groupId")Integer groupId)
    {
        return trainingSessionService.getAllByMemberGroup(groupId);
    }

    @GetMapping("/sessions/{id}")
    public TrainingSession getById(@PathVariable("id")Integer id){
        return trainingSessionService.getById(id);
    }

    @PostMapping("/sessions")
    public ResponseEntity<TrainingSession> insert(@RequestBody TrainingSession trainingSession)
    {
        return trainingSessionService.insert(trainingSession);
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<TrainingSession> delete(@PathVariable Integer id)
    {
        return trainingSessionService.delete(id);
    }
}
