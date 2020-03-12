package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.AttendanceRequestDTO;
import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping(Routes.ATTENDANCES_BASE+"/training/{id}")
    public List<Attendance> getAllByTrainingSessionId(@PathVariable("id")Integer id)
    {
        return attendanceService.getAllByTrainingSessionId(id);
    }

    @GetMapping(Routes.ATTENDANCES_BASE+"/session/{sessionId}/user/{userId}")
    public Attendance getByTrainingSessionAndAppUser(@PathVariable("sessionId")Integer sessionId,
                                                     @PathVariable("userId")Integer userId)
    {
        return attendanceService.getByTrainingSessionAndAppUser(sessionId,userId);
    }

    @PostMapping(Routes.ATTENDANCES_BASE)
    public ResponseEntity<Attendance> insert(@RequestBody AttendanceRequestDTO attendanceRequestDTO)
    {
        return attendanceService.insert(attendanceRequestDTO);
    }

    @DeleteMapping(Routes.ATTENDANCES_BASE+"/{id}")
    public ResponseEntity<Attendance> delete(@PathVariable("id")Integer id)
    {
        return attendanceService.delete(id);
    }
}