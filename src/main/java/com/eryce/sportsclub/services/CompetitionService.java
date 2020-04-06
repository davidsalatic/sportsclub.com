package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private MailService mailService;

    public List<Competition> getAll() {
        return competitionRepository.findAll();
    }

    public Competition getById(Integer id) {
        return competitionRepository.getOne(id);
    }

    public ResponseEntity<Competition> insert(Competition competition) {
        competitionRepository.save(competition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Competition> inviteMembers(Competition competition) {
        sendEmailToMembers(competition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendEmailToMembers(Competition competition)
    {
        List<String> memberEmails = appUserService.getEmails(appUserService.getAllMembers());
        mailService.sendCompetitionMessageToMember(memberEmails,competition);
    }

    public ResponseEntity<Competition> update(Competition competition) {
        this.competitionRepository.save(competition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Competition> delete(Integer id) {
        competitionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
