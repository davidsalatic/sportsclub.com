package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {

    @Autowired
    private TermRepository termRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;

    public List<Term> getAllByMemberGroup(Integer memberGroupId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(memberGroupId);
        return termRepository.findAllByMemberGroup(memberGroup);
    }

    public ResponseEntity<Term> insert(Term term) {
        termRepository.save(term);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Term> delete(Integer id) {
        termRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
