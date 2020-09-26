package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.CompetitionDto;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.repositories.CompetitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionService {

    private CompetitionRepository competitionRepository;
    private AppUserService appUserService;
    private MailService mailService;

    public List<CompetitionDto> getAll() {
        return convertToDto(competitionRepository.findAll());
    }

    private List<CompetitionDto> convertToDto(List<Competition> competitions) {
        List<CompetitionDto> competitionsDto = new ArrayList<>();
        for (Competition competition : competitions) {
            competitionsDto.add(competition.convertToDto());
        }
        return competitionsDto;
    }

    public CompetitionDto getById(Integer id) {
        return competitionRepository.getOne(id).convertToDto();
    }

    public CompetitionDto insert(CompetitionDto competitionDto) {
        Competition competition = competitionDto.convertToEntity();
        return competitionRepository.save(competition).convertToDto();
    }

    public void inviteMembers(CompetitionDto competitionDto) {
        sendEmailToMembers(competitionDto);
    }

    private void sendEmailToMembers(CompetitionDto competitionDto) {
        List<String> memberEmails = appUserService.getEmailsOfUsers(appUserService.getAllMembers());
        mailService.sendCompetitionMessageToMember(memberEmails, competitionDto);
    }

    public CompetitionDto update(CompetitionDto competitionDto) {
        return insert(competitionDto);
    }

    public void delete(Integer id) {
        Competition competition = competitionRepository.getOne(id);
        competitionRepository.delete(competition);
    }
}
