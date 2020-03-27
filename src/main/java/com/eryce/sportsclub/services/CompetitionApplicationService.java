package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.repositories.CompetitionApplicationRepository;
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

    public ResponseEntity<CompetitionApplication> insert(CompetitionApplication competitionApplication) {
        competitionApplicationRepository.save(competitionApplication);

        sendEmailToStaff(competitionApplication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendEmailToStaff(CompetitionApplication application)
    {
        List<AppUser> staff = appUserService.getAllStaff();

        List<String> emailAddresses = new ArrayList<>();
        for(AppUser staffMember : staff)
            emailAddresses.add(staffMember.getUsername());

        mailService.sendEmailToStaffRegardingNewCompetitionApplication(emailAddresses,application);
    }
}
