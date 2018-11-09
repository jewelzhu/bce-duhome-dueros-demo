package com.baidubce.iot.duhome.demo.util;

import java.awt.*;

public class ColorHelper {
    public static String HSBtoRGB(double hue, double saturation, double brightness) {
        int intRgb =
                java.awt.Color.HSBtoRGB((float)hue/360F, (float)saturation, (float)brightness);
        return String.format("#%06X", (0xFFFFFF & intRgb));
    }
}
