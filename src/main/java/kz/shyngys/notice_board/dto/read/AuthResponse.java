package kz.shyngys.notice_board.dto.read;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {

    public Long id;
    public String token;

    public static AuthResponse of(Long id, String token) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.id = id;
        authResponse.token = token;
        return authResponse;
    }

}
