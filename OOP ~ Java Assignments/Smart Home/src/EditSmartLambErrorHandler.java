import java.io.IOException;
import java.util.EventListener;

/**
 * This class provides exception handling for EditSmartLamp class.
 */
public class EditSmartLambErrorHandler extends EditSmartDeviceErrorHandler {

    /**
     * This method handles a command related to properties of a smart lamp while adding.
     * If a device with the given name already exists, an error message will be thrown.
     * If there is a state specified in the command, it will be checked to ensure that it is "On" or "Off". If the state
     * is not specified or is not valid, an error message will be thrown.
     * If the value for 'kelvinValue' is not positive, an error message will be thrown.
     * Any inconvenience command will throw an exception.
     * @param command Appropriate command that includes the type of the device, name of the device and optionally
     * (state, kelvin value, brightness percentage)
     */
    public void smartLambAddCommandHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand != 6 && lengthOfTheCommand != 4 && lengthOfTheCommand != 3){
            throwError("ERROR: Erroneous command!");
            return;
        }
        addHandler(command);
        if(errorExist){return;}
        if (command.split("\t").length  == 6) {
            try{
                int kelvinValue = Integer.parseInt(command.split("\t")[4]);
                setKelvinHandler(kelvinValue);

                if (!errorExist) {
                    int brightnessValue = Integer.parseInt(command.split("\t")[5]);
                    setBrightnessHandler(brightnessValue);
                }
            }
            catch (Exception e){
                throwError("ERROR: Erroneous command!");
            }

        }
    }

    /**
     * This method handles a command's exceptions which set the kelvin of a smart device.
     * The value should be in the range of 2000K-6500K.
     * If the value is not in the correct range, an error message will be thrown.
     * @param kelvinValue the desired kelvin value.
     * @throws RuntimeException if the kelvin value is not in the correct range
     */
    public void setKelvinHandler(int kelvinValue) throws IOException {
        if(kelvinValue> 6500 || kelvinValue < 2000){
            throwError("ERROR: Kelvin value must be in range of 2000K-6500K!");
        }
    }
    /**
     * This method handles a command's exceptions which set the brightness of a smart device.
     * The value should be in the range of 0%-100%.
     * If the value is not in the correct range, an error message will be thrown.
     * @param brightnessPercentage the desired brightness as a percentage
     * @throws RuntimeException if the brightness value is not in the correct range
     */
    public void setBrightnessHandler(int brightnessPercentage) throws IOException {
        if(brightnessPercentage > 100 || brightnessPercentage < 0){
            throwError("ERROR: Brightness must be in range of 0%-100%!");
        }
    }
    /**
     * This method handles a command's exception which set the kelvin or brightness of a smart lamp.
     * If the command is not in the correct format, an error message will be thrown. If there is no device with the specified
     * name, an error message will be thrown. If the specified device is not a smart lamp, an error message will be thrown.
     * If value of the brightness and kelvin is not in the correct range or not an integer value an error message will be thrown.
     * @param command Appropriate command to set the kelvin or brightness of a smart lamp.
     * @throws RuntimeException if the command is not in the correct format, there is no device with the specified name, or the specified device is not a smart lamp
     * or, kelvin or brightness not in the correct range.
     */
    public void setKelvinAndBrightnessHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand != 3){
            throwError("ERROR: Erroneous command!");
            return;
        }
        try {
            String nameOfTheIntendedDevice = command.split("\t")[1];

            Smart_Lamp smartLamp = (Smart_Lamp) SmartSystemMethods.findDeviceByName(nameOfTheIntendedDevice);
            if(smartLamp==null){
                throwError("ERROR: There is not such a device!");
                return;
            }

            int kelvinValue = Integer.parseInt(command.split("\t")[2]);

            String action = command.split("\t")[0];
            if(action.equals("SetKelvin")){
                setKelvinHandler(kelvinValue);
            }
            else if (action.equals("SetBrightness")) {
                setBrightnessHandler(kelvinValue);
            }

        }

        catch (ClassCastException e){
            throwError("ERROR: This device is not a smart lamp!");
        }
        catch (NumberFormatException e){
            throwError("ERROR: Erroneous command!");
        }
    }
    /**
     * This method handles a command to set the kelvin and brightness of a smart lamp.
     * If the command is not in the correct format, an error message will be thrown. If there is no device with the specified
     * name, an error message will be thrown. If the specified device is not a smart lamp, an error message will be thrown.
     * If value of the brightness and kelvin is not in the correct range or not an integer value an error message will be thrown.
     * @param command Appropriate command to set the kelvin and brightness of a smart lamp.
     * @throws RuntimeException if the command is not in the correct format, there is no device with the specified name, or the specified device is not a smart lamp
     * or, kelvin and brightness not in the correct range.
     */
    public void setWhiteHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;
        if(lengthOfTheCommand != 4){
            throwError("ERROR: Erroneous command!");
            return;
        }
        try{
            String nameOfTheIntendedDevice = command.split("\t")[1];

            Smart_Lamp smartLamp = (Smart_Lamp) SmartSystemMethods.findDeviceByName(nameOfTheIntendedDevice);
            if(smartLamp==null){
                throwError("ERROR: There is not such a device!");
                return;
            }

            int kelvinValue = Integer.parseInt(command.split("\t")[2]);
            int brightnessValue = Integer.parseInt(command.split("\t")[3]);
            setKelvinHandler(kelvinValue);

            if(!errorExist) {
                setBrightnessHandler(brightnessValue);
            }
        }
        catch (ClassCastException e){
            throwError("ERROR: This device is not a smart lamp!");
        }
        catch (NumberFormatException e){
            throwError("ERROR: Erroneous command!");
        }
    }
}
