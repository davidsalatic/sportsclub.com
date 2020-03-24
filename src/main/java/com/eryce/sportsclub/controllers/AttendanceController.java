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
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping(Routes.ATTENDANCES_BASE+"/training/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public List<Attendance> getAllByTrainingSessionId(@PathVariable("id")Integer id)
    {
        return attendanceService.getAllByTrainingSessionId(id);
    }

    @GetMapping(Routes.ATTENDANCES_BASE+"/member/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_SELF+"')")
    public List<Attendance> getAllByAppUser(@PathVariable ("id") Integer appUserId)
    {
        return this.attendanceService.getByAppUser(appUserId);
    }

    @GetMapping(Routes.ATTENDANCES_BASE+"/session/{sessionId}/user/{userId}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public Attendance getByTrainingSessionAndAppUser(@PathVariable("sessionId")Integer sessionId,
                                                     @PathVariable("userId")Integer userId)
    {
        return attendanceService.getByTrainingSessionAndAppUser(sessionId,userId);
    }

    @PostMapping(Routes.ATTENDANCES_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public ResponseEntity<Attendance> insert(@RequestBody AttendanceRequestDTO attendanceRequestDTO)
    {
        return attendanceService.insert(attendanceRequestDTO);
    }

    @DeleteMapping(Routes.ATTENDANCES_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_TRAINING_SESSIONS+"')")
    public ResponseEntity<Attendance> delete(@PathVariable("id")Integer id)
    {
        return attendanceService.delete(id);
    }
}