package org.highjack.ms3.web.rest.vm;

import com.pi4j.io.gpio.PinMode;
import org.highjack.ms3.gpioCore.GpioActionEnum;

import java.util.Arrays;

public class GpioPinOperationWrapper {

    private boolean validEnum;
    private String operationName;
    private PinMode[] allowedModes;
    private GpioActionEnum wrappedEnum;



    public GpioPinOperationWrapper(String operationName, PinMode[] allowedModes) {
        this.validEnum = false;
        this.operationName = operationName;
        this.allowedModes = allowedModes;
    }

    public GpioPinOperationWrapper(GpioActionEnum wrappedEnum) {
        this.wrappedEnum = wrappedEnum;
        this.validEnum = true;
        this.operationName = wrappedEnum.getName();
        this.allowedModes = wrappedEnum.getAllowedModes();
    }


    public boolean isAllowed(PinMode mode) {
        return this.getAllowedModes().length==0 || Arrays.asList(this.getAllowedModes()).contains(mode);
    }



    public GpioActionEnum getWrappedEnum() {
        return wrappedEnum;
    }

    public void setWrappedEnum(GpioActionEnum wrappedEnum) {
        this.wrappedEnum = wrappedEnum;
    }
    public boolean isValidEnum() {
        return validEnum;
    }

    public void setValidEnum(boolean validEnum) {
        this.validEnum = validEnum;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public PinMode[] getAllowedModes() {
        return allowedModes;
    }

    public void setAllowedModes(PinMode[] allowedModes) {
        this.allowedModes = allowedModes;
    }
}
