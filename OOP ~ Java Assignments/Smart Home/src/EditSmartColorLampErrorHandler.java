import java.io.IOException;
import java.util.regex.Pattern;

/**
 * This class provides exception handling for EditSmartColorLamp class.
 */
public class EditSmartColorLampErrorHandler extends EditSmartLambErrorHandler {
    /**
     * This method handles a command related to properties of a smart color lamp while adding.
     * If a device with the given name already exists, an error message will be thrown.
     * If there is a state specified in the command, it will be checked to ensure that it is "On" or "Off". If the state
     * is not specified or is not valid, an error message will be thrown.
     * If the value for 'kelvinValue' is not positive, an error message will be thrown.
     * If color code isn't a hexadecimal value or isn't in the specified range, an error message will be thrown.
     * Any inconvenience command will throw an exception.
     * @param command Appropriate command that includes the type of the device, name of the device and optionally
     * (state, kelvin value or color code, brightness percentage)
     */
    public void smartColorLambAddCommandHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if (lengthOfTheCommand != 6 && lengthOfTheCommand != 4 && lengthOfTheCommand != 3) {
            throwError("ERROR: Erroneous command!");
            return;
        }
        addHandler(command);
        if (errorExist) {return;}
        if (command.split("\t").length  == 6) {
            try {
                Integer.parseInt(command.split("\t")[4]);
                int kelvinValue = Integer.parseInt(command.split("\t")[4]);
                setKelvinHandler(kelvinValue);

                if (!errorExist) {
                    int brightnessValue = Integer.parseInt(command.split("\t")[5]);
                    setBrightnessHandler(brightnessValue);
                }
            }
            catch (Exception e) {
                String colorCode = command.split("\t")[4];
                colorCodeHandler(colorCode);
                if(!errorExist){
                    try {
                        int brightness = Integer.parseInt(command.split("\t")[5]);
                        setBrightnessHandler(brightness);
                    }
                    catch (Exception e1){
                        throwError("ERROR: Erroneous command!");
                    }
                }
            }
        }
    }

    /**
     * This method handles a command's exception which set the color code for a smart color lamp.
     * If the length of the command does not match the properLengthOfTheCommand parameter, an error will be thrown.
     * If the specified device does not exist, an error will be thrown. If the specified device is not a smart color lamp,
     * an error will be thrown. If the color code is invalid, an error will be thrown via colorCodeHandler.
     * @param command Appropriate command includes the name of the device and the color code to set.
     * @param properLengthOfTheCommand The expected length of the command. Created this parameter for using this method also for setColor method,
     * setColor includes the validating color code too so this parameter prevents code repeating.
     */
    public void setColorCodeHandler(String command,int properLengthOfTheCommand) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;
        if(lengthOfTheCommand != properLengthOfTheCommand){
            throwError("ERROR: Erroneous command!");
            return;
        }
        try {
            String nameOfTheDevice = (command.split("\t")[1]);
            Smart_Color_Lamp smartColorLamp = (Smart_Color_Lamp) SmartSystemMethods.findDeviceByName(nameOfTheDevice);
            if(smartColorLamp == null){
                throwError("ERROR: There is not such a device!");
                return;
            }
            String colorCode = command.split("\t")[2];
            colorCodeHandler(colorCode);
        }
        catch (ClassCastException e){
        throwError("ERROR: This device is not a smart color lamp!");
        }
    }
    /**
     * This method handles a command's exception which set the color and brightness for a smart color lamp.
     * If the length of the command does not match the properLengthOfTheCommand parameter, an error will be thrown via setColorCodeHandler.
     * If the specified device does not exist or is not a smart color lamp, an error will be thrown via setColorCodeHandler.
     * If the color code or brightness percentage is invalid, an error will be thrown via setBrightnessHandler.
     * @param command Appropriate command includes the name of the device, the color code, and the brightness percentage to set.
     * @param properLengthOfTheCommand The expected length of the command string.
     */
    public void setColorHandler(String command, int properLengthOfTheCommand)throws  IOException{
        setColorCodeHandler(command,properLengthOfTheCommand);
        if(!errorExist){
            int brightnessPercentage = Integer.parseInt(command.split("\t")[3]);
            setBrightnessHandler(brightnessPercentage);
        }
    }

    /**
     * This method is responsible for handling exceptions to a color code string.
     * If the color code is valid, the method returns without throwing an error. If the color code is invalid,
     * the method throws an error with a message describing the issue. A valid color code must be a hexadecimal value
     * between 0x000000 and 0xFFFFFF, inclusive.
     * @param colorCode The color code string.
     */
    public void colorCodeHandler(String colorCode) throws IOException {
        if (colorCode.startsWith("0x")) {
            try{
                long hexadecimalValueOfColorCode = Long.parseLong(colorCode.substring(2), 16);
                long maxValueColorCodeMightBe = Long.parseLong("FFFFFF", 16);
                long minValueColorCodeMightBe = Long.parseLong("000000", 16);
                if (hexadecimalValueOfColorCode > maxValueColorCodeMightBe || hexadecimalValueOfColorCode < minValueColorCodeMightBe) {
                    throwError("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                }
            }
            catch (NumberFormatException e){
                throwError("ERROR: Erroneous command!");
            }
        }
        else{
            throwError("ERROR: Erroneous command!");
        }
    }
}
