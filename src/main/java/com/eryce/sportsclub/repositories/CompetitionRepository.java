package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition,Integer> {
}
