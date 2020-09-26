package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.TermDto;
import com.eryce.sportsclub.services.TermService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.TERM_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(TERM_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class TermController {

    private TermService termService;

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<TermDto>> getAllByMemberGroup(@PathVariable("groupId") Integer memberGroupId) {
        try {
            return ok(termService.getAllByMemberGroup(memberGroupId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<TermDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(termService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new TermDto());
        }
    }

    @PostMapping
    public ResponseEntity<TermDto> insert(@RequestBody TermDto termDto) {
        return ok(termService.insert(termDto));
    }

    @PutMapping
    public ResponseEntity<TermDto> update(@RequestBody TermDto termDto) {
        return ok(termService.update(termDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") Integer id) {
        try {
            termService.delete(id);
            return ok(id);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(id);
        }
    }
}
