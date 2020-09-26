package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.dto.PaymentDto;
import com.eryce.sportsclub.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.eryce.sportsclub.constants.Authorize.HAS_MANAGER_OR_MEMBER_ROLE;
import static com.eryce.sportsclub.constants.Authorize.HAS_MANAGER_ROLE;
import static com.eryce.sportsclub.constants.Routes.PAYMENTS_BASE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(PAYMENTS_BASE)
@PreAuthorize(HAS_MANAGER_ROLE)
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping("/membership/{membershipId}/user/{userId}")
    public ResponseEntity<List<PaymentDto>> getAllPaymentsForMembershipByAppUser(@PathVariable("membershipId") Integer membershipId, @PathVariable("userId") Integer userId) {
        try {
            return ok(paymentService.getAllPaymentsForMembershipByAppUser(membershipId, userId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/membership/{membershipId}")
    public ResponseEntity<List<PaymentDto>> getAllPaymentsForMembership(@PathVariable("membershipId") Integer membershipId) {
        try {
            return ok(paymentService.getAllPaymentsForMembership(membershipId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize(HAS_MANAGER_OR_MEMBER_ROLE)
    public ResponseEntity<List<PaymentDto>> getAllPaymentsForAppUser(@PathVariable("memberId") Integer memberId) {
        try {
            return ok(paymentService.getAllPaymentsForAppUser(memberId));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable("id") Integer id) {
        try {
            return ok(paymentService.getById(id));
        } catch (EntityNotFoundException exception) {
            return badRequest().body(new PaymentDto());
        }
    }

    @PostMapping
    public ResponseEntity<PaymentDto> insert(@RequestBody PaymentDto paymentDto) {
        return ok(paymentService.insert(paymentDto));
    }

    @PutMapping
    public ResponseEntity<PaymentDto> update(@RequestBody PaymentDto paymentDto) {
        return ok(paymentService.update(paymentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        try {
            paymentService.delete(id);
            return ok(id);
        } catch (EntityNotFoundException exception) {
            return badRequest().body(id);
        }
    }
}