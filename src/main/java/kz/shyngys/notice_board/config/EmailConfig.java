package kz.shyngys.notice_board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

    @Value("${email.useFake}")
    public boolean useFake;

    @Value("${email.host}")
    public String host;

    @Value("${email.port}")
    public int port;

    @Value("${email.username}")
    public String username;

    @Value("${email.password}")
    public String password;

}
