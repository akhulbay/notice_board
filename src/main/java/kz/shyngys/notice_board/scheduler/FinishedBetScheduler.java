package kz.shyngys.notice_board.scheduler;

import kz.shyngys.notice_board.model.db.Bet;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.BetRepository;
import kz.shyngys.notice_board.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinishedBetScheduler {

    private final EmailService emailService;
    private final BetRepository betRepository;
    private final AdvertisementRepository advertisementRepository;

    @Scheduled(fixedDelay = 5_000)
    @Transactional
    public void sendGreetingsToWinner() {
        while (true) {
            Optional<Bet> optionalBet = betRepository.findFirstFinishedBet(LocalDateTime.now());

            if (optionalBet.isEmpty()) {
                break;
            }

            Bet bet = optionalBet.get();
            String lockId = UUID.randomUUID().toString();

            betRepository.lockFinishedBet(bet.getId(), lockId);
            String resultingLockId = betRepository.findLockIdById(bet.getId());

            if (!lockId.equals(resultingLockId)) {
                break;
            }

            emailService.sendBetWonMessages(bet);

            removeBetAndAdvertisement(bet);
        }
    }

    private void removeBetAndAdvertisement(Bet bet) {
        betRepository.deleteById(bet.getId());
        advertisementRepository.markRemoved(bet.getId());
    }


}
