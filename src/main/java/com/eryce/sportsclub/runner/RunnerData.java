package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.models.ClubMember;
import com.eryce.sportsclub.models.MemberGroup;
import com.eryce.sportsclub.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private AppUserRepository appUserRepository;
    private AttendanceRepository attendanceRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    private MembershipRepository membershipRepository;
    private PaymentRepository paymentRepository;
    private TrainingSessionRepository trainingSessionRepository;

    @Override
    public void run(String... args) {

        insertTestData();
    }

    private void insertTestData() {
        MemberGroup memberGroup = new MemberGroup("grupa1");
        MemberGroup memberGroup1 = new MemberGroup("grupa2");

        ClubMember clubMember = new ClubMember("davidsalatic","sifra123","David","Salatic");
        clubMember.setMemberGroup(memberGroup);

        memberGroupRepository.save(memberGroup);

        clubMember.setPhoneNumber("0611868582");
        appUserRepository.save(clubMember);

        System.out.println("Started in Runner");
    }
}
