package org.highjack.ms3.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.highjack.ms3.service.UseGpioService;
import org.highjack.ms3.service.TestService;
import org.highjack.ms3.web.rest.vm.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * GpioAccess controller
 */
@RestController
@RequestMapping("/api")
public class GpioAccessResource {

    private final Logger log = LoggerFactory.getLogger(GpioAccessResource.class);

    private final UseGpioService gpioService;
private final TestService testService;

    public GpioAccessResource(UseGpioService gpioService,TestService testService) {
        this.gpioService = gpioService;
this.testService = testService;
    }




    @PostMapping("/configure-gpio")
    public ResponseEntity<ResponseDTO> configureGpio(@RequestBody ConfigureGpioArgs args) {
        ResponseDTO response = this.gpioService.configurePinMode(args.gpioName, args.pinMode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/call-gpio-action")
    @Timed
    public ResponseEntity<ResponseDTO> callGpioAction(@RequestBody CallGpioActionArgs args) {
        ResponseDTO response = this.gpioService.useGpio(args.gpioName, args.actionName, args.args!=null ? args.args : new Object[]{});
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register-gpio")
    @Timed
    public ResponseEntity<ResponseDTO> registerGpio(@RequestBody RegisterGpioArgs args) {
        ResponseDTO response = this.gpioService.registerGpio(Integer.valueOf(args.gpioAddress), args.pinMode, args.name, args.args!=null ? args.args : new Object[]{});
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/stop-all-gpio")
    public  ResponseEntity<ResponseDTO> stopAllGpio() {
        ResponseDTO response = this.gpioService.stopAllGPIO();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
@GetMapping("/test-17")
    public  ResponseEntity<ResponseDTO> test() {
ResponseDTO response;
try {
        response = this.testService.run();
} catch (InterruptedException e) {
response =new ResponseDTO(ResponseDTO.FAILURE, "interruptedException");
}


   return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/unregister-all-gpio")
    public  ResponseEntity<ResponseDTO> unregisterAllGpio() {
        ResponseDTO response = this.gpioService.unregisterAllAndShutdown();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/get-active-gpios")
    public ResponseEntity<ResponseDTO> getActiveGpios() {
        ResponseDTO response = this.gpioService.getActiveGpios();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/get-gpio")
    @Timed
    public ResponseEntity<ResponseDTO> getGpio(@RequestBody GetGpioArgs args) {
        ResponseDTO response;
        if ( args.gpioAddress!=null) {
            response = this.gpioService.getActiveGpioStatus(Integer.valueOf(args.gpioAddress));
        } else if (args.gpioName!=null) {
            response = this.gpioService.getActiveGpioStatus(args.gpioName);
        }else {
            response = new ResponseDTO(ResponseDTO.FAILURE, "please provide the gpioAdress or gpioName");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //register runtime action

    @PostMapping("/register-rt-operation")
    @Timed
    public ResponseEntity<ResponseDTO> registerGpioRuntimeOperation(@RequestBody RegisterGpioRuntimeOperationArgs args) {

        ResponseDTO response = this.gpioService.registerGpioOperation(args.methodName, args.pinModes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


public static class RegisterGpioArgs { public String gpioAddress; public String pinMode; public String name; public Object[] args; public RegisterGpioArgs() { } public String getGpioAddress() { return gpioAddress; } public void setGpioAddress(String gpioAddress) { this.gpioAddress = gpioAddress; } public String getPinMode() { return pinMode; } public void setPinMode(String pinMode) { this.pinMode = pinMode; } public String getName() { return name; } public void setName(String name) { this.name = name; } public Object[] getArgs() { return args; } public void setArgs(Object[] args) { this.args = args; } } public static class CallGpioActionArgs { public String gpioName; public String actionName; public Object[] args; public CallGpioActionArgs() { } public String getGpioName() { return gpioName; } public void setGpioName(String gpioName) { this.gpioName = gpioName; } public String getActionName() { return actionName; } public void setActionName(String actionName) { this.actionName = actionName; } public Object[] getArgs() { return args; } public void setArgs(Object[] args) { this.args = args; } } public static class ConfigureGpioArgs { public String gpioName; public String pinMode; public ConfigureGpioArgs() { } public String getGpioName() { return gpioName; } public void setGpioName(String gpioName) { this.gpioName = gpioName; } public String getPinMode() { return pinMode; } public void setPinMode(String pinMode) { this.pinMode = pinMode; } } public static class GetGpioArgs { public String gpioName; public String gpioAddress; public GetGpioArgs() { } public String getGpioName() { return gpioName; } public void setGpioName(String gpioName) { this.gpioName = gpioName; } public String getGpioAddress() { return gpioAddress; } public void setGpioAddress(String gpioAddress) { this.gpioAddress = gpioAddress; } } public static class RegisterGpioRuntimeOperationArgs { public String methodName; public String[] pinModes; public RegisterGpioRuntimeOperationArgs() { } public String getMethodName() { return methodName; } public void setMethodName(String methodName) { this.methodName = methodName; } }
}
