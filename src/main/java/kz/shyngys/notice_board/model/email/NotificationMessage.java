package kz.shyngys.notice_board.model.email;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationMessage {

    BET_WON("Congratulations! You have been won!"),
    BET_OUTBID("Unfortunately, you have been outbid! You can try again.");

    public final String message;

}
