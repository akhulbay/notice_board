package kz.shyngys.notice_board.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.shyngys.notice_board.dto.AuthResponse;
import kz.shyngys.notice_board.dto.LoginRequest;
import kz.shyngys.notice_board.dto.UserToCreateUpdateDto;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(UserToCreateUpdateDto userToCreate);

    void logout(HttpServletRequest request);

}
