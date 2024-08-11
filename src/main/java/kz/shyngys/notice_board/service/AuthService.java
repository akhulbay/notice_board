package kz.shyngys.notice_board.service;

import jakarta.servlet.http.HttpServletRequest;
import kz.shyngys.notice_board.dto.LoginRequest;
import kz.shyngys.notice_board.dto.read.AuthResponse;

import java.util.Optional;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(LoginRequest request);

    void logout(HttpServletRequest request);

    Optional<String> getCurrentUserEmail();

    Optional<Long> getCurrentUserId();

}
