package com.baidubce.iot.duhome.demo.util;

import org.junit.Test;


import static com.baidubce.iot.duhome.demo.util.ColorHelper.HSBtoRGB;

public class ColorHelperTest {
    @Test
    public void testConvert() {
        String red = HSBtoRGB(4, 1, 1);
        System.out.println(red);

        String green = HSBtoRGB(120F, 1, 0.75);
        System.out.println(green);

        String yello = HSBtoRGB(60, 1, 1);
        System.out.println(yello);

        String blue = HSBtoRGB(231, 0.65, 0.71);
        System.out.println(blue);
    }
}
