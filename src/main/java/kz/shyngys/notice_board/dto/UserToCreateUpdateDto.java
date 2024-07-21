package kz.shyngys.notice_board.dto;

public record UserToCreateUpdateDto(
        String email,
        String password,
        String role) {
}
