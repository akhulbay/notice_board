package kz.shyngys.notice_board.service;

import kz.shyngys.notice_board.model.db.Bet;

public interface EmailService {

    void sendBetWonMessages(Bet bet);

    void sendBetOutbidMessage(Bet bet);

}
