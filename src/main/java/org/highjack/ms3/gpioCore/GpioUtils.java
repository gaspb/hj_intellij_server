package org.highjack.ms3.gpioCore;

import com.pi4j.io.gpio.GpioPin;

public class GpioUtils {


    public static String pinToString(GpioPin pin) {
        return "Pin "+pin.getPin().getAddress()+" : Mode="+pin.getMode().getName()+", Direction="+pin.getMode().getDirection()+", Name="+pin.getName();
    }
}