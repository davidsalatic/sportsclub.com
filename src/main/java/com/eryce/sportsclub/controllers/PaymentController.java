package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Permissions;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.PaymentRequestDTO;
import com.eryce.sportsclub.models.Payment;
import com.eryce.sportsclub.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(Routes.PAYMENTS_BASE+"/membership/{membershipId}/user/{userId}")
    @PreAuthorize("hasAuthority('"+ Permissions.ACCESS_MEMBERSHIPS+"')")
    public List<Payment> getAllPaymentsForMembershipByAppUser(@PathVariable("membershipId") Integer membershipId,@PathVariable("userId")Integer userId)
    {
        return paymentService.getAllPaymentsForMembershipByAppUser(membershipId,userId);
    }

    @GetMapping(Routes.PAYMENTS_BASE+"/membership/{membershipId}")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public List<Payment> getAllPaymentsForMembership(@PathVariable("membershipId") Integer membershipId)
    {
        return paymentService.getAllPaymentsForMembership(membershipId);
    }

    @GetMapping(Routes.PAYMENTS_BASE+"/member/{memberId}")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public List<Payment> getAllPaymentsForAppUser(@PathVariable("memberId")Integer memberId)
    {
        return paymentService.getAllPaymentsForAppUser(memberId);
    }

    @GetMapping(Routes.PAYMENTS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public Payment getById(@PathVariable("id")Integer id){
        return paymentService.getById(id);
    }

    @PostMapping(Routes.PAYMENTS_BASE)
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public ResponseEntity<Payment> insert(@RequestBody PaymentRequestDTO paymentRequestDTO)
    {
        return paymentService.insert(paymentRequestDTO);
    }

    @DeleteMapping(Routes.PAYMENTS_BASE+"/{id}")
    @PreAuthorize("hasAuthority('"+Permissions.ACCESS_MEMBERSHIPS+"')")
    public ResponseEntity<Payment> delete(@PathVariable Integer id)
    {
        return paymentService.delete(id);
    }
}