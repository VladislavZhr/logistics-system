package com.logistics.notificationservice.infrastructure.adapter.out.email;

import com.logistics.notificationservice.application.port.output.MailSenderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderAdapter implements MailSenderPort {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }
}
