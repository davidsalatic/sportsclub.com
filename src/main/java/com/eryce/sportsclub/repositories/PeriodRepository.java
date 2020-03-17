package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period,Integer> {
    Period findByMonthAndYear(Integer month, Integer year);
}
