package com.sweet_companion_bot.service;

import java.util.Random;

public class Util {

    private static Random random = new Random();

    public static int getRandomInt(int min, int max) {
        int n = random.nextInt((max+1) - min) + min;
        return n;
    }
}
