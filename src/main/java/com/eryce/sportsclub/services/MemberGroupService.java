package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MemberGroupService {

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    public Collection<MemberGroup> getAll() {
        return memberGroupRepository.findAll();
    }

    public MemberGroup getById(Integer id) {
        return memberGroupRepository.getOne(id);
    }

    public ResponseEntity<MemberGroup> insertGroupIfNotExists(MemberGroup memberGroup) {
        if (exists(memberGroup))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        memberGroupRepository.save(memberGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<MemberGroup> updateGroupIfExists(MemberGroup memberGroup) {
        if(exists(memberGroup))
        {
            memberGroupRepository.save(memberGroup);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<MemberGroup> deleteGroupIfExists(MemberGroup memberGroup) {
        if(exists(memberGroup))
        {
            memberGroupRepository.delete(memberGroup);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean exists(MemberGroup memberGroup) {
        return existsByUsername(memberGroup.getName()) ||  memberGroupRepository.existsById(memberGroup.getId());
    }

    private boolean existsByUsername(String name) {
        return !(memberGroupRepository.findAllByNameContainingIgnoreCase(name).isEmpty());
    }
}
