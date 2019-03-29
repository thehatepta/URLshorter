package com.urlShorter.services;

public class RandomCharCreators {

    public static String getRandomChars() {
        String randomStr = "";
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 5; i++)
            randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
        return randomStr;
    }

}
