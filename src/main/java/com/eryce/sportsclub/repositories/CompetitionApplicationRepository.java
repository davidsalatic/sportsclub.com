package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.models.CompetitionApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionApplicationRepository extends JpaRepository<CompetitionApplication, Integer> {
    CompetitionApplication findByCompetitionAndAppUser(Competition competition, AppUser appUser);

    List<CompetitionApplication> findAllByCompetition(Competition competition);

    List<CompetitionApplication> findAllByAppUser(AppUser appUser);
}
