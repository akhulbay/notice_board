package kz.shyngys.notice_board.impl.in_service;

import kz.shyngys.notice_board.model.email.Email;
import kz.shyngys.notice_board.service.in_service.EmailInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailInServiceFake implements EmailInService {

    @Override
    public void send(Email email) {
        log.info("FAKE :: sending email: {}", email);
    }

}
