package kz.shyngys.notice_board.util.validator;

import kz.shyngys.notice_board.dto.UserToCreateUpdateDto;
import lombok.experimental.UtilityClass;

import static kz.shyngys.notice_board.util.StrUtil.isNullOrEmpty;

@UtilityClass
public class UserValidator {

    public void validate(UserToCreateUpdateDto user) {
        if (isNullOrEmpty(user.email())) {
            throw new IllegalArgumentException("Email is required");
        }

        if (isNullOrEmpty(user.password())) {
            throw new IllegalArgumentException("Password is required");
        }
    }

}
