package kz.shyngys.notice_board.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import kz.shyngys.notice_board.dto.read.AuthResponse;
import kz.shyngys.notice_board.dto.LoginRequest;
import kz.shyngys.notice_board.dto.write.UserToCreateUpdateDto;
import kz.shyngys.notice_board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Authenticate user and generate an access token",
            description = "This endpoint allows a user to log in by providing valid credentials. " +
                          "Upon successful authentication, an access token is generated, which can be used " +
                          "for accessing protected resources."
    )
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(
            summary = "Create a new user account and generate access token",
            description = "This endpoint is used to register a new user. The request should contain " +
                          "all the necessary details for creating an account. Upon successful registration, " +
                          "an access token is generated for the newly created account."
    )
    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserToCreateUpdateDto userToCreate) {
        return authService.register(userToCreate);
    }

    @Operation(
            summary = "Log out the current user",
            description = "This endpoint logs out the currently authenticated user. The session will be invalidated, " +
                          "and the access token will be revoked, rendering it unusable for further requests."
    )
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request) {
        authService.logout(request);
    }

}
