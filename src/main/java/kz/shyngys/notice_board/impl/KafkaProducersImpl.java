package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.model.email.Email;
import kz.shyngys.notice_board.model.kafka.KafkaTopic;
import kz.shyngys.notice_board.service.KafkaProducers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducersImpl implements KafkaProducers {

    @Autowired
    @Qualifier("emailKafkaTemplate")
    private KafkaTemplate<String, Email> kafkaTemplate;

    @Override
    public void sendEmail(Email email) {
        kafkaTemplate.send(KafkaTopic.EMAIL.name(), email);
    }


}
