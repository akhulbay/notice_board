package kz.shyngys.notice_board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class NoticeBoardApplication {

    public static void main(String[] args) {
        log.info("NoticeBoardApplication started");
        SpringApplication.run(NoticeBoardApplication.class, args);
    }

}
