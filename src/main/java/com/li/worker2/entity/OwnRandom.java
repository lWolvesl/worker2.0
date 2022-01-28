package com.li.worker2.entity;

import java.util.Random;

/**
 * @author li
 */
public class OwnRandom {
    static Random random = new Random();
    public static int getTen(){
        return random.nextInt(10);
    }
}
