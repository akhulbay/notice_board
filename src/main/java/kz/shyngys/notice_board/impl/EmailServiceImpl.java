package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.exception.NoUserWithId;
import kz.shyngys.notice_board.model.db.Advertisement;
import kz.shyngys.notice_board.model.db.Bet;
import kz.shyngys.notice_board.model.db.User;
import kz.shyngys.notice_board.model.email.Email;
import kz.shyngys.notice_board.model.email.NotificationMessage;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.UserRepository;
import kz.shyngys.notice_board.service.EmailService;
import kz.shyngys.notice_board.service.KafkaProducers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final KafkaProducers kafkaProducers;


    @Override
    public void sendBetWonMessage(@NonNull Bet bet) {
        send0(bet, NotificationMessage.BET_WON);
    }

    @Override
    public void sendBetOutbidMessage(@NonNull Bet bet) {
        send0(bet, NotificationMessage.BET_OUTBID);
    }

    public void send0(Bet bet, NotificationMessage message) {
        User user = userRepository.findById(bet.getUserId())
                .orElseThrow(() -> new NoUserWithId(bet.getUserId()));

        Advertisement advertisement = advertisementRepository.findById(bet.getId())
                .orElseThrow(() -> new NoAdvertisementWithId(bet.getUserId()));

        Email emailToSend = Email.builder()
                .body(message.message)
                .to(user.getEmail())
                .subject(generateTitle(advertisement))
                .build();

        kafkaProducers.sendEmail(emailToSend);
    }

    private String generateTitle(Advertisement advertisement) {
        return String.format("Advertisement with title: %s and id: %s", advertisement.getTitle(), advertisement.getId());
    }

}
