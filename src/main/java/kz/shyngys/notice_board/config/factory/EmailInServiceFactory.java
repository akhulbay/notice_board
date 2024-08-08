package kz.shyngys.notice_board.config.factory;

import kz.shyngys.notice_board.config.EmailConfig;
import kz.shyngys.notice_board.impl.in_service.EmailInServiceFake;
import kz.shyngys.notice_board.impl.in_service.EmailInServiceReal;
import kz.shyngys.notice_board.service.in_service.EmailInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailInServiceFactory {

    @Autowired
    private EmailConfig emailConfig;

    @Value("${emailUseFake}")
    private boolean useFake;

    @Bean
    public EmailInService emailInService() {
        if (useFake) {
            return new EmailInServiceFake();
        }

        return new EmailInServiceReal(emailConfig);
    }

}
