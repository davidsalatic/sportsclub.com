package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.CompetitionApplicationDto;
import com.eryce.sportsclub.dto.CompetitionDto;
import com.eryce.sportsclub.dto.AppUserDto;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final String SERVER_URL = "http://localhost";
    private final String FRONT_PORT = "4200";

    public void sendRegistrationMessage(AppUser appUser) {
        String REGISTER_URL = SERVER_URL + ":" + FRONT_PORT + "/register";
        String subject = "Complete your registration";
        String registrationToken = jwtTokenProvider.createToken(appUser);
        String body = "To complete your registration, follow the link: " + REGISTER_URL + "/" + registrationToken;
        List<String> recipients = new ArrayList<>();
        recipients.add(appUser.getUsername());
        this.sendMessageAsync(createEmailMessage(recipients, subject, body));
    }

    public void sendCompetitionMessageToMember(List<String> recipients, CompetitionDto competitionDto) {
        String subject = competitionDto.getName();
        String body = competitionDto.getName() + "\n" + competitionDto.getDescription() + "\n" +
                "Date of competition: " + competitionDto.getDateHeld() + " " + competitionDto.getTimeHeld() + "\n" +
                "Location: " + competitionDto.getLocation() + "\n\nTo apply for this competition, visit the next link: " +
                SERVER_URL + ":" + FRONT_PORT + "/competitions/" + competitionDto.getId() + "/apply";
        this.sendMessageAsync(createEmailMessage(recipients, subject, body));
    }

    public void sendNewCompetitionApplicationMessageToStaff(List<String> recipients, CompetitionApplicationDto application) {
        String subject = application.getAppUser().getName() + " applied for " + application.getCompetition().getName();
        String body = application.getAppUser().getName() + " applied for the competition: " + application.getCompetition().getName();
        if (application.getMessage() != null && application.getMessage().length() > 0)
            body = body + " and left you a message: \n\n'" + application.getMessage() + "'.";
        this.sendMessageAsync(createEmailMessage(recipients, subject, body));
    }

    public void sendCanceledCompetitionApplicationMessageToStaff(List<String> emails, CompetitionApplication competitionApplication) {
        String subject = competitionApplication.getAppUser().getName() + " canceled application for " + competitionApplication.getCompetition().getName();
        String body = competitionApplication.getAppUser().getName() + " " + competitionApplication.getAppUser().getSurname() +
                " canceled the application for the competition: " + competitionApplication.getCompetition().getName();
        this.sendMessageAsync(createEmailMessage(emails, subject, body));
    }

    public void sendUnpaidMembershipsListMessageToStaff(List<String> recipients, List<AppUserDto> membersWithInsufficientPayments) {
        String subject = "Monthly report on membership";
        int counter = 1;
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Here is the list of users who haven't made enough payments in this month:\n\n");
        for (AppUserDto member : membersWithInsufficientPayments) {
            bodyBuilder.append(counter);
            bodyBuilder.append(".");
            bodyBuilder.append(member.getName());
            bodyBuilder.append(member.getSurname());
            bodyBuilder.append(", ");
            bodyBuilder.append(member.getMemberGroup().getName());
        }
        String body = bodyBuilder.toString();
        this.sendMessageAsync(createEmailMessage(recipients, subject, body));
    }

    public void sendUnpaidMembershipMessageToMember(String recipient) {
        String subject = "Reminder - Unpaid membership";
        String body = "You haven't made enough payments in this month.";
        List<String> recipients = new ArrayList<>();
        recipients.add(recipient);
        this.sendMessageAsync(createEmailMessage(recipients, subject, body));
    }

    public SimpleMailMessage createEmailMessage(List<String> recipients, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(recipients.toArray(new String[0]));
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }

    private void sendMessageAsync(final SimpleMailMessage emailMessage) {
        Thread sendMailThread = new Thread(() -> javaMailSender.send(emailMessage));
        sendMailThread.start();
    }
}
