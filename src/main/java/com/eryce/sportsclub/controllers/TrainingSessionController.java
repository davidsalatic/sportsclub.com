package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @GetMapping("/sessions/{month}/{year}")
    public List<TrainingSession>getAllByMonth(@PathVariable("month") Integer month,@PathVariable("year")Integer year)
    {
        return trainingSessionService.getTrainingSessionsByMonth(month,year);
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
