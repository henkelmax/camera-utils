package de.maxhenkel.camerautils;

public class Utils {

    public static double round(double value, int digits) {
        return Math.round(value * Math.pow(10D, digits)) / Math.pow(10D, digits);
    }

}
