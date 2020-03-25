package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.AttendanceRequestDTO;
import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.ATTENDANCES_BASE)
@PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/training/{id}")
    public List<Attendance> getAllByTrainingSessionId(@PathVariable("id")Integer id)
    {
        return attendanceService.getAllByTrainingSessionId(id);
    }

    @GetMapping("/member/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_SELF+"')")
    public List<Attendance> getAllByAppUser(@PathVariable ("id") Integer appUserId)
    {
        return this.attendanceService.getByAppUser(appUserId);
    }

    @GetMapping("/session/{sessionId}/user/{userId}")
    public Attendance getByTrainingSessionAndAppUser(@PathVariable("sessionId")Integer sessionId,
                                                     @PathVariable("userId")Integer userId)
    {
        return attendanceService.getByTrainingSessionAndAppUser(sessionId,userId);
    }

    @PostMapping
    public ResponseEntity<Attendance> insert(@RequestBody AttendanceRequestDTO attendanceRequestDTO)
    {
        return attendanceService.insert(attendanceRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Attendance> delete(@PathVariable("id")Integer id)
    {
        return attendanceService.delete(id);
    }
}