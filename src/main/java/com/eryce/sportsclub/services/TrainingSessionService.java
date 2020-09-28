package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.TrainingSessionDto;
import com.eryce.sportsclub.models.*;
import com.eryce.sportsclub.repositories.AttendanceRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.PeriodRepository;
import com.eryce.sportsclub.repositories.TrainingSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingSessionService {

    private TrainingSessionRepository trainingSessionRepository;
    private MemberGroupRepository memberGroupRepository;
    private AttendanceRepository attendanceRepository;
    private PeriodRepository periodRepository;

    public List<TrainingSessionDto> getAllByMemberGroup(Integer groupId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(groupId);
        return convertToDto(trainingSessionRepository.findAllByMemberGroup(memberGroup));
    }

    public List<TrainingSessionDto> getAllByMemberGroupAndPeriod(Integer groupId, Integer periodId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(groupId);
        Period period = periodRepository.getOne(periodId);
        return convertToDto(trainingSessionRepository.findAllByMemberGroupAndPeriod(memberGroup, period));
    }

    private List<TrainingSessionDto> convertToDto(List<TrainingSession> trainingSessions) {
        List<TrainingSessionDto> trainingSessionsDto = new ArrayList<>();
        for (TrainingSession trainingSession : trainingSessions) {
            trainingSessionsDto.add(trainingSession.convertToDto());
        }
        return trainingSessionsDto;
    }

    public TrainingSessionDto getById(Integer id) {
        return trainingSessionRepository.getOne(id).convertToDto();
    }

    public TrainingSessionDto insert(TrainingSessionDto trainingSessionDto) {
        TrainingSession trainingSession = trainingSessionRepository.save(trainingSessionDto.convertToEntity());
        return trainingSession.convertToDto();
    }

    public List<TrainingSessionDto> generateInTerms(Term[] terms, Integer periodId, Integer generateFromDay) {
        List<TrainingSessionDto> trainingSessionDtos = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int numberOfDaysInCurrentMonth = today.lengthOfMonth();

        Period period = periodRepository.getOne(periodId);

        deleteTrainingSessionsInGroupInPeriodIfGreaterThan(terms[0].getMemberGroup(), period, generateFromDay);

        for (int i = generateFromDay; i <= numberOfDaysInCurrentMonth; i++) {
            LocalDate date = LocalDate.of(period.getYear(), period.getMonth(), i);
            for (Term term : terms) {
                int loopDayOfWeek = date.getDayOfWeek().getValue();
                int termDayOfWeek = term.getDayOfWeek();

                //Sunday in JS is 0, in LocalDate is 7
                if (termDayOfWeek == 0) {
                    termDayOfWeek = 7;
                }

                if (loopDayOfWeek == termDayOfWeek) {
                    TrainingSession trainingSession = TrainingSession.builder()
                            .dateHeld(date)
                            .dayOfWeek(date.getDayOfWeek().toString())
                            .timeHeld(term.getStartTime())
                            .memberGroup(term.getMemberGroup())
                            .period(period)
                            .build();
                    trainingSessionDtos.add(trainingSession.convertToDto());
                    trainingSessionRepository.save(trainingSession);
                }
            }
        }
        return trainingSessionDtos;
    }

    private void deleteTrainingSessionsInGroupInPeriodIfGreaterThan(MemberGroup memberGroup, Period period, Integer fromDay) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findAllByMemberGroupAndPeriod(memberGroup, period);
        for (TrainingSession trainingSession : trainingSessions)
            if (trainingSession.getDateHeld().getDayOfMonth() >= fromDay) {
                deleteAttendancesForTrainingSession(trainingSession);
                trainingSessionRepository.delete(trainingSession);
            }
    }

    public void delete(Integer id) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(id);
        deleteAttendancesForTrainingSession(trainingSession);
        trainingSessionRepository.delete(trainingSession);
    }

    private void deleteAttendancesForTrainingSession(TrainingSession trainingSession) {
        List<Attendance> attendances = attendanceRepository.findAllByTrainingSession(trainingSession);
        for (Attendance attendance : attendances) {
            attendanceRepository.delete(attendance);
        }
    }

    public void deleteByMemberGroupAndPeriod(Integer groupId, Integer periodId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(groupId);
        Period period = periodRepository.getOne(periodId);
        List<TrainingSession> trainingSessions = trainingSessionRepository.findAllByMemberGroupAndPeriod(memberGroup, period);
        for (TrainingSession trainingSession : trainingSessions) {
            delete(trainingSession.getId());
        }
    }
}
