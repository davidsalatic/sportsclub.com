package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Routes;
import java.util.List;

import com.eryce.sportsclub.models.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final String REGISTER_URL = Routes.FRONT_URL+"/register";

    public void sendRegistrationMessage(String recipientEmailAddress, String registrationToken)
    {
        final SimpleMailMessage emailMessage = createRegistrationEmailMessage(recipientEmailAddress,registrationToken);
        this.sendMessageAsync(emailMessage);
    }

    private SimpleMailMessage createRegistrationEmailMessage(String recipient, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject("Complete your registration");
        simpleMailMessage.setText("To complete your registration, follow the link: "+ this.REGISTER_URL+"/"+token);
        return simpleMailMessage;
    }

    public void sendCompetitionMessage(List<String> recipients, Competition competition)
    {
        final SimpleMailMessage competitionMessage = createCompetitionEmailMessage(recipients,competition);
        this.sendMessageAsync(competitionMessage);
    }

    private SimpleMailMessage createCompetitionEmailMessage(List<String> recipients, Competition competition) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipients.toArray(new String[recipients.size()]));
        simpleMailMessage.setSubject(competition.getName());
        simpleMailMessage.setText(competition.getName()+"\n"+competition.getDescription()+"\n"+
                "Date of competition: "+competition.getDateHeld()+ " "+competition.getTimeHeld()+"\n"+
                "Location: "+competition.getLocation()+"\n\nTo apply for this competition, visit the next link:"+
                Routes.FRONT_URL+"/competitions/"+competition.getId()+"/apply");
        return simpleMailMessage;
    }

    private void sendMessageAsync(final SimpleMailMessage emailMessage)
    {
        Thread sendMailThread = new Thread(() -> javaMailSender.send(emailMessage));
        sendMailThread.start();
    }
}
