package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.models.TrainingSession;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.List;

@Service
public class MemberGroupService {

    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TrainingSessionService trainingSessionService;
    @Autowired
    private TermRepository termRepository;

    public Collection<MemberGroup> getAll() {
        List<MemberGroup> memberGroups = memberGroupRepository.findAll();
        return memberGroupRepository.findAll();
    }

    public MemberGroup getById(Integer id) {
        return memberGroupRepository.getOne(id);
    }

    public MemberGroup getByName(String name) {
        return memberGroupRepository.findByName(name.trim());
    }

    public ResponseEntity<MemberGroup> insert(MemberGroup memberGroup) {
        memberGroup.setName(memberGroup.getName().trim());
        memberGroupRepository.save(memberGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<MemberGroup> update(MemberGroup memberGroup) {
        return this.insert(memberGroup);
    }

    public ResponseEntity<MemberGroup> delete(Integer id) {
        MemberGroup memberGroup = getById(id);
        removeMemberGroupFromUsersInMemberGroup(memberGroup);
        deleteTrainingSessionsForGroup(memberGroup);
        deleteTermsForGroup(memberGroup);
        memberGroupRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void removeMemberGroupFromUsersInMemberGroup(MemberGroup memberGroup) {
        List<AppUser> appUsersInGroup = appUserRepository.findAllByMemberGroup(memberGroup);
        for(AppUser appUser: appUsersInGroup)
            appUser.setMemberGroup(null);
    }

    private void deleteTrainingSessionsForGroup(MemberGroup memberGroup) {
        List<TrainingSession> trainingSessions = trainingSessionService.getAllByMemberGroup(memberGroup.getId());
        for(TrainingSession trainingSession:trainingSessions)
        {
            trainingSessionService.delete(trainingSession.getId());
        }
    }

    private void deleteTermsForGroup(MemberGroup memberGroup) {
        List<Term> terms = termRepository.findAllByMemberGroup(memberGroup);
        for(Term term : terms)
            termRepository.delete(term);
    }
}
