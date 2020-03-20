package com.eryce.sportsclub.services;

import com.eryce.sportsclub.constants.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationMessage(String recipientEmailAddress, String registrationToken)
    {
        final SimpleMailMessage emailMessage = createRegistrationEmailMessage(recipientEmailAddress,registrationToken);

        this.sendMessageAsync(emailMessage);
    }

    private SimpleMailMessage createRegistrationEmailMessage(String recipient, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject("Complete your registration");
        simpleMailMessage.setText("To complete your registration, follow the link: "+ Routes.REGISTRATION_FRONT_LINK+"/"+token);
        return simpleMailMessage;
    }

    private void sendMessageAsync(final SimpleMailMessage emailMessage)
    {
        Thread sendMailThread = new Thread(() -> javaMailSender.send(emailMessage));
        sendMailThread.start();
    }
}
