package kz.shyngys.notice_board.service;

import kz.shyngys.notice_board.model.db.Bet;

public interface EmailService {

    void sendBetWonMessage(Bet bet);

    void sendBetOutbidMessage(Bet bet);

}
