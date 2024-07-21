package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.service.InMemoryTokenBlackListService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InMemoryTokenBlackListServiceImpl implements InMemoryTokenBlackListService {

    private final Set<String> blackList = new HashSet<>();

    @Override
    public void addToBlacklist(String token) {
        blackList.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blackList.contains(token);
    }

}
