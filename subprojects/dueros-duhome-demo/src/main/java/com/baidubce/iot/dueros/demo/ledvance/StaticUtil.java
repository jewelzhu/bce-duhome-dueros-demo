package com.baidubce.iot.dueros.demo.ledvance;

public class StaticUtil {
    public static final int minTemperature = 2700;
    public static final int maxTemperature = 6500;
    public static final int temperatureChangeUnit = 1000;
    public static String getRealLedvancePuid(String zPuid) {
        return zPuid.substring(2);
    }
}
