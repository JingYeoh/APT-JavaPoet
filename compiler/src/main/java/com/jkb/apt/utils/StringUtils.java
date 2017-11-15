package com.jkb.apt.utils;

/**
 * 字符串工具类.
 *
 * @author JingYeoh.
 *         Date 17-11-15.
 *         Github https://github.com/justkiddingbaby
 *         Blog http://blog.justkiddingbaby.com
 */

public class StringUtils {
    /**
     * 是否有空值
     */
    public static boolean hasEmpty(CharSequence... value) {
        if (value == null || value.length == 0) return true;
        for (CharSequence item : value) {
            if (item == null || item.toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
