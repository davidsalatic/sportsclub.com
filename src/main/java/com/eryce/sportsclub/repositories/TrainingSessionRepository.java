package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Integer> {

    List<TrainingSession> findAllByMonth(Integer month);
    List<TrainingSession> findAllByDateHeld(LocalDate dateHeld);
    List<TrainingSession> findAllByTimeHeld(LocalTime timeHeld);

}
