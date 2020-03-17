package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping(Routes.PERIOD_BASE+"/month/{month}/year/{year}")
    public Period getByMonthAndYear(@PathVariable("month")Integer month,@PathVariable("year")Integer year)
    {
        return periodService.getByMonthAndYear(month,year);
    }

    @PostMapping(Routes.PERIOD_BASE)
    public ResponseEntity<Period>insert(@RequestBody Period period)
    {
        return periodService.insertIfNotExist(period);
    }
}
