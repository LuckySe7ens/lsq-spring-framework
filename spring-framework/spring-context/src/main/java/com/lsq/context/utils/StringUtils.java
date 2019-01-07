package com.lsq.context.utils;

/**
 * Created by Administrator on 2019/1/7.
 */
public class StringUtils {
    public static String firstlower(String string) {
        char[] chars = string.toCharArray();
        chars[0] = (char) (chars[0] + 32);
        return new String(chars);
    }
}
