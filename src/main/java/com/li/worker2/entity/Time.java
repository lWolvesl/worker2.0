package com.li.worker2.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author li
 */
public class Time {
    public static int getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("HH");
        return Integer.parseInt(sdf.format(new Date()));
    }

    public static String getTimes() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss.ms");
        return sdf.format(new Date());
    }

    public static int getSecond(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("mm");
        return Integer.parseInt(sdf.format(new Date()));
    }

    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
