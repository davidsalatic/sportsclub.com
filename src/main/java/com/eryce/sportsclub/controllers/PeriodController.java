package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Routes.PERIOD_BASE)
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping
    public List<Period> getAll()
    {
        return periodService.getAll();
    }

    @GetMapping("/{id}")
    public Period getById(@PathVariable("id")Integer id)
    {
        return periodService.getById(id);
    }

    @GetMapping("/month/{month}/year/{year}")
    public Period getByMonthAndYear(@PathVariable("month")Integer month,@PathVariable("year")Integer year)
    {
        return periodService.getByMonthAndYear(month,year);
    }

    @PostMapping
    public ResponseEntity<Period>insert(@RequestBody Period period)
    {
        return periodService.insertIfNotExist(period);
    }

    @PostMapping("notify")
    public ResponseEntity<Period> notifyManagersOfUnpaidMemberships(@RequestBody Period period)
    {
        return periodService.notifyManagersOfUnpaidMemberships(period);
    }
}
