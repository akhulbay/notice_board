package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.config.BetConfig;
import kz.shyngys.notice_board.dto.BetRequest;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.model.db.Advertisement;
import kz.shyngys.notice_board.model.db.Bet;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.BetRepository;
import kz.shyngys.notice_board.service.AuthService;
import kz.shyngys.notice_board.service.EmailService;
import kz.shyngys.notice_board.service.NoticeBoardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final AuthService authService;
    private final AdvertisementRepository advertisementRepository;
    private final BetRepository betRepository;
    private final EmailService emailService;
    private final BetConfig betConfig;

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

        // here we lock this bet by id if it exists
        Optional<Bet> optionalBet = betRepository.findBetById(request.advertisementId);

        LocalDateTime now = LocalDateTime.now();

        if (!isValid(optionalBet, request.amount, now)) {
            throw new RuntimeException("Cannot make bet, refresh and try again");
        }

        if (optionalBet.isPresent() && !Objects.equals(optionalBet.get().getUserId(), userId)) {
            notifyOutbid(optionalBet.get());
        }

        betRepository.save(
                Bet.builder()
                        .id(request.advertisementId)
                        .amount(request.amount)
                        .expiresAt(now.plusMinutes(betConfig.expirationMinutes))
                        .userId(userId)
                        .build()
        );
    }

    /**
     * Validates that the given parameters are correct, allowing a bet to be placed on the advertisement (ad).
     * <p>
     * The validation process involves:
     * <ol>
     *   <li>If there is no existing bet for the given ad, it is permissible to place the first bet.</li>
     *   <li>The bet must not be expired, and the provided amount of money must exceed the current highest amount.</li>
     * </ol>
     */
    private boolean isValid(Optional<Bet> optionalBet, Long amount, LocalDateTime now) {
        if (optionalBet.isEmpty()) {
            return true;
        }

        Bet bet = optionalBet.get();

        return bet.getExpiresAt().isAfter(now) && amount > bet.getAmount();
    }

    private void notifyOutbid(Bet bet) {
        emailService.sendBetOutbidMessage(bet);
    }

}
