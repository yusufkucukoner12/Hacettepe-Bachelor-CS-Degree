import java.io.IOException;

public class EditSmartPlugErrorHandler extends EditSmartDeviceErrorHandler{

    /**
     * This method handles a command related to properties of a smart plug while adding.
     * If a device with the given name already exists, an error message will be thrown.
     * If there is a state specified in the command, it will be checked to ensure that it is "On" or "Off". If the state
     * is not specified or is not valid, an error message will be thrown.
     * If the value for 'ampereValue' is not positive, an error message will be thrown.
     * Any inconvenience command will throw an exception.
     * @param command Appropriate command that includes the type of the device, name of the device and optionally
     * (state, ampere value)
     */
    public void plugAddCommandHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand != 5 && lengthOfTheCommand != 4 && lengthOfTheCommand != 3){
            throwError("ERROR: Erroneous command!");
            return;
        }
        addHandler(command);
        if(errorExist){return;}
        if (lengthOfTheCommand == 5) {
            try{
                double ampereValue = Double.parseDouble(command.split("\t")[4]);

                if(ampereValue<=0){
                    throwError("ERROR: Ampere value must be a positive number!");
                }
            }
            catch (Exception e){
                throwError("ERROR: Erroneous command!");
            }
        }



    }
    /**
     * This method handles a command's exception which plug in a smart plug.
     * If the command is not in the correct format, an error message will be thrown. If there is no device with the specified
     * name, an error message will be thrown. If the specified device is not a smart plug, an error message will be thrown.
     * If that smart plug already plugged in or ampere value not positive, an error message will be thrown,
     * @param command Appropriate command to plug in a smart plug.
     * @throws RuntimeException if the command is not in the correct format, there is no device with the specified name, or the specified device is not a smart plug
     * or, already plugged in or ampere value not in the correct range.
     */
    public void plugInHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;
        if(lengthOfTheCommand != 3){
            throwError("ERROR: Erroneous command!");
            return;
        }
        try{
            String nameOfTheIntendedDevice = command.split("\t")[1];
            Smart_Plug smartPlug = (Smart_Plug) SmartSystemMethods.findDeviceByName(nameOfTheIntendedDevice);
            double currentValue = Double.parseDouble(command.split("\t")[2]);

            if (smartPlug.isPlugState()) {
                throwError("ERROR: There is already an item plugged in to that plug!");
            }
            else if (currentValue <= 0) {
                throwError("ERROR: Ampere value must be a positive number!");
            }
        }
        catch (NullPointerException e){
            throwError("ERROR: There is not such a device!");
        }
        catch (ClassCastException e){
            throwError("ERROR: This device is not a smart plug!");
        }
    }
    /**
     * This method handles a command's exception which plug out a smart plug.
     * If the command is not in the correct format, an error message will be thrown. If there is no device with the specified
     * name, an error message will be thrown. If the specified device is not a smart plug, an error message will be thrown.
     * If that smart plug already plugged out, an error message will be thrown,
     * @param command Appropriate command to plug out a smart plug.
     * @throws RuntimeException if the command is not in the correct format, there is no device with the specified name, or the specified device is not a smart plug
     * or, already plugged out.
     */
    public void plugOutHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;
        if(lengthOfTheCommand != 2){
            throwError("ERROR: Erroneous command!");
            return;
        }
        try{
            String nameOfTheIntendedDevice = command.split("\t")[1];
            Smart_Plug smartPlug = (Smart_Plug) SmartSystemMethods.findDeviceByName(nameOfTheIntendedDevice);

            if (!smartPlug.isPlugState()) {
                throwError("ERROR: This plug has no item to plug out from that plug!");
            }
        }
        catch (NullPointerException e){
            throwError("ERROR: There is not such a device!");
        }
        catch (ClassCastException e){
            throwError("ERROR: This device is not a smart plug!");
        }
    }
}
