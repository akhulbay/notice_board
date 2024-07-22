package kz.shyngys.notice_board.util;

public final class StrUtil {

    private StrUtil() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullAndEmpty(String str) {
        return str != null && !str.isEmpty();
    }

}
