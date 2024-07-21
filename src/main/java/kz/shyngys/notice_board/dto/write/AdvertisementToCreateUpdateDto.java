package kz.shyngys.notice_board.dto.write;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record AdvertisementToCreateUpdateDto(
        String title,
        String description,
        Long minPrice,
        LocalDateTime createdAt,
        List<MultipartFile> images) {

}
