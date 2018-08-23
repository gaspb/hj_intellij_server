package org.highjack.ms3.gpioCore;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;
import org.highjack.ms3.web.rest.vm.GpioPinOperationWrapper;
import org.highjack.ms3.web.rest.vm.ResponseDTO;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class ExecuteGpioAction {


    private static final Logger log = LoggerFactory.getLogger(ExecuteGpioAction.class);


    public static ResponseDTO execute(GpioPin pin, String actionName, Object... args){
        GpioPinOperationWrapper actionEnum = GpioActionEnum.getAny(actionName);
        if (actionEnum==null) {
            return new ResponseDTO(ResponseDTO.FAILURE, "Action "+actionName+" is not defined in enum GpioActionEnum");
        }

        //check privileges
        if(!actionEnum.isAllowed(pin.getMode())) {
            return new ResponseDTO(ResponseDTO.FAILURE, "Action "+actionName+" cannot be invoked on a pin with mode "+pin.getMode());
        }


        if (actionEnum.isValidEnum()) {

           return executeEnum(pin, actionEnum, args);
        } else {

            return  executeRuntimeOperation(pin, actionName, args);
        }


    }


    private static ResponseDTO executeEnum(GpioPin pin, GpioPinOperationWrapper actionEnum, Object... args) {

log.info("executing enum "+actionEnum.getWrappedEnum().name());

switch(actionEnum.getWrappedEnum()) {
            case TOGGLE:
                log.info("TOGGLING IO PIN ");
                ((GpioPinDigitalOutput) pin).toggle();
                break;
            case ADD_LISTENER:
                boolean isAnalog = PinMode.allAnalog().stream().anyMatch(mode->mode.equals(pin.getMode()));
                pin.addListener(isAnalog ? new GpioPinListenerAnalog() {
                    @Override
                    public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
                        // display pin state on console
                        System.out.println(" --> GPIO PIN VALUE CHANGE: " + event.getPin() + " = " + event.getValue());
                    }
                } : new GpioPinListenerDigital() {
                    @Override
                    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                        // display pin state on console
                        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                    }
                } );
                break;
            case SET_SHUTDOWN_OPT:
                if (args.length>0) {
                    boolean setTo = (boolean) args[0];
                    if (args.length==2) {
                        try {
                            pin.setShutdownOptions(setTo, PinState.valueOf((String)args[1]));
                        } catch(IllegalArgumentException e) {
                            return new ResponseDTO(ResponseDTO.FAILURE, "Second argument must be a PinState : LOW or HIGH");

                        }
                    } else {
                        pin.setShutdownOptions(setTo);
                    }
                }
                break;
default:
log.info("executing enum "+actionEnum.getWrappedEnum().name()+" completed");

        }

        return new ResponseDTO(ResponseDTO.SUCCESS);
    }




    private static ResponseDTO executeRuntimeOperation(GpioPin pin, String actionName, Object... args) {

        boolean isAnalog = PinMode.allAnalog().stream().anyMatch(mode->mode.equals(pin.getMode()));
        boolean isInput = pin.getMode().getDirection().equals(PinDirection.IN);
        Class beanClass = isAnalog ?
            isInput ? GpioPinAnalogInput.class : GpioPinAnalogOutput.class
            : isInput ? GpioPinDigitalInput.class : GpioPinDigitalOutput.class;
        String message = "";
        try {
            //get method
            Method method = BeanUtils.findMethodWithMinimalParameters(beanClass, actionName);
            //get return type
            Class returnType = method.getReturnType();
            //call valueOf is arg is enum
            if (args!=null && args.length>1) {
                Class[] types = method.getParameterTypes();
                for (int x=0; x<args.length;x++) {
                    if(types.length>x) {
                        if (types[x].isEnum() && args[x] instanceof String) {
                            args[x] = Enum.valueOf(types[x], (String) args[x]);
                        } else {
                            try {
                                args[x] = types[x].cast(args[x]);
                            } catch(ClassCastException e) {
                                return new ResponseDTO(ResponseDTO.FAILURE, "Argument "+args[x]+"is not castable to class "+types[x].getName());
                            }
                        }
                    } else {
                        //provided too much arguments
                        message = "Provided too much arguments for the function. Expected:"+types.length+", Recieved : "+args.length;
                    }
                }
                if (types.length>args.length) {
                    Object[] nullArgs = new Object[types.length-args.length];
                    for (int x=0; x<types.length-args.length;x++) {
                        nullArgs[x] = null;
                    }
                    //concat
                    args = Stream.of(args, nullArgs).flatMap(Stream::of).toArray(String[]::new);
                }
            }
            Object response = method.invoke(beanClass.cast(pin), args);
            return new ResponseDTO(ResponseDTO.SUCCESS, message, returnType.cast(response));
        } catch(Exception e) {
            return new ResponseDTO(ResponseDTO.FAILURE, "Failed to invoke method "+actionName+" on beanClass "+beanClass);
        }

    }




}
