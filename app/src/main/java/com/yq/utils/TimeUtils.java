package com.yq.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gbh on 16/12/3.
 */

public class TimeUtils {

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentTimeyyyMMdd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }


    public static String getCurrentyyyMMdd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
    public static String getCurrentTimeyyyMM() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return simpleDateFormat.format(new Date());
    }


    public static String getCurrentTimeRq() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        return simpleDateFormat.format(new Date());
    }
}
