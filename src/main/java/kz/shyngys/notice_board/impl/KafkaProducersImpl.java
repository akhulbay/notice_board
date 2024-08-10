package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.model.email.Email;
import kz.shyngys.notice_board.model.kafka.KafkaTopic;
import kz.shyngys.notice_board.service.KafkaProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducersImpl implements KafkaProducers {

    @Autowired
    private KafkaTemplate<String, Email> emailKafkaTemplate;

    @Override
    public void sendEmail(Email email) {
        emailKafkaTemplate.send(KafkaTopic.EMAIL.name(), email);
    }


}
