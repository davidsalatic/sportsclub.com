package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.AttendanceRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.PeriodRepository;
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
    @Autowired
    private PeriodRepository periodRepository;

    private final Integer FIRST_DAY_OF_MONTH=1;

    public List<TrainingSession> getAllByMemberGroup(Integer groupId) {
        MemberGroup memberGroup= memberGroupRepository.getOne(groupId);
        return trainingSessionRepository.findAllByMemberGroup(memberGroup);
    }

    public List<TrainingSession> getAllByMemberGroupAndPeriod(Integer groupId, Integer periodId) {
        MemberGroup memberGroup= memberGroupRepository.getOne(groupId);
        Period period = periodRepository.getOne(periodId);
        return trainingSessionRepository.findAllByMemberGroupAndPeriod(memberGroup,period);
    }

    public TrainingSession getById(Integer id)
    {
        return trainingSessionRepository.getOne(id);
    }

    public ResponseEntity<TrainingSession> insert(TrainingSession trainingSession) {
        trainingSessionRepository.save(trainingSession);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<TrainingSession> generateInTerms(Term[] terms,Integer periodId,Integer generateFromDay) {

        LocalDate today = LocalDate.now();
        int numberOfDaysInCurrentMonth=today.lengthOfMonth();

        Period period = periodRepository.getOne(periodId);

        if(generateFromDay.equals(FIRST_DAY_OF_MONTH))
            deleteTrainingSessionsInGroupInPeriodIfGreaterThan(terms[0].getMemberGroup(),period,FIRST_DAY_OF_MONTH);
        else
            deleteTrainingSessionsInGroupInPeriodIfGreaterThan(terms[0].getMemberGroup(),period,generateFromDay);

        for(int i=generateFromDay;i<=numberOfDaysInCurrentMonth;i++)
        {
            LocalDate date = LocalDate.of(period.getYear(),period.getMonth(),i);
            for (Term term : terms) {
                if (date.getDayOfWeek().getValue() == term.getDayOfWeek()) {
                    TrainingSession trainingSession = new TrainingSession();
                    trainingSession.setDateHeld(date);
                    trainingSession.setTimeHeld(term.getStartTime());
                    trainingSession.setMemberGroup(term.getMemberGroup());
                    trainingSession.setPeriod(period);
                    trainingSessionRepository.save(trainingSession);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteTrainingSessionsInGroupInPeriodIfGreaterThan(MemberGroup memberGroup,Period period,Integer fromDay) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findAllByMemberGroupAndPeriod(memberGroup,period);
        for(TrainingSession trainingSession : trainingSessions)
        {
            if(trainingSession.getDateHeld().getDayOfMonth()>=fromDay)
            {
                this.deleteAttendancesForTrainingSession(trainingSession);
                trainingSessionRepository.delete(trainingSession);
            }
        }
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

    public ResponseEntity<TrainingSession> deleteByMemberGroupAndPeriod(Integer groupId, Integer periodId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(groupId);
        Period period = periodRepository.getOne(periodId);
        List<TrainingSession> trainingSessions = trainingSessionRepository.findAllByMemberGroupAndPeriod(memberGroup,period);
        for(TrainingSession trainingSession : trainingSessions)
        {
            this.delete(trainingSession.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
