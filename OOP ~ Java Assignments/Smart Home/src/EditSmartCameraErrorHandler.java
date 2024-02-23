import java.io.IOException;
/**
 * This class provides exception handling for EditSmartCamera class.
 */
public class EditSmartCameraErrorHandler extends  EditSmartDeviceErrorHandler{
    /**
     * This method handles a command related to properties of a smart camera while adding.
     * If a device with the given name already exists, an error will be thrown.
     * If the value for megaBytesUsingPerMinute is not positive, an error will be thrown.
     * If there is a state specified in the command, it will be checked to ensure that it is "On" or "Off". If the state
     * is not specified or is not valid, an error will be thrown.
     * Any inconvenience command will throw an exception.
     * @param command Appropriate command that includes the type of the device, name of the device, total storage used, megabytes consumed per minute
     * and (optionally) the state of the device.
     */
    public void cameraAddCommandHandler(String command) throws IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand != 4 && lengthOfTheCommand != 5){
            throwError("ERROR: Erroneous command!");
            return;
        }
        SmartDevices nameOfTheDevice = SmartSystemMethods.findDeviceByName(command.split("\t")[2]);

        if (nameOfTheDevice != null) {
            throwError("ERROR: There is already a smart device with same name!");
            return;
        }
        double megaBytesUsingPerMinute = Double.parseDouble(command.split("\t")[3]);
        if (megaBytesUsingPerMinute <= 0) {
            throwError("ERROR: Megabyte value must be a positive number!");
        }
        else if (lengthOfTheCommand > 4) {
            String stateOfTheDevice = command.split("\t")[4];

            if (!stateOfTheDevice.equals("On") && !stateOfTheDevice.equals("Off")) {
                throwError("ERROR: Erroneous command!");
            }
        }
    }
}

