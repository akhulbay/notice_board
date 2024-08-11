package kz.shyngys.notice_board.model.email;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String from;
    private String to;
    private String subject;
    private String body;

}
