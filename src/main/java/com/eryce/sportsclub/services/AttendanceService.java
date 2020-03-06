package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.AttendanceRepository;
import com.eryce.sportsclub.repositories.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    public List<Attendance> findByAppUserId(Integer id) {
        AppUser appUser =  appUserRepository.getOne(id);
        return attendanceRepository.findAllByAppUser(appUser);
    }

    public ResponseEntity<Attendance> insertAttendanceIfNotExists(Attendance attendance) {
        attendanceRepository.save(attendance);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public Collection<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> findByTrainingSessionId(Integer id) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(id);
        return attendanceRepository.findAllByTrainingSession(trainingSession);
    }

    public ResponseEntity<Attendance> updateAttendanceIfExists(Attendance attendance) {
            attendanceRepository.save(attendance);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Attendance> deleteAttendanceIfExists(Integer id)
    {
            attendanceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    public Attendance getByTrainingSessionAndAppUser(Integer sessionId, Integer userId) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(sessionId);
        AppUser appUser = appUserRepository.getOne(userId);
        return attendanceRepository.findByTrainingSessionAndAppUser(trainingSession,appUser);
    }
}
