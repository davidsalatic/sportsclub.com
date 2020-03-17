package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.AttendanceRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.TrainingSessionRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    private final Integer FIRST_DAY_OF_MONTH=1;

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

    public ResponseEntity<TrainingSession> generateInTerm(Term[] terms,Integer generateFromDay) {

        LocalDate today = LocalDate.now();
        int numberOfDaysInCurrentMonth=today.lengthOfMonth();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        if(!(generateFromDay.equals(FIRST_DAY_OF_MONTH)))
            deleteTrainingSessionsInGroupForCurrentMonth(terms[0].getMemberGroup());

        for(int i=generateFromDay;i<=numberOfDaysInCurrentMonth;i++)
        {
            LocalDate date = LocalDate.of(currentYear,currentMonth,i);
            for (Term term : terms) {
                if (date.getDayOfWeek().getValue() == term.getDayOfWeek()) {
                    TrainingSession trainingSession = new TrainingSession();
                    trainingSession.setDateHeld(date);
                    trainingSession.setTimeHeld(term.getStartTime());
                    trainingSession.setMemberGroup(term.getMemberGroup());
                    trainingSessionRepository.save(trainingSession);
                }
            }
        }


       return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteTrainingSessionsInGroupForCurrentMonth(MemberGroup memberGroup) {

    }

}
