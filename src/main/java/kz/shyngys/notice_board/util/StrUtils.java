package kz.shyngys.notice_board.util;

public final class StrUtils {

    private StrUtils() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullAndEmpty(String str) {
        return str != null && !str.isEmpty();
    }

}
