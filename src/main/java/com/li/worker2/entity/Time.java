package com.li.worker2.entity;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
