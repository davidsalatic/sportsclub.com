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
        if(exists(attendance))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        attendanceRepository.save(attendance);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private boolean exists(Attendance attendance) {
        return existsByTrainingSession(attendance);
    }

    private boolean existsByTrainingSession(Attendance attendance) {
        return (attendanceRepository.findByTrainingSession(attendance.getTrainingSession())!=null);
    }

    public Collection<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> findByTrainingSessionId(Integer id) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(id);
        return attendanceRepository.findAllByTrainingSession(trainingSession);
    }

    public ResponseEntity<Attendance> updateAttendanceIfExists(Attendance attendance) {
        if(exists(attendance))
        {
            attendanceRepository.save(attendance);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Attendance> deleteAttendanceIfExists(Attendance attendance)
    {
        if(exists(attendance))
        {
            attendanceRepository.delete(attendance);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
