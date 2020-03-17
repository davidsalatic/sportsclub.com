package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Period;
import com.eryce.sportsclub.repositories.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {

    @Autowired
    private PeriodRepository periodRepository;

    public List<Period> getAll() {
        return periodRepository.findAll();
    }

    public Period getById(Integer id) {
        return periodRepository.getOne(id);
    }

    public Period getByMonthAndYear(Integer month, Integer year) {
        return periodRepository.findByMonthAndYear(month,year);
    }

    public ResponseEntity<Period> insertIfNotExist(Period period) {
        if(this.getByMonthAndYear(period.getMonth(),period.getYear())==null)
        {
            this.periodRepository.save(period);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
