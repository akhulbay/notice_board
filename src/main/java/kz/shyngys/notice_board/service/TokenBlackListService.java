package kz.shyngys.notice_board.service;

// todo shyngys replace with better solution, so api have to be STATELESS, can extract to Postgres table
public interface TokenBlackListService {

    void addToBlacklist(String token);

    boolean isBlacklisted(String token);

}
