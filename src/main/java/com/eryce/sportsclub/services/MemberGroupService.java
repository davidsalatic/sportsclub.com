package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.MemberGroupDto;
import com.eryce.sportsclub.dto.TrainingSessionDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.TermRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberGroupService {

    private MemberGroupRepository memberGroupRepository;
    private AppUserRepository appUserRepository;
    private TrainingSessionService trainingSessionService;
    private TermRepository termRepository;

    public List<MemberGroupDto> getAll() {
        return convertToDto(memberGroupRepository.findAll());
    }

    private List<MemberGroupDto> convertToDto(List<MemberGroup> memberGroups) {
        List<MemberGroupDto> memberGroupsDto = new ArrayList<>();
        for (MemberGroup memberGroup : memberGroups) {
            memberGroupsDto.add(memberGroup.convertToDto());
        }
        return memberGroupsDto;
    }

    public MemberGroupDto getById(Integer id) {
        return memberGroupRepository.getOne(id).convertToDto();
    }

    public MemberGroupDto getByName(String name) {
        MemberGroup memberGroup = memberGroupRepository.findByName(name.trim());
        if (memberGroup != null) {
            return memberGroup.convertToDto();
        }
        return null;
    }

    public MemberGroupDto insert(MemberGroupDto memberGroupDto) {
        memberGroupDto.setName(memberGroupDto.getName().trim());
        MemberGroup memberGroup = memberGroupRepository.save(memberGroupDto.convertToEntity());
        return memberGroup.convertToDto();
    }

    public MemberGroupDto update(MemberGroupDto memberGroupDto) {
        return insert(memberGroupDto);
    }

    public void delete(Integer id) {
        MemberGroup memberGroup = memberGroupRepository.getOne(id);
        removeMemberGroupFromUsersInMemberGroup(memberGroup);
        deleteTrainingSessionsForGroup(memberGroup);
        deleteTermsForGroup(memberGroup);
        memberGroupRepository.deleteById(id);
    }

    private void removeMemberGroupFromUsersInMemberGroup(MemberGroup memberGroup) {
        List<AppUser> usersInGroup = appUserRepository.findAllByMemberGroup(memberGroup);
        for (AppUser appUser : usersInGroup) {
            appUser.setMemberGroup(null);
        }
    }

    private void deleteTrainingSessionsForGroup(MemberGroup memberGroup) {
        List<TrainingSessionDto> trainingSessions = trainingSessionService.getAllByMemberGroup(memberGroup.getId());
        for (TrainingSessionDto trainingSession : trainingSessions) {
            trainingSessionService.delete(trainingSession.getId());
        }
    }

    private void deleteTermsForGroup(MemberGroup memberGroup) {
        List<Term> terms = termRepository.findAllByMemberGroup(memberGroup);
        for (Term term : terms) {
            termRepository.delete(term);
        }
    }
}
