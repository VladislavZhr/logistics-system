package com.logistics.notificationservice.testSendToEmial;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.logistics.notificationservice.application.port.output.MailSenderPort;
import com.logistics.notificationservice.application.service.SendNotificationService;
import com.logistics.notificationservice.domain.model.Message;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class MailIntegrationTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @Test
    void sendMail_shouldDeliverMessageToInbox() throws Exception {
        // ✅ 1. Налаштування JavaMailSender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025); // порт GreenMail SMTP
        mailSender.setProtocol("smtp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        // ✅ 2. Реалізація MailSenderPort (лямбда)
        MailSenderPort mailSenderPort = (to, subject, body) -> {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            mailSender.send(mail);
        };

        // ✅ 3. Ініціалізуємо сервіс з фейковим портом
        SendNotificationService service = new SendNotificationService(mailSenderPort);

        // ✅ 4. Емуляція повідомлення
        Message message = new Message(
                "test@localhost", // будь-який email працює
                "Password Reset",
                "Here is your reset token: abc123"
        );

        // ✅ 5. Виклик use-case'а
        service.sendMessageForgetPassword(message);

        // ✅ 6. Перевірка: лист дійсно надійшов
        MimeMessage[] received = greenMail.getReceivedMessages();
        assertEquals(1, received.length);
        assertEquals("Password Reset", received[0].getSubject());
        assertTrue(received[0].getContent().toString().contains("abc123"));
    }
}
