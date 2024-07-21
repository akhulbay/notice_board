package kz.shyngys.notice_board.service;

public interface InMemoryTokenBlackListService {

    void addToBlacklist(String token);

    boolean isBlacklisted(String token);

}
