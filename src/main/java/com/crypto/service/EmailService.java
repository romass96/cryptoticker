package com.crypto.service;

import com.crypto.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    private SimpleMailMessage createMessage(String subject, String body, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        return message;
    }

    public void sendResetTokenEmail(User user, String contextPath, String token) {
        String body = "To change password please go to the link above:\n"
                + contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        SimpleMailMessage message =  createMessage("Reset Password", body, user.getEmail());
        emailSender.send(message);
        logger.info("Reset token is sent to " + user.getEmail());
    }

    public void sendVerificationEmail(User user, String contextPath, String token) {
        String body = "Please go to the link to verify your account:\n"
                + contextPath + "/user/verifyEmail?id=" + user.getId() + "&token=" + token;
        SimpleMailMessage message =  createMessage("Account verification", body, user.getEmail());
        emailSender.send(message);
        logger.info("Verification token is sent to " + user.getEmail());
    }

}
