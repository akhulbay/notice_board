package kz.shyngys.notice_board.exception;

public class NoAdvertisementWithId extends RuntimeException {

    public NoAdvertisementWithId(Long id) {
        super("Advertisement with id " + id + " not found");
    }

}
