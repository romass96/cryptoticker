package com.crypto.service;

import com.crypto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private SimpleMailMessage createMessage(String subject, String body, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(body);
        message.setTo(user.getEmail());
        return message;
    }

    public void sendResetTokenEmail(User user, String contextPath, String token) {
        String body = "To change password please go to the link above:\n"
                + contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        SimpleMailMessage message =  createMessage("Reset Password", body, user);
        emailSender.send(message);
    }

    public void sendVerificationEmail(User user, String contextPath, String token) {
        String body = "Please go to the link to verify your account:\n"
                + contextPath + "/user/verifyEmail?id=" + user.getId() + "&token=" + token;
        SimpleMailMessage message =  createMessage("Account verification", body, user);
        emailSender.send(message);
    }
}
