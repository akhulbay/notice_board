package kz.shyngys.notice_board.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StrUtil {

    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean isNotNullAndEmpty(String str) {
        return str != null && !str.isEmpty();
    }

}
