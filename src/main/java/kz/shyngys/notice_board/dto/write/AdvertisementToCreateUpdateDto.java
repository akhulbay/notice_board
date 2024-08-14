package kz.shyngys.notice_board.dto.write;

import java.time.LocalDateTime;

public record AdvertisementToCreateUpdateDto(
        String title,
        String description,
        Long minPrice,
        LocalDateTime createdAt) {

}
