package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Integer> {
}
