package kz.shyngys.notice_board.exception;

public class NoImageWithName extends RuntimeException {

    public NoImageWithName(String name) {
        super("No image with name " + name);
    }

}
