package kz.shyngys.notice_board.dto.write;

public record UserToCreateUpdateDto(
        String email,
        String password,
        String role) {
}
