package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.CompetitionApplicationRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.repositories.CompetitionApplicationRepository;
import com.eryce.sportsclub.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionApplicationService {

    @Autowired
    private CompetitionApplicationRepository competitionApplicationRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CompetitionRepository competitionRepository;

    public List<CompetitionApplication> getByCompetition(Integer competitionId) {
        Competition competition = competitionRepository.getOne(competitionId);
        return competitionApplicationRepository.findAllByCompetition(competition);
    }

    public CompetitionApplication getByCompetitionAndAppUser(Integer competitionId, Integer appUserId) {
        Competition competition = competitionRepository.getOne(competitionId);
        AppUser appUser = appUserService.getById(appUserId);
        return competitionApplicationRepository.findByCompetitionAndAppUser(competition,appUser);
    }

    public ResponseEntity<CompetitionApplication> insert(CompetitionApplicationRequestDTO competitionApplication) {
        competitionApplicationRepository.save(competitionApplication.generateCompetitionApplication());

        sendEmailToStaff(competitionApplication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendEmailToStaff(CompetitionApplicationRequestDTO application)
    {
        List<String> emailAddresses = appUserService.getEmails(appUserService.getAllStaff());
        mailService.sendNewCompetitionApplicationMessageToStaff(emailAddresses,application);
    }

    public ResponseEntity<CompetitionApplication> delete(Integer id) {
        this.competitionApplicationRepository.deleteById(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
