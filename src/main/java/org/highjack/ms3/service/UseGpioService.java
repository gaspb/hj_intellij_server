package org.highjack.ms3.service;

import com.pi4j.io.gpio.*;
import org.highjack.ms3.gpioCore.ExecuteGpioAction;
import org.highjack.ms3.gpioCore.GpioActionEnum;
import org.highjack.ms3.gpioCore.GpioUtils;
import org.highjack.ms3.web.rest.vm.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UseGpioService {

    private final Logger log = LoggerFactory.getLogger(UseGpioService.class);


	 // create gpio controller
        final static GpioController gpio = GpioFactory.getInstance();

    //full control test
	static private Set<GpioPin> activeGPIOList = new TreeSet<>((GpioPin o1, GpioPin o2)->o2.getPin().getAddress() - o1.getPin().getAddress());






    /**
     * Register any digital or analog GPIO to be controlled through this service
     * @param gpioAddress
     * @param pinMode possible values : O, I or any of PinMode's enum value
     * @param name
     * @return
     */
	public ResponseDTO registerGpio(Integer gpioAddress, String pinMode, String name, Object... args) {
        Pin pin = RaspiPin.getPinByAddress(gpioAddress);
        GpioPin gpioPin;
        List<String> supportedPinModeForDebug = new ArrayList<>();
        pin.getSupportedPinModes().forEach(mode->supportedPinModeForDebug.add(mode.getName()));


        boolean isDigital = pin.getSupportedPinModes().stream().filter(mode -> PinMode.allDigital().contains(mode)).count() > 0;
        boolean isAnalog = !isDigital && pin.getSupportedPinModes().stream().filter(mode -> PinMode.allAnalog().contains(mode)).count() > 0;
        boolean isInput = pin.getSupportedPinModes().stream().filter(mode -> PinMode.allInputs().contains(mode)).count() > 0;
        boolean isAnalogInput = isAnalog && isInput;


        gpioPin = isDigital ?
            args.length == 0 ?
                gpio.provisionDigitalMultipurposePin(RaspiPin.getPinByAddress(gpioAddress), name, PinMode.valueOf(pinMode))
                : gpio.provisionDigitalMultipurposePin(RaspiPin.getPinByAddress(gpioAddress), name, PinMode.valueOf(pinMode), PinPullResistance.valueOf((String) args[0]))
            : isAnalog ?
            isAnalogInput ?
                gpio.provisionAnalogInputPin(pin, name)
                : args.length == 0 ?
                gpio.provisionAnalogOutputPin(pin, name)
                : gpio.provisionAnalogOutputPin(pin, name, (Double) args[0])
            : null;

        if (gpioPin == null) {
            return new ResponseDTO(ResponseDTO.FAILURE, "Pin " + gpioAddress + " can't be used as a Digital Input pin. Please check the attached supported pin mode list", (String[]) supportedPinModeForDebug.toArray());

        }


        activeGPIOList.add(gpioPin);
        return new ResponseDTO(ResponseDTO.SUCCESS);
    }


    /**
     * Will call ExecuteGpioAction.execute on the given parameters
     * @param gpioName
     * @param actionName
     * @param args
     * @return
     */
    public ResponseDTO useGpio(String gpioName, String actionName, Object... args) {

	    Optional<GpioPin> opt = activeGPIOList.stream().filter(regPin->regPin.getName().equals(gpioName)).findFirst();

        if (!opt.isPresent()) {
            return new ResponseDTO(ResponseDTO.FAILURE, "failed to recover gpio pin with name:"+gpioName);
        }
        LoggerFactory.getLogger(UseGpioService.class).error("------   Using GPIO "+gpioName+" with action "+actionName);
        return ExecuteGpioAction.execute(opt.get(), actionName, args);
    }

    /**
     * Stop all GPIO processes
     * @return
     */
    public ResponseDTO stopAllGPIO() {
        gpio.shutdown();
        return new ResponseDTO(ResponseDTO.SUCCESS);
    }


    /**
     * Stop all GPIO processes
     * @return
     */
    public ResponseDTO unregisterAllAndShutdown() {
        gpio.unprovisionPin((GpioPin[])activeGPIOList.toArray());
        gpio.shutdown();
        return new ResponseDTO(ResponseDTO.SUCCESS);
    }

    public ResponseDTO configurePinMode(String gpioName, String mode) {
        GpioPin pin = recoverGpio(gpioName);
        if (pin==null) {
            return new ResponseDTO(ResponseDTO.FAILURE, "failed to recover gpio pin with name:"+gpioName);
        }
        //TODO only digital
        pin.setMode(PinMode.valueOf(mode));
        activeGPIOList.add(pin);
        return new ResponseDTO(ResponseDTO.SUCCESS);
    }

    private static GpioPin recoverGpio(String gpioName) {
        Optional<GpioPin> opt = activeGPIOList.stream().filter(regPin->regPin.getName().equals(gpioName)).findFirst();

        if (!opt.isPresent()) {
            return null;
        }
        return opt.get();
    }
    private static GpioPin recoverGpio(int gpioAddress) {
        Optional<GpioPin> opt = activeGPIOList.stream().filter(regPin->regPin.getPin().getAddress()==gpioAddress).findFirst();

        if (!opt.isPresent()) {
            return null;
        }
        return opt.get();
    }

    public ResponseDTO getActiveGpios() {
        return new ResponseDTO(ResponseDTO.SUCCESS, "", activeGPIOList.stream().map(GpioUtils::pinToString).toArray());
    }

    public ResponseDTO getActiveGpioStatus(String gpioName) {
        GpioPin pin = recoverGpio(gpioName);
        if (pin==null) {
            return new ResponseDTO(ResponseDTO.FAILURE, "This pin is not registered yet.");
        }
        String result = GpioUtils.pinToString(pin);
        return new ResponseDTO(ResponseDTO.SUCCESS, result);


    }
    public ResponseDTO getActiveGpioStatus(int gpioAddress) {
        GpioPin pin = recoverGpio(gpioAddress);
        if (pin==null) {
            return new ResponseDTO(ResponseDTO.FAILURE, "This pin is not registered yet.");
        }
        String result = "Pin "+pin.getPin().getAddress()+" : Mode="+pin.getMode().getName()+", Direction="+pin.getMode().getDirection()+", Name="+pin.getName();
        return new ResponseDTO(ResponseDTO.SUCCESS, result);


    }

    public ResponseDTO registerGpioOperation(String gpioName, String... pinModes) {

        PinMode[] modes = pinModes.length>0 ? (PinMode[])Arrays.stream(pinModes).map(PinMode::valueOf).toArray() : new PinMode[]{};

        GpioActionEnum.addRuntimeOperation(gpioName, modes);
        return new ResponseDTO(ResponseDTO.SUCCESS, "Operation added successfully");


    }

}
