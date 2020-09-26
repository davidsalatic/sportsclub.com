package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.MemberGroupDto;
import com.eryce.sportsclub.services.MemberGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_COACH_OR_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.MEMBER_GROUPS_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(MEMBER_GROUPS_BASE)
@PreAuthorize(HAS_COACH_OR_MANAGER_ROLE)
@AllArgsConstructor
public class MemberGroupController {

    private MemberGroupService memberGroupService;

    @GetMapping
    public ResponseEntity<List<MemberGroupDto>> getAll() {
        return ok(memberGroupService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberGroupDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(memberGroupService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new MemberGroupDto());
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<MemberGroupDto> getByName(@RequestParam String name) {
        return ok(memberGroupService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<MemberGroupDto> insert(@RequestBody MemberGroupDto memberGroupDto) {
        return ok(memberGroupService.insert(memberGroupDto));
    }

    @PutMapping
    public ResponseEntity<MemberGroupDto> update(@RequestBody MemberGroupDto memberGroupDto) {
        return ok(memberGroupService.update(memberGroupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        try {
            memberGroupService.delete(id);
            return ok(id);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(id);
        }
    }
}
