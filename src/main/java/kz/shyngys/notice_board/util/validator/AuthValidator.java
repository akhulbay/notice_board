package kz.shyngys.notice_board.util.validator;

import kz.shyngys.notice_board.dto.LoginRequest;

import static kz.shyngys.notice_board.util.StrUtils.isNullOrEmpty;

public final class AuthValidator {

    private AuthValidator() {
    }

    public static void validate(LoginRequest request) {
        if (isNullOrEmpty(request.password)) {
            throw new IllegalArgumentException("F57paS3A0K :: password cannot be null");
        }

        if (isNullOrEmpty(request.email)) {
            throw new IllegalArgumentException("Kn02yhgPFW :: email cannot be null");
        }
    }

}
