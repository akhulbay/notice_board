package kz.shyngys.notice_board.exception;

public class NoImageWithId extends RuntimeException {

    public NoImageWithId(Long id) {
        super("No image with id " + id);
    }

}
