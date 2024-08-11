package kz.shyngys.notice_board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class NoticeBoardApplication {

    public static void main(String[] args) {
        log.info("NoticeBoardApplication started");
        SpringApplication.run(NoticeBoardApplication.class, args);
    }

}
