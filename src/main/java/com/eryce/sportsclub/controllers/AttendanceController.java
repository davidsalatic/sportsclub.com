package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.models.Attendance;
import com.eryce.sportsclub.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/attendances")
    public Collection<Attendance> getAll()
    {
        return attendanceService.getAll();
    }

    @GetMapping("/attendances/{id}")
    public List<Attendance> findByAppUserId(@PathVariable("id")Integer id)
    {
        return attendanceService.findByAppUserId(id);
    }

    @GetMapping("/attendances/training/{id}")
    public List<Attendance> findByTrainingSessionId(@PathVariable("id")Integer id)
    {
        return attendanceService.findByTrainingSessionId(id);
    }

    @PostMapping("/attendances")
    public ResponseEntity<Attendance> insertAttendance(@RequestBody Attendance attendance)
    {
        return attendanceService.insertAttendanceIfNotExists(attendance);
    }

    @PutMapping("/attendances")
    public ResponseEntity<Attendance> updateAttendance(@RequestBody Attendance attendance)
    {
        return attendanceService.updateAttendanceIfExists(attendance);
    }

    @DeleteMapping("attendances")
    public ResponseEntity<Attendance> deleteAttendance(@RequestBody Attendance attendance)
    {
        return attendanceService.deleteAttendanceIfExists(attendance);
    }
}
