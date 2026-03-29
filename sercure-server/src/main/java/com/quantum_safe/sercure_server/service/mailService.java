package com.quantum_safe.sercure_server.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class mailService
{
    private final JavaMailSender mailSender;

    public mailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSecurityAlert(String toEmail, Long messageId) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("⚠ Security Alert - Message Re-read Attempt");
        message.setText(
                "A second read attempt was detected for message ID: "
                        + messageId +
                        "\nIf this was not you, please secure your account."
        );

        mailSender.send(message);
    }

}
