package kz.shyngys.notice_board.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.shyngys.notice_board.dto.AuthResponse;
import kz.shyngys.notice_board.dto.LoginRequest;
import kz.shyngys.notice_board.dto.UserToCreateUpdateDto;
import kz.shyngys.notice_board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserToCreateUpdateDto userToCreate) {
        return authService.register(userToCreate);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request) {
        authService.logout(request);
    }

}
