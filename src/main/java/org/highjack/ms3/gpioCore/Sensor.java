package org.highjack.ms3.gpioCore;


import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.event.PinEvent;
public abstract class Sensor {
    private String sensorName;
    private Pin pin;
    private PinDirection direction;
    private PinMode pinMode;
    private PinEvent trigger;
    private Object value;

    public Sensor(String sensorName, Pin pin, PinDirection direction, PinMode pinMode, PinEvent trigger, Object value) {
        this.sensorName = sensorName;
        this.pin = pin;
        this.direction = direction;
        this.pinMode = pinMode;
        this.trigger = trigger;
        this.value = value;
    }

    /*
     * TODO add Listeners etc here or in Input Output implementations
     *
     */




    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }

    public PinDirection getDirection() {
        return direction;
    }

    public void setDirection(PinDirection direction) {
        this.direction = direction;
    }

    public PinMode getPinMode() {
        return pinMode;
    }

    public void setPinMode(PinMode pinMode) {
        this.pinMode = pinMode;
    }

    public PinEvent getTrigger() {
        return trigger;
    }

    public void setTrigger(PinEvent trigger) {
        this.trigger = trigger;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

/**

 String sensorName;
 Pin number: GPIO 22 (specified through pinGPIO)
 PinDirection: input only
 Mode: pull-up
 Trigger: rising-edge
 Initial value: false

 **/
