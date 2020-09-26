package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.TermDto;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.models.Term;
import com.eryce.sportsclub.repositories.MemberGroupRepository;
import com.eryce.sportsclub.repositories.TermRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TermService {

    private TermRepository termRepository;
    private MemberGroupRepository memberGroupRepository;

    public List<TermDto> getAllByMemberGroup(Integer memberGroupId) {
        MemberGroup memberGroup = memberGroupRepository.getOne(memberGroupId);
        return convertToDto(termRepository.findAllByMemberGroup(memberGroup));
    }

    private List<TermDto> convertToDto(List<Term> terms) {
        List<TermDto> termsDto = new ArrayList<>();
        for (Term term : terms) {
            termsDto.add(term.convertToDto());
        }
        return termsDto;
    }

    public TermDto getById(Integer id) {
        return termRepository.getOne(id).convertToDto();
    }

    public TermDto insert(TermDto termDto) {
        Term term = termRepository.save(termDto.convertToEntity());
        return term.convertToDto();
    }

    public TermDto update(TermDto termDto) {
        return insert(termDto);
    }

    public ResponseEntity<Term> delete(Integer id) {
        termRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
