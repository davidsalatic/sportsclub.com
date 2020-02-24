package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @GetMapping("/sessions/{month}")
    public List<TrainingSession>getAllByMonth(@PathVariable("month") Integer month)
    {
        return trainingSessionService.getTrainingSessionsByMonth(month);
    }

    @GetMapping("/sessions")
    public List<TrainingSession>getAll()
    {
        return trainingSessionService.getAll();
    }

    @PostMapping("/sessions")
    public ResponseEntity<TrainingSession> insertTrainingSession(@RequestBody TrainingSession trainingSession)
    {
        return trainingSessionService.insertTrainingSessionIfNotExists(trainingSession);
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<TrainingSession> deleteTrainingSession(@RequestBody TrainingSession trainingSession)
    {
        return null;
    }

}
