package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;

    public List<TrainingSession> getTrainingSessionsByGroupId(Integer groupId) {
        MemberGroup memberGroup= memberGroupRepository.getOne(groupId);
        return trainingSessionRepository.findAllByMemberGroup(memberGroup);
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

    public ResponseEntity<TrainingSession> deleteTrainingSessionIfExists(Integer id) {
        trainingSessionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<TrainingSession> updateTrainingSessionIfExists(TrainingSession trainingSession) {
        this.insertTrainingSessionIfNotExists(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
