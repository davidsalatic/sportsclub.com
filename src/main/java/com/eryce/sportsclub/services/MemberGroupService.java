package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.repositories.AppUserRepository;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MemberGroupService {

    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    public Collection<MemberGroup> getAll() {
        return memberGroupRepository.findAll();
    }

    public MemberGroup getById(Integer id) {
        return memberGroupRepository.getOne(id);
    }

    public ResponseEntity<MemberGroup> insertGroupIfNotExists(MemberGroup memberGroup) {
        memberGroupRepository.save(memberGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<MemberGroup> updateGroupIfExists(MemberGroup memberGroup) {
        memberGroupRepository.save(memberGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<MemberGroup> deleteGroupIfExists(Integer id) {
        List<AppUser> appUsersInGroup = appUserRepository.findAllByMemberGroup(getById(id));
        for(AppUser appUser: appUsersInGroup)
            appUser.setMemberGroup(null);
        memberGroupRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
