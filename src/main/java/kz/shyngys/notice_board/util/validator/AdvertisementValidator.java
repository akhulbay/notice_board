package kz.shyngys.notice_board.util.validator;

import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import lombok.experimental.UtilityClass;

import static kz.shyngys.notice_board.util.StrUtil.isNullOrEmpty;

@UtilityClass
public class AdvertisementValidator {

    public void validate(AdvertisementToCreateUpdateDto dto) {
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
