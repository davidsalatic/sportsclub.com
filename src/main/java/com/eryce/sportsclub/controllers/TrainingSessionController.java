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
    public List<TrainingSession>getAllByMonth(@PathVariable("groupId")Integer groupId)
    {
        return trainingSessionService.getTrainingSessionsByGroupId(groupId);
    }

    @GetMapping("/sessions")
    public List<TrainingSession>getAll()
    {
        return trainingSessionService.getAll();
    }

    @GetMapping("/sessions/{id}")
    public TrainingSession getById(@PathVariable("id")Integer id){
        return trainingSessionService.getById(id);
    }

    @PostMapping("/sessions")
    public ResponseEntity<TrainingSession> insertTrainingSession(@RequestBody TrainingSession trainingSession)
    {
        return trainingSessionService.insertTrainingSessionIfNotExists(trainingSession);
    }

    @PutMapping("/sessions")
    public ResponseEntity<TrainingSession> updateTrainingSession(@RequestBody TrainingSession trainingSession)
    {
        return trainingSessionService.updateTrainingSessionIfExists(trainingSession);
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<TrainingSession> deleteTrainingSession(@PathVariable Integer id)
    {
        return trainingSessionService.deleteTrainingSessionIfExists(id);
    }
}
