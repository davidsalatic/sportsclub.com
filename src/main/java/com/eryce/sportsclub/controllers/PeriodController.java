package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping(Routes.PERIOD_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public List<Period> getAll()
    {
        return periodService.getAll();
    }

    @GetMapping(Routes.PERIOD_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public Period getById(@PathVariable("id")Integer id)
    {
        return periodService.getById(id);
    }

    @GetMapping(Routes.PERIOD_BASE+"/month/{month}/year/{year}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public Period getByMonthAndYear(@PathVariable("month")Integer month,@PathVariable("year")Integer year)
    {
        return periodService.getByMonthAndYear(month,year);
    }

    @PostMapping(Routes.PERIOD_BASE)
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERS+"')")
    public ResponseEntity<Period>insert(@RequestBody Period period)
    {
        return periodService.insertIfNotExist(period);
    }
}
