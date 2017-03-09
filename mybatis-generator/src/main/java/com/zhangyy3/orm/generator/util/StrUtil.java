package com.zhangyy3.orm.generator.util;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 17:46
 */
public class StrUtil {

    public static final String LB = "\r\n";

    public static boolean isNotBlank(String str) {
        return null != str && str.trim().length() > 0;
    }

    public static String getBlank(int i ) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < i ; j ++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
