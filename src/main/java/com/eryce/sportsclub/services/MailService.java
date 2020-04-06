package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.CommentRequestDTO;
import com.eryce.sportsclub.dto.CompetitionApplicationRequestDTO;
import com.eryce.sportsclub.dto.PostRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Competition;
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

    private final String FRONT_URL = "http://localhost:4200";


    public void sendRegistrationMessage(String recipientEmailAddress, String registrationToken)
    {
        String REGISTER_URL = FRONT_URL+"/register";
        String subject= "Complete your registration";
        String body = "To complete your registration, follow the link: "+ REGISTER_URL+"/"+registrationToken;
        List<String> recipients = new ArrayList<>();
        recipients.add(recipientEmailAddress);
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public void sendCompetitionMessageToMember(List<String> recipients, Competition competition)
    {
        String subject = competition.getName();
        String body=competition.getName()+"\n"+competition.getDescription()+"\n"+
                "Date of competition: "+competition.getDateHeld()+ " "+competition.getTimeHeld()+"\n"+
                "Location: "+competition.getLocation()+"\n\nTo apply for this competition, visit the next link: "+
                FRONT_URL+"/competitions/"+competition.getId()+"/apply";
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public void sendNewCompetitionApplicationMessageToStaff(List<String> recipients, CompetitionApplicationRequestDTO application) {
        String subject = application.getAppUser().getName()+" applied for "+application.getCompetition().getName();
        String body = application.getAppUser().getName()+" applied for the competition: "+application.getCompetition().getName();
        if(application.getMessage()!=null && application.getMessage().length()>0)
            body=body+" and left you a message: \n\n'"+application.getMessage()+"'.";
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public void sendUnpaidMembershipsListMessageToStaff(List<String> recipients, List<AppUser> membersWithInsufficientPayments) {
        String subject ="Monthly report on membership";
        int counter=1;
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Here is the list of users who haven't made enough payments in this month:\n\n");
        for (AppUser member : membersWithInsufficientPayments) {
            bodyBuilder.append(counter);
            bodyBuilder.append(".");
            bodyBuilder.append(member.getName());
            bodyBuilder.append(member.getSurname());
            bodyBuilder.append(", ");
            bodyBuilder.append(member.getMemberGroup().getName());
        }
        String body = bodyBuilder.toString();
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public void sendUnpaidMembershipMessageToMember(String recipient) {
        String subject ="Reminder - Unpaid membership";
        String body="You haven't made enough payments in this month.";
        List<String> recipients = new ArrayList<>();
        recipients.add(recipient);
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public void sendNewPostMessageToAllUsers(List<String> recipients, PostRequestDTO postRequestDTO) {
        String subject =postRequestDTO.getAppUser().getName()+" posted on Sports Club";
        String body= postRequestDTO.getTitle()+"\n\n"+postRequestDTO.getText();
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public void sendNewCommentMessageToParticipants(List<String> recipients, CommentRequestDTO commentRequestDTO) {
        String subject = "New comment by "+commentRequestDTO.getAppUser().getName()+ " "+commentRequestDTO.getAppUser().getSurname();
        String body=commentRequestDTO.getAppUser().getName()+ " "+commentRequestDTO.getAppUser().getSurname()+" commented on the post '"+
                commentRequestDTO.getPost().getTitle()+"'.\n\n'"+commentRequestDTO.getText()+"'";
        this.sendMessageAsync(createEmailMessage(recipients,subject,body));
    }

    public SimpleMailMessage createEmailMessage(List<String>recipients,String subject,String body)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(recipients.toArray(new String[0]));
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }

    private void sendMessageAsync(final SimpleMailMessage emailMessage)
    {
        Thread sendMailThread = new Thread(() -> javaMailSender.send(emailMessage));
        sendMailThread.start();
    }


}
