package com.crypto.service;

import com.crypto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Resource
    private Environment environment;

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
}
