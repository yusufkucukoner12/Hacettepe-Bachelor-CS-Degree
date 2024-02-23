import java.io.IOException;

/**
 * This class provides exception handling for EditSmartDevices class.
 */
public class EditSmartDeviceErrorHandler {
    boolean errorExist = false;
    public void throwError(String errorMessage) throws IOException {
        try {
            throw new RuntimeException(errorMessage);
        }
        catch (RuntimeException error) {
            IO.myWriter.write(error.getMessage()+"\n");
            errorExist = true;
        }
    }
    /**
     * This method handles a command which contains common properties of smart devices while adding.
     * If a device with the given name already exists, an error message will be thrown.
     * If there is a state specified in the command, it will be checked to ensure that it is "On" or "Off". If the state
     * is not specified or is not valid, an error message will be thrown.
     * @param command Appropriate command that includes the type of the device, name of the device and optionally
     * (state and specific properties of smart device)

     */
    public void addHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;
        SmartDevices name = SmartSystemMethods.findDeviceByName(command.split("\t")[2]);

        if (name != null) {
            throwError("ERROR: There is already a smart device with same name!");
            return;
        }
        if (lengthOfTheCommand > 3) {
            String state = command.split("\t")[3];

            if (!state.equals("On") && !state.equals("Off")) {
                throwError("ERROR: Erroneous command!");
            }
        }
    }



    /**
     * This method is responsible for handling a command's exception which switch the state of a smart device with a given name
     * to the intended state. The method first checks if the command has the correct number of arguments. If not,
     * an error will be thrown. It then finds the smart device with the given name using the findDeviceByName method from SmartSystemMethods.
     * If no device exist an error will be thrown.
     * If the device is found, the method checks if the device is already in the intended state. If the device is already
     * in the intended state, an error will be thrown.
     * @param command The command string containing the device name and intended state.
     * @throws RuntimeException if the command is not in the correct format, there is no device, or switch state is already given state.
     */

    public void switchDeviceStateHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if (lengthOfTheCommand != 3) {
            throwError("ERROR: Erroneous command!");
            return;
        }
        String nameOfTheDevice = command.split("\t")[1];
        String intendedState = command.split("\t")[2];
        SmartDevices device = SmartSystemMethods.findDeviceByName(nameOfTheDevice);

        try{
            if(device.getState().equals(intendedState)){
                throwError("ERROR: This device is already switched "+intendedState.toLowerCase()+"!");
            }
            else if (!intendedState.equals("On") && !intendedState.equals("Off")) {
                throwError("ERROR: Erroneous command!");
            }
        }
        catch (Exception e){
            throwError("ERROR: There is not such a device!");
        }
    }
    /**
     * This method handles a command's exception which change the name of a smart device.
     * If the command is not in the correct format, an error message will be thrown.
     * If intended name and current name are same an error message will be thrown.
     * If there is no device with the current name, an error message will be thrown. If there is already a device with the
     * intended name, an error message will be thrown.
     * @param command Appropriate command to change the device name.
     * @throws RuntimeException if the command is not in the correct format, there is no device with the current name,
     * or there is already a device with the intended name.
     */
    public void changeHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if (lengthOfTheCommand != 3) {
            throwError("ERROR: Erroneous command!");
            return;
        }

        String currentName = command.split("\t")[1];
        String intendedName = command.split("\t")[2];

        if (currentName.equals(intendedName)) {
            throwError("ERROR: Both of the names are the same, nothing changed!");
        }
        else {
            SmartDevices currentDevice = SmartSystemMethods.findDeviceByName(currentName);
            SmartDevices intendedDevice = SmartSystemMethods.findDeviceByName(intendedName);

            if(currentDevice == null){
                throwError("ERROR: There is not such a device!");
            }
            else if (intendedDevice!=null){
                throwError("ERROR: There is already a smart device with same name!");
            }
        }
    }
    /**
     * This method handles a command's exception which remove a smart device.
     * If the command is not in the correct format, an error message will be thrown.
     * If there is no device with the specified name, an error message will be thrown.
     * @param command the command to remove the device
     * @throws RuntimeException if the command is not in the correct format or there is no device with the specified name
     */
    public void removeHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;
        if(lengthOfTheCommand!= 2) {
            throwError("ERROR: Erroneous command!");
            return;
        }
        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(command.split("\t")[1]);
        if(smartDevice == null){
            throwError("ERROR: There is not such a device!");
        }
    }
}