package kz.shyngys.notice_board.util.validator;

import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;

import static kz.shyngys.notice_board.util.StrUtils.isNullOrEmpty;

public final class AdvertisementValidator {

    private AdvertisementValidator() {
    }

    public static void validate(AdvertisementToCreateUpdateDto dto) {
        if (isNullOrEmpty(dto.title())) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        if (isNullOrEmpty(dto.description())) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        if (dto.minPrice() == null) {
            throw new IllegalArgumentException("Minimum price cannot be null");
        }
    }

}
