package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.CompetitionApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionApplicationRepository extends JpaRepository<CompetitionApplication,Integer> {
}
