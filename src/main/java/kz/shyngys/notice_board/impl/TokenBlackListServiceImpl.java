package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.model.db.BlackListedToken;
import kz.shyngys.notice_board.repository.TokenBlackListRepository;
import kz.shyngys.notice_board.service.TokenBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlackListServiceImpl implements TokenBlackListService {

    private final TokenBlackListRepository tokenBlackListRepository;

    @Override
    public void addToBlacklist(String token) {
        tokenBlackListRepository.save(
                BlackListedToken.builder()
                        .token(token)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public boolean isBlacklisted(String token) {
        return tokenBlackListRepository.existsByToken(token);
    }

}
