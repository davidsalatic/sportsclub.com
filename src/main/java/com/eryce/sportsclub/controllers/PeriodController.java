package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.PeriodDto;
import com.eryce.sportsclub.services.PeriodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.eryce.sportsclub.constants.Routes.PERIOD_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(PERIOD_BASE)
@AllArgsConstructor
public class PeriodController {

    private PeriodService periodService;

    @GetMapping
    public ResponseEntity<List<PeriodDto>> getAll() {
        return ok(periodService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(periodService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new PeriodDto());
        }
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<PeriodDto> getByMonthAndYear(@PathVariable("month") Integer month, @PathVariable("year") Integer year) {
        try {
            return ok(periodService.getByMonthAndYear(month, year));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new PeriodDto());
        }
    }
}
