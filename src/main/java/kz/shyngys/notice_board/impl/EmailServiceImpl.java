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

    public static class MessageContext {

        Advertisement advertisement;
        User user;
        String message;

        public static MessageContext of(@NonNull Advertisement advertisement, @NonNull User user, @NonNull String message) {
            MessageContext ctx = new MessageContext();

            ctx.advertisement = advertisement;
            ctx.user = user;
            ctx.message = message;

            return ctx;
        }

    }

    @Override
    public void sendBetWonMessages(@NonNull Bet bet) {
        Advertisement advertisement = advertisementRepository.findById(bet.getId())
                .orElseThrow(() -> new NoAdvertisementWithId(bet.getId()));

        User betWinner = userRepository.findById(bet.getUserId())
                .orElseThrow(() -> new NoUserWithId(bet.getUserId()));

        SEND_TO_WINNER:
        {
            MessageContext winnerMsg = MessageContext.of(advertisement, betWinner,
                    NotificationMessage.BET_WON.message);
            send(winnerMsg);
        }

        SEND_TO_OWNER:
        {
            MessageContext ownerMsg = MessageContext.of(advertisement, advertisement.getUser(),
                    NotificationMessage.AD_SOLD.message);
            send(ownerMsg);
        }
    }

    @Override
    public void sendBetOutbidMessage(@NonNull Bet bet) {
        Advertisement advertisement = advertisementRepository.findById(bet.getId())
                .orElseThrow(() -> new NoAdvertisementWithId(bet.getId()));

        User betLoser = userRepository.findById(bet.getUserId())
                .orElseThrow(() -> new NoUserWithId(bet.getUserId()));

        MessageContext message = MessageContext.of(advertisement, betLoser,
                NotificationMessage.BET_OUTBID.message);
        send(message);
    }

    public void send(MessageContext context) {
        Email emailToSend = Email.builder()
                .body(context.message)
                .to(context.user.getEmail())
                .subject(generateTitle(context.advertisement))
                .build();

        kafkaProducers.sendEmail(emailToSend);
    }

    private String generateTitle(Advertisement advertisement) {
        return String.format("Advertisement with title: %s and id: %s", advertisement.getTitle(), advertisement.getId());
    }

}
