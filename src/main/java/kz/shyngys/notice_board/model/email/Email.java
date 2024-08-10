package kz.shyngys.notice_board.model.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Email {

    private String from;
    private String to;
    private String subject;
    private String body;

}
