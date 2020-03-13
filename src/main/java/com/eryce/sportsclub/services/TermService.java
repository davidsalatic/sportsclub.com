package com.eryce.sportsclub.services;

import com.eryce.sportsclub.models.Term;
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

    public List<Term> getAll() {
        return termRepository.findAll();
    }

    public ResponseEntity<Term> insert(Term term) {
        termRepository.save(term);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
