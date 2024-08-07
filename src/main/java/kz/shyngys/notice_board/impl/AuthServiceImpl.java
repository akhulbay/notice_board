package kz.shyngys.notice_board.impl;

import jakarta.servlet.http.HttpServletRequest;
import kz.shyngys.notice_board.dto.LoginRequest;
import kz.shyngys.notice_board.dto.read.AuthResponse;
import kz.shyngys.notice_board.dto.write.UserToCreateUpdateDto;
import kz.shyngys.notice_board.mapper.UserCreateUpdateMapper;
import kz.shyngys.notice_board.model.User;
import kz.shyngys.notice_board.service.AuthService;
import kz.shyngys.notice_board.service.InMemoryTokenBlackListService;
import kz.shyngys.notice_board.service.JwtService;
import kz.shyngys.notice_board.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static kz.shyngys.notice_board.util.validator.AuthValidator.validate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final InMemoryTokenBlackListService tokenBlackListService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(@NonNull LoginRequest request) {
        validate(request);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email, request.password
                )
        );

        User user = (User) userService.loadUserByUsername(request.email);

        String token = jwtService.generateToken(user);

        return AuthResponse.of(user.getId(), token);
    }

    @Override
    public AuthResponse register(@NonNull UserToCreateUpdateDto userToCreate) {
        if (userService.isExistsByEmail(userToCreate.email())) {
            throw new RuntimeException("User already exists with email: " + userToCreate.email());
        }

        Long id = userService.create(userToCreate);

        String token = jwtService.generateToken(UserCreateUpdateMapper.INSTANCE.toUser(userToCreate));

        return AuthResponse.of(id, token);
    }

    @Override
    public void logout(@NonNull HttpServletRequest request) {
        String token = jwtService.extractFromRequest(request);

        tokenBlackListService.addToBlacklist(token);
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.isAuthenticated() || (auth.getPrincipal() instanceof AnonymousAuthenticationToken)) {
            return Optional.empty();
        }

        Object principal = auth.getPrincipal();

        if (principal == null) {
            return Optional.empty();
        }

        if (principal instanceof UserDetails userDetails) {
            return Optional.of(userDetails.getUsername());
        } else {
            return Optional.of(principal.toString());
        }
    }

    @Override
    public Optional<Long> getCurrentUserId() {
        Optional<String> currentUserEmail = getCurrentUserEmail();

        if (currentUserEmail.isEmpty()) {
            return Optional.empty();
        }

        User user = (User) userService.loadUserByUsername(currentUserEmail.get());

        return Optional.of(user.getId());
    }

}
