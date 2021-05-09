package org.garcia.layerBusiness.util;

public class SecondsToTime {

    public static String convertSecToTime(int duration) {
        int seconds = duration;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
