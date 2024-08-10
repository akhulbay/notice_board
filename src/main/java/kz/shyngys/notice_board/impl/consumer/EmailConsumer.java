package kz.shyngys.notice_board.impl.consumer;

import kz.shyngys.notice_board.model.email.Email;
import kz.shyngys.notice_board.service.in_service.EmailInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailInService emailInService;

    @KafkaListener(
            topics = "EMAIL",
            groupId = "EMAIL_GROUP",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void sendEmail(Email email) {
        try {
            emailInService.send(email);
        } catch (Exception e) {
            log.error("Error while sending message {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
