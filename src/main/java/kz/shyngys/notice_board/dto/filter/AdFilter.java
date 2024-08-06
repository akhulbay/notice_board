package kz.shyngys.notice_board.dto.filter;

import java.time.LocalDateTime;

public record AdFilter(
        String title,
        String description,
        Long priceFrom,
        Long priceTo,
        LocalDateTime createdAtFrom,
        LocalDateTime createdAtTo) {

}
