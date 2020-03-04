package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Integer> {

    List<TrainingSession> findAllByMonthAndYear(Integer month,Integer year);
}
