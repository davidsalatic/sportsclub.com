package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.AttendanceRequestDTO;
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

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    public List<Attendance> getAllByTrainingSessionId(Integer id) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(id);
        return attendanceRepository.findAllByTrainingSession(trainingSession);
    }

    public List<Attendance> getByAppUser(Integer appUserId) {
        AppUser appUser=appUserRepository.getOne(appUserId);
        return attendanceRepository.findAllByAppUser(appUser);
    }

    public Attendance getByTrainingSessionAndAppUser(Integer sessionId, Integer userId) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(sessionId);
        AppUser appUser = appUserRepository.getOne(userId);
        return attendanceRepository.findByTrainingSessionAndAppUser(trainingSession,appUser);
    }

    public ResponseEntity<Attendance> insert(AttendanceRequestDTO attendanceRequestDTO) {
        attendanceRepository.save(attendanceRequestDTO.generateAttendance());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Attendance> delete(Integer id) {
        attendanceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
