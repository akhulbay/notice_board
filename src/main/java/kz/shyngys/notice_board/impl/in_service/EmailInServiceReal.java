package kz.shyngys.notice_board.impl.in_service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import kz.shyngys.notice_board.config.EmailConfig;
import kz.shyngys.notice_board.model.email.Email;
import kz.shyngys.notice_board.service.in_service.EmailInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class EmailInServiceReal implements EmailInService {

    private final EmailConfig config;

    @Override
    public void send(Email email) {
        log.info("REAL :: started sending email");

        JavaMailSenderImpl sender = createJavaMailSender();

        MimeMessage mimeMessage = convert(sender, email);

        try {
            sender.send(mimeMessage);
        } catch (MailException e) {
            log.error("Error while sending email: {}", e.getMessage());
        }

        log.info("REAL :: finished sending email");
    }

    private MimeMessage convert(JavaMailSenderImpl sender, Email email) {
        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setSubject(email.getSubject());

            if (email.getFrom() == null) {
                helper.setFrom(config.username);
            } else {
                helper.setFrom(email.getFrom());
            }

            helper.setText(email.getBody());
            helper.setTo(new InternetAddress(email.getTo()));

            log.info("Sending email to {}, with subject {}", email.getTo(), email.getSubject());

            return message;
        } catch (MessagingException e) {
            log.error("Error while creating mime message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private JavaMailSenderImpl createJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(config.host);
        sender.setPort(config.port);
        sender.setPassword(config.password);
        sender.setUsername(config.username);

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        sender.setJavaMailProperties(properties);

        return sender;
    }

}
