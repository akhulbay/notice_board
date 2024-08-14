package kz.shyngys.notice_board.util.validator;

import kz.shyngys.notice_board.dto.write.UserToCreateUpdateDto;

import static kz.shyngys.notice_board.util.StrUtils.isNullOrEmpty;

public final class UserValidator {

    private UserValidator() {
    }

    public static void validate(UserToCreateUpdateDto user) {
        if (isNullOrEmpty(user.email())) {
            throw new IllegalArgumentException("Email is required");
        }

        if (isNullOrEmpty(user.password())) {
            throw new IllegalArgumentException("Password is required");
        }
    }

}
