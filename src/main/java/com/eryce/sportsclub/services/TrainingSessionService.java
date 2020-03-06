package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.AttendanceRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
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
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<TrainingSession> getAllByMemberGroup(Integer groupId) {
        MemberGroup memberGroup= memberGroupRepository.getOne(groupId);
        return trainingSessionRepository.findAllByMemberGroup(memberGroup);
    }

    public TrainingSession getById(Integer id)
    {
        return trainingSessionRepository.getOne(id);
    }

    public ResponseEntity<TrainingSession> insert(TrainingSession trainingSession) {
        trainingSessionRepository.save(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<TrainingSession> delete(Integer id) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(id);
        deleteAttendancesForTrainingSession(trainingSession);
        trainingSessionRepository.delete(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteAttendancesForTrainingSession(TrainingSession trainingSession) {
        List<Attendance> attendances = attendanceRepository.findAllByTrainingSession(trainingSession);
        for(Attendance attendance: attendances)
        {
            attendanceRepository.delete(attendance);
        }
    }
}
