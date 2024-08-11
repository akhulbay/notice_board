package kz.shyngys.notice_board.service;

public interface TokenBlackListService {

    void addToBlacklist(String token);

    boolean isBlacklisted(String token);

}
