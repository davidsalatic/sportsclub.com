package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.AttendanceDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.AttendanceRepository;
import com.eryce.sportsclub.repositories.TrainingSessionRepository;
import com.eryce.sportsclub.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceService {

    private AttendanceRepository attendanceRepository;
    private AppUserRepository appUserRepository;
    private TrainingSessionRepository trainingSessionRepository;

    public List<AttendanceDto> getAllByTrainingSessionId(Integer id) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(id);
        List<Attendance> attendanceByTrainingSession = attendanceRepository.findAllByTrainingSession(trainingSession);
        return convertToDto(attendanceByTrainingSession);
    }

    public List<AttendanceDto> getAllByUserId(Integer userId) {
        AppUser appUser = appUserRepository.getOne(userId);
        List<Attendance> attendance = attendanceRepository.findAllByAppUser(appUser);
        return convertToDto(attendance);
    }

    private List<AttendanceDto> convertToDto(List<Attendance> attendance) {
        List<AttendanceDto> attendanceDto = new ArrayList<>();
        for (Attendance trainingAttendance : attendance) {
            attendanceDto.add(trainingAttendance.convertToDto());
        }
        return attendanceDto;
    }

    public AttendanceDto getByTrainingSessionIdAndUserId(Integer sessionId, Integer userId) {
        TrainingSession trainingSession = trainingSessionRepository.getOne(sessionId);
        AppUser appUser = appUserRepository.getOne(userId);
        Attendance attendance = attendanceRepository.findByTrainingSessionAndAppUser(trainingSession, appUser);
        return attendance.convertToDto();
    }

    public AttendanceDto insert(AttendanceDto attendanceDto) {
        return attendanceRepository.save(attendanceDto.convertToEntity()).convertToDto();
    }

    public void delete(Integer id) {
        attendanceRepository.deleteById(id);
    }
}
