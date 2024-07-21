package kz.shyngys.notice_board.exception;

public class NoUserWithId extends RuntimeException {

    public NoUserWithId(Long id) {
        super("User with id " + id + " not found");
    }

}
