package kz.shyngys.notice_board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BetConfig {

    @Value("${bet.expiration-minutes}")
    public int expirationMinutes;

}
