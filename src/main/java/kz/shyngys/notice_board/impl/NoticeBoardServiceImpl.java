package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.dto.BetRequest;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.model.db.Advertisement;
import kz.shyngys.notice_board.model.db.Bet;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.BetRepository;
import kz.shyngys.notice_board.service.AuthService;
import kz.shyngys.notice_board.service.NoticeBoardService;
import kz.shyngys.notice_board.util.BetUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final AuthService authService;
    private final AdvertisementRepository advertisementRepository;
    private final BetRepository betRepository;

    @Transactional
    @Override
    public void makeBet(@NonNull BetRequest request) {
        Long userId = authService.getCurrentUserId()
                .orElseThrow(() -> new RuntimeException("User not logged in"));

        Advertisement advertisement = advertisementRepository.findById(request.getAdvertisementId())
                .orElseThrow(() -> new NoAdvertisementWithId(request.getAdvertisementId()));

        if (request.amount < advertisement.getMinPrice()) {
            throw new RuntimeException("Not enough price for this advertisement");
        }

        Optional<Bet> optionalBet = betRepository.findBetById(request.advertisementId);

        LocalDateTime now = LocalDateTime.now();

        if (!isValid(optionalBet, request.amount, now)) {
            throw new RuntimeException("Cannot make bet, refresh and try again");
        }

        betRepository.save(
                Bet.builder()
                        .id(request.advertisementId)
                        .amount(request.amount)
                        .expiresAt(now.plusMinutes(BetUtil.BET_EXPIRATION_MINUTES))
                        .userId(userId)
                        .build()
        );
    }

    private boolean isValid(Optional<Bet> optionalBet, Long amount, LocalDateTime now) {
        if (optionalBet.isEmpty()) {
            return true;
        }

        Bet bet = optionalBet.get();

        return bet.getExpiresAt().isAfter(now) && amount > bet.getAmount();
    }

}
