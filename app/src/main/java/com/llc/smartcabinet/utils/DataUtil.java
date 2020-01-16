package com.llc.smartcabinet.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author newler
 * @what
 * @date 2020/1/12
 */
public class DataUtil {
    public static String convert2String(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
