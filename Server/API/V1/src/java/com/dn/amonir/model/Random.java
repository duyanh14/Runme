/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.model;

/**
 *
 * @author Duy
 */
public class Random {

    public static String String(int length) {
        return new RandomString(length).nextString();
    }

    public static float Float(float min, float max) {
         java.util.Random rand = new  java.util.Random();
        float result = rand.nextFloat() * (max - min) + min;
        return result;
    }

    public static int Int(int min, int max) {
        java.util.Random rand = new java.util.Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    static String Int(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new java.util.Random().nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1) + (int) Math.pow(10, charLength - 1));
    }

    void nextBytes(byte[] macAddr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
