package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.CompetitionApplicationDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.repositories.CompetitionApplicationRepository;
import com.eryce.sportsclub.repositories.CompetitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionApplicationService {

    private CompetitionApplicationRepository competitionApplicationRepository;
    private AppUserService appUserService;
    private MailService mailService;
    private CompetitionRepository competitionRepository;

    public List<CompetitionApplicationDto> getByCompetition(Integer competitionId) {
        Competition competition = competitionRepository.getOne(competitionId);
        return convertToDto(competitionApplicationRepository.findAllByCompetition(competition));
    }

    private List<CompetitionApplicationDto> convertToDto(List<CompetitionApplication> competitionApplications) {
        List<CompetitionApplicationDto> competitionApplicationsDto = new ArrayList<>();
        for (CompetitionApplication competitionApplication : competitionApplications) {
            competitionApplicationsDto.add(competitionApplication.convertToDto());
        }
        return competitionApplicationsDto;
    }

    public CompetitionApplicationDto getByCompetitionAndUser(Integer competitionId, Integer userId) {
        Competition competition = competitionRepository.getOne(competitionId);
        AppUser appUser = appUserService.getById(userId).convertToEntity();
        return competitionApplicationRepository.findByCompetitionAndAppUser(competition, appUser).convertToDto();
    }

    public CompetitionApplicationDto insert(CompetitionApplicationDto competitionApplicationDto) {
        if (competitionApplicationExists(competitionApplicationDto)) {
            throw new EntityExistsException();
        }
        List<String> emailAddresses = appUserService.getEmailsOfUsers(appUserService.getAllStaff());
        mailService.sendNewCompetitionApplicationMessageToStaff(emailAddresses, competitionApplicationDto);

        return competitionApplicationRepository.save(competitionApplicationDto.convertToEntity()).convertToDto();
    }

    private boolean competitionApplicationExists(CompetitionApplicationDto competitionApplicationDto) {
        Competition competition = competitionApplicationDto.getCompetition().convertToEntity();
        AppUser appUser = competitionApplicationDto.getAppUser().convertToEntity();
        return competitionApplicationRepository.findByCompetitionAndAppUser(competition, appUser) != null;
    }

    public void delete(Integer id) {
        CompetitionApplication competitionApplication = competitionApplicationRepository.getOne(id);
        competitionApplicationRepository.delete(competitionApplication);
        mailService.sendCanceledCompetitionApplicationMessageToStaff(appUserService.getEmailsOfUsers(appUserService.getAllStaff()), competitionApplication);
    }
}
