package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    public List<TrainingSession> getTrainingSessionsByMonth(Integer year,Integer month) {
        return trainingSessionRepository.findAllByYearAndMonth(year,month);
    }

    public ResponseEntity<TrainingSession> insertTrainingSessionIfNotExists(TrainingSession trainingSession) {
        trainingSession.setMonth(trainingSession.getDateHeld().getMonthValue());
        trainingSession.setYear(trainingSession.getDateHeld().getYear());
        trainingSessionRepository.save(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<TrainingSession> getAll() {
        return trainingSessionRepository.findAll();
    }

    public ResponseEntity<TrainingSession> deleteTrainingSessionIfExists(TrainingSession trainingSession) {
        trainingSessionRepository.delete(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<TrainingSession> updateTrainingSessionIfExists(TrainingSession trainingSession) {
        trainingSessionRepository.save(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
