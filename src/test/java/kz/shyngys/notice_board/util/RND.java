package kz.shyngys.notice_board.util;

import java.util.Random;

public final class RND {

    private static final Random RANDOM = new Random();

    private RND() {
    }

    public static long randomLong() {
        long value = RANDOM.nextLong();
        if (value < 0) {
            value = -value;
        }
        return value;
    }

}
