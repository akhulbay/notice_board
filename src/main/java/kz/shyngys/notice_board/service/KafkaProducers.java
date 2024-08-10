package kz.shyngys.notice_board.service;

import kz.shyngys.notice_board.model.email.Email;

public interface KafkaProducers {

    void sendEmail(Email email);

}
