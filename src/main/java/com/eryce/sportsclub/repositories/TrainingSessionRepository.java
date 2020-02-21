package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.TrainingSession;
import org.springframework.data.repository.CrudRepository;

public interface TrainingSessionRepository extends CrudRepository<TrainingSession,Integer> {
}
