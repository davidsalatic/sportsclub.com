package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Routes;
import java.util.List;

import com.eryce.sportsclub.dto.CompetitionApplicationRequestDTO;
import com.eryce.sportsclub.models.AppUser;
import com.eryce.sportsclub.models.Competition;
import com.eryce.sportsclub.models.CompetitionApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
                "Location: "+competition.getLocation()+"\n\nTo apply for this competition, visit the next link: "+
                Routes.FRONT_URL+"/competitions/"+competition.getId()+"/apply");
        return simpleMailMessage;
    }

    public void sendEmailToStaffRegardingNewCompetitionApplication(List<String> recipients, CompetitionApplicationRequestDTO application) {
        final SimpleMailMessage applicationMessage = createNewCompetitionApplicationEmailMessage(recipients,application);
        this.sendMessageAsync(applicationMessage);
    }

    private SimpleMailMessage createNewCompetitionApplicationEmailMessage(List<String> recipients, CompetitionApplicationRequestDTO application) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipients.toArray(new String[recipients.size()]));
        simpleMailMessage.setSubject(application.getAppUser().getName()+" applied for "+application.getCompetition().getName());
        String text = application.getAppUser().getName()+" applied for the competition: "+application.getCompetition().getName();
        if(application.getMessage()!=null && application.getMessage().length()>0)
            text=text+" and left you a message: \n\n'"+application.getMessage()+"'.";
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    public void sendUnpaidMembershipsListMessageToStaff(List<String> staffEmails, List<AppUser> membersWithInsufficientPayments) {
        final SimpleMailMessage unpaidMemberships = createUnpaidMembershipsMessageForStaff(staffEmails,membersWithInsufficientPayments);
        this.sendMessageAsync(unpaidMemberships);
    }

    private SimpleMailMessage createUnpaidMembershipsMessageForStaff(List<String> recipients, List<AppUser> membersWithInsufficientPayments) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipients.toArray(new String[recipients.size()]));
        simpleMailMessage.setSubject("Monthly report on membership");
        String text = "Here is the list of users who haven't made enough payments in this month:\n\n";
        int counter=1;
        for(AppUser member : membersWithInsufficientPayments)
        {
            text+=(counter+"."+member.getName()+ " "+member.getSurname()+", "+member.getMemberGroup().getName());
            counter++;
        }

        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    public void sendUnpaidMembershipMessageToMember(String username) {
        final SimpleMailMessage unpaidMembership = createUnpaidMembershipForMember(username);
        this.sendMessageAsync(unpaidMembership);
    }

    private SimpleMailMessage createUnpaidMembershipForMember(String username) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(username);
        simpleMailMessage.setSubject("Reminder - Unpaid membership");
        simpleMailMessage.setText("You haven't made enough payments in this month.");

        return simpleMailMessage;
    }

    private void sendMessageAsync(final SimpleMailMessage emailMessage)
    {
        Thread sendMailThread = new Thread(() -> javaMailSender.send(emailMessage));
        sendMailThread.start();
    }
}
