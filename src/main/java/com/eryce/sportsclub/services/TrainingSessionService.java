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

    public List<TrainingSession> getTrainingSessionsByMonth(Integer month) {
        return trainingSessionRepository.findAllByMonth(month);
    }

    public ResponseEntity<TrainingSession> insertTrainingSessionIfNotExists(TrainingSession trainingSession) {
        if(exists(trainingSession))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        trainingSessionRepository.save(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private boolean exists(TrainingSession trainingSession) {
        return trainingSessionRepository.existsById(trainingSession.getTrainingSessionId()) || existsByDateAndTime(trainingSession);
    }

    private boolean existsByDateAndTime(TrainingSession trainingSession) {
        return !(trainingSessionRepository.findAllByDateHeld(trainingSession.getDateHeld()).isEmpty() &&
                trainingSessionRepository.findAllByTimeHeld(trainingSession.getTimeHeld()).isEmpty());
    }

    public List<TrainingSession> getAll() {
        return trainingSessionRepository.findAll();
    }
}
