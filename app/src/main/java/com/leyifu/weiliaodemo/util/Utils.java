package com.leyifu.weiliaodemo.util;

/**
 * Created by hahaha on 2017/11/6 0006.
 */

public class Utils {

    public static String formatterTime(long time) {

        long second = time / 1000;
        long minute = second / 60;
        long hour = minute / 60;

        second = second % 60;
        minute = minute % 60;

        return (hour < 10 ? "0" + hour : hour)
                + ":" + (minute < 10 ? "0" + minute : minute)
                + ":" + (second < 10 ? "0" + second : second);
    }
}
