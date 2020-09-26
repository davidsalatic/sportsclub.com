package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.AttendanceDto;
import com.eryce.sportsclub.services.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_ANY_ROLE;
import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.ATTENDANCE_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(ATTENDANCE_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class AttendanceController {

    private AttendanceService attendanceService;

    @GetMapping("/training/{id}")
    public ResponseEntity<List<AttendanceDto>> getAllByTrainingSessionId(@PathVariable("id") Integer id) {
        try {
            return ok(attendanceService.getAllByTrainingSessionId(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/member/{id}")
    @PreAuthorize(HAS_ANY_ROLE)
    public ResponseEntity<List<AttendanceDto>> getAllByUserId(@PathVariable("id") Integer userId) {
        try {
            return ok(attendanceService.getAllByUserId(userId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/session/{sessionId}/user/{userId}")
    public ResponseEntity<AttendanceDto> getByTrainingSessionIdAndUserId(@PathVariable("sessionId") Integer sessionId,
                                                                         @PathVariable("userId") Integer userId) {
        try {
            return ok(attendanceService.getByTrainingSessionIdAndUserId(sessionId, userId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new AttendanceDto());
        }
    }

    @PostMapping
    public ResponseEntity<AttendanceDto> insert(@RequestBody AttendanceDto attendanceDto) {
        return ok(attendanceService.insert(attendanceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        try {
            attendanceService.delete(id);
            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}