package com.eryce.sportsclub.runner;

import com.eryce.sportsclub.constants.Roles;
import com.eryce.sportsclub.controllers.*;
import com.eryce.sportsclub.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class RunnerData implements CommandLineRunner {

    @Autowired
    private RoleController roleController;
    @Autowired
    private MemberGroupController memberGroupController;
    @Autowired
    private AppUserController appUserController;
    @Autowired
    private MembershipController membershipController;
    @Autowired
    private TrainingSessionController trainingSessionController;
    @Autowired
    private AttendanceController attendanceController;
    @Autowired
    private PaymentController paymentController;
    @Autowired
    private PermissionController permissionController;

    @Override
    public void run(String... args) {
//        insertTestData();
        System.out.println("***************APP STARTED*****************");
    }

    private void insertTestData() {
        //GROUPS
        MemberGroup memberGroup  = new MemberGroup();
        memberGroup.setName("1.Grupa");

        MemberGroup memberGroup1 = new MemberGroup();
        memberGroup1    .setName("2.Grupa");

        //PERMISIONS
        Permission permission = new Permission();
        permission.setName("CAN_SEE_FINANCES");

        Permission permission1 = new Permission();
        permission1.setName("CAN_SEE_USER_STUFF");

        Permission permission2 = new Permission();
        permission2.setName("CAN_COACH_MEMBERS");

        //ROLES
        Role role = new Role();
        role.setName(Roles.CLUB_MEMBER);
        role.getPermissions().add(permission1);

        Role role1 = new Role();
        role1.setName(Roles.COACH);
        role1.getPermissions().add(permission2);
        role1.getPermissions().add(permission1);

        Role role2 = new Role();
        role2.setName(Roles.MANAGER);
        role2.getPermissions().add(permission);
        role2.getPermissions().add(permission1);
        role2.getPermissions().add(permission2);

        //USERS
        AppUser manager = new AppUser();
        manager.setName("David");
        manager.setSurname("Salatic");
        manager.setUsername("davidsalatic");
        manager.setPassword("david123");
        manager.setAddress("Vladike Cirica 10");
        manager.setDateJoined(LocalDate.now());
        manager.setJmbg("1003997800160");
        manager.setRole(role2);

        AppUser member = new AppUser();
        member.setName("Milos");
        member.setSurname("Milosevic");
        member.setUsername("milosmil");
        member.setPassword("mil1");
        member.setAddress("Bul Knez 2");
        member.setDateJoined(LocalDate.now());
        member.setJmbg("1312312312");
        member.setMemberGroup(memberGroup);
        member.setRole(role);

        AppUser member1 = new AppUser();
        member1.setName("Mina");
        member1.setSurname("Minic");
        member1.setUsername("minamin");
        member1.setPassword("minnn123");
        member1.setAddress("Cirpanova 4");
        member1.setDateJoined(LocalDate.now());
        member1.setJmbg("155531244");
        member1.setMemberGroup(memberGroup);
        member1.setRole(role);

        AppUser member2 = new AppUser();
        member2.setName("Nikola");
        member2.setSurname("Nikolic");
        member2.setUsername("nikinikis");
        member2.setPassword("nikas123");
        member2.setAddress("Neka uica 4");
        member2.setDateJoined(LocalDate.now());
        member2.setJmbg("1805983091");
        member2.setMemberGroup(memberGroup1);
        member2.setRole(role);

        AppUser member3 = new AppUser();
        member3.setName("Nevena");
        member3.setSurname("Miliciev");
        member3.setUsername("nekis");
        member3.setPassword("sifras1");
        member3.setAddress("Djordj Niks 4");
        member3.setDateJoined(LocalDate.now());
        member3.setJmbg("5122551");
        member3.setRole(role1);

        //MEMBERSHIPS
        Membership membership = new Membership();
        membership.setMonth(1);
        membership.setPrice(2000);
        membership.setYear(2020);

        Membership membership1 = new Membership();
        membership1.setMonth(2);
        membership1.setPrice(2000);
        membership1.setYear(2020);

        //TRAININGS
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setDateHeld(LocalDate.now());
        trainingSession.setTimeHeld(LocalTime.now());
        trainingSession.setMemberGroup(memberGroup);
        trainingSession.setCreator(member3);

        TrainingSession trainingSession1 = new TrainingSession();
        trainingSession1.setDateHeld(LocalDate.now().plusDays(2));
        trainingSession1.setTimeHeld(LocalTime.now().plusHours(1));
        trainingSession1.setMemberGroup(memberGroup);
        trainingSession1.setCreator(member3);

        TrainingSession trainingSession2 = new TrainingSession();
        trainingSession2.setDateHeld(LocalDate.now().plusDays(1));
        trainingSession2.setTimeHeld(LocalTime.now().plusHours(2));
        trainingSession2.setMemberGroup(memberGroup);
        trainingSession2.setCreator(member3);

        //ATTENDANCES
        Attendance attendance = new Attendance();
        attendance.setAppUser(member);
        attendance.setTrainingSession(trainingSession);

        Attendance attendance1 = new Attendance();
        attendance1.setTrainingSession(trainingSession);
        attendance1.setAppUser(member1);

        Attendance attendance2 = new Attendance();
        attendance2.setAppUser(member2);
        attendance2.setTrainingSession(trainingSession);

        Attendance attendance3 = new Attendance();
        attendance3.setTrainingSession(trainingSession1);
        attendance3.setAppUser(member2);

        Attendance attendance4 = new Attendance();
        attendance4.setTrainingSession(trainingSession1);
        attendance4.setAppUser(member);

        Attendance attendance5 = new Attendance();
        attendance5.setTrainingSession(trainingSession2);
        attendance5.setAppUser(member);

        //PAYMENTS
        Payment payment = new Payment();
        payment.setAmount(1000);
        payment.setAppUser(member);
        payment.setMembership(membership);
        payment.setDayOfMonth(14);
        payment.setMonthOfPayment(01);
        payment.setYearOfPayment(2020);
        Payment payment1 = new Payment();
        payment1.setAmount(1000);
        payment1.setAppUser(member);
        payment1.setMembership(membership);
        payment1.setDayOfMonth(16);
        payment1.setMonthOfPayment(01);
        payment1.setYearOfPayment(2020);

        Payment payment2 = new Payment();
        payment2.setYearOfPayment(2020);
        payment2.setMonthOfPayment(01);
        payment2.setDayOfMonth(25);
        payment2.setMembership(membership);
        payment2.setAppUser(member1);
        payment2.setAmount(2000);

        Payment payment3 = new Payment();
        payment3.setYearOfPayment(2020);
        payment3.setMonthOfPayment(02);
        payment3.setDayOfMonth(01);
        payment3.setMembership(membership1);
        payment3.setAppUser(member);
        payment3.setAmount(2000);

        //REPOSITORIES

        permissionController.insertPermission(permission);
        permissionController.insertPermission(permission1);
        permissionController.insertPermission(permission2);

        memberGroupController.insertGroup(memberGroup);
        memberGroupController.insertGroup(memberGroup1);

        roleController.insertRole(role);
        roleController.insertRole(role1);
        roleController.insertRole(role2);

        appUserController.insertUser(manager);
        appUserController.insertUser(member);
        appUserController.insertUser(member1);
        appUserController.insertUser(member2);
        appUserController.insertUser(member3);

        membershipController.insertMembership(membership);
        membershipController.insertMembership(membership1);

        trainingSessionController.insertTrainingSession(trainingSession);
        trainingSessionController.insertTrainingSession(trainingSession1);
        trainingSessionController.insertTrainingSession(trainingSession2);

        attendanceController.insertAttendance(attendance);
        attendanceController.insertAttendance(attendance1);
        attendanceController.insertAttendance(attendance2);
        attendanceController.insertAttendance(attendance3);
        attendanceController.insertAttendance(attendance4);
        attendanceController.insertAttendance(attendance5);

        paymentController.insertPayment(payment);
        paymentController.insertPayment(payment1);
        paymentController.insertPayment(payment2);
        paymentController.insertPayment(payment3);
    }
}