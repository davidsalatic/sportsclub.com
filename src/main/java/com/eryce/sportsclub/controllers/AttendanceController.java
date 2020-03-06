package com.eryce.sportsclub.controllers;

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

    @GetMapping("/attendances/training/{id}")
    public List<Attendance> getAllByTrainingSessionId(@PathVariable("id")Integer id)
    {
        return attendanceService.getAllByTrainingSessionId(id);
    }

    @GetMapping("/attendances/session/{sessionId}/user/{userId}")
    public Attendance getByTrainingSessionAndAppUser(@PathVariable("sessionId")Integer sessionId,
                                                     @PathVariable("userId")Integer userId)
    {
        return attendanceService.getByTrainingSessionAndAppUser(sessionId,userId);
    }

    @PostMapping("/attendances")
    public ResponseEntity<Attendance> insert(@RequestBody Attendance attendance)
    {
        return attendanceService.insert(attendance);
    }

    @DeleteMapping("attendances/{id}")
    public ResponseEntity<Attendance> delete(@PathVariable("id")Integer id)
    {
        return attendanceService.delete(id);
    }
}