package kz.shyngys.notice_board.dto.read;

import java.time.LocalDateTime;
import java.util.List;

public record AdvertisementToReadDto(
        Long id,
        String title,
        String description,
        Long minPrice,
        LocalDateTime createdAt,
        String status,
        List<byte[]> images) {

}
