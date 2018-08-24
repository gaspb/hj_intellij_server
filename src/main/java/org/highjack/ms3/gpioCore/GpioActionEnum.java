package org.highjack.ms3.gpioCore;

import com.pi4j.io.gpio.PinMode;
import org.highjack.ms3.web.rest.vm.GpioPinOperationWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum GpioActionEnum {


    TOGGLE("toggle", new PinMode[]{PinMode.DIGITAL_OUTPUT}),
    SET_SHUTDOWN_OPT("setShutdownOptions", new PinMode[]{}),
    ADD_LISTENER("addListener", new PinMode[]{})

;

    private final String name;
    private final PinMode[] allowedModes;
    private static List<GpioPinOperationWrapper> runtimeAddedOperations = new ArrayList<>();

    GpioActionEnum(String name, PinMode[] allowedModes) {
        this.name = name;
        this.allowedModes = allowedModes;
    }


    public static void addRuntimeOperation(String methodName, PinMode[] allowedModes) {
        runtimeAddedOperations.add(new GpioPinOperationWrapper(methodName, allowedModes));
    }
    public static void removeRuntimeOperation(String methodName) {
        runtimeAddedOperations.removeIf(op->op.getOperationName().equals(methodName));
    }
    public static void clearRuntimeOperation() {
        runtimeAddedOperations.clear();
    }


    public static GpioPinOperationWrapper getRuntimeOperation(String methodName) {
        Optional<GpioPinOperationWrapper> opt = runtimeAddedOperations.stream().filter(wrap->wrap.getOperationName().equals(methodName)).findFirst();
        return opt.isPresent() ? opt.get() : null;
    }

    public GpioPinOperationWrapper toWrapper() {
        return new GpioPinOperationWrapper(this);
    }


    public static GpioPinOperationWrapper getAny(String methodName) {

if(runtimeAddedOperations!=null && !runtimeAddedOperations.isEmpty()){
        Optional<GpioPinOperationWrapper> opt = runtimeAddedOperations.stream().filter(wrap->wrap.getOperationName().equals(methodName)).findFirst();
         if(opt.isPresent()) {
             return opt.get();
         }

}
             try {
                 return GpioActionEnum.valueOf(methodName).toWrapper();
             } catch(IllegalArgumentException e) {
                return null;
             }

    }



    public String getName() {
        return name;
    }

    public PinMode[] getAllowedModes() {
        return allowedModes;
    }
}
