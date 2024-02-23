import java.io.IOException;
import java.text.ParseException;

/**
 * The DeviceInterface interface defines methods that can be used to interact with different types of smart devices.
 */
public interface EditSmartDeviceInterface {

     /**
      * Removes the device with the given command.
      * @param command Appropriate command used to identify the command which contains information about the device which will be removed.
      * correct command structure as follows: Remove <TAB> <NAME>
      * <NAME> is String.
      * Any other command structure will throw an error message.
      */
     void removeDevice(String command) throws IOException;

     /**
      * Switches the state of the device with the given command.
      * @param command Appropriate command used to identify the device and which state device will be switched.
      * correct command structure as follows: Switch <TAB> <NAME> <TAB> <STATUS>
      * <NAME> and <STATUS>(On/Off) are String.
      * Any other command structure will throw an error message.
      */
     void switchDeviceState(String command);

     /**
      * Switches the state of the given smart device.
      * @param smartDevice The smart device which will be switched.
      */
     void switchDeviceState(SmartDevices smartDevice);

     /**
      * Changes the name of the device with the given command.
      * @param command Appropriate command used to identify the device current name and possible new name.
      * correct command structure as follows: ChangeName <TAB> <OLD_NAME> <TAB> <NEW_NAME>
      * <OLD_NAME> and <NEW_NAME> are String.
      * Any other command structure will throw an error message.
      */
     void changeDeviceName(String command);

     /**
      * Sets the switch time of devices.
      * @param command Appropriate command used to set the switch time of devices for state switch.
      * correct command structure as follows: SetSwitchTime <TAB> <NAME> <TAB> <YEAR>-<MONTH>-<DAY>_<HOUR>:<MINUTE>:<SECOND>
      * <NAME> is String.
      * <YEAR>, <MONTH>, <DAY>, <HOUR>, <MINUTE> and <SECOND> are Integer with appropriate forms.
      * Any other command structure will throw an error message.
      * @throws ParseException If there is an error parsing the switch time.
      */
     boolean setSwitchTimeOfDevices(String command) throws ParseException, IOException;

     /**
      * Checks the switch times of devices and switches their states accordingly.
      */
     void checkSwitchTimes() throws IOException;

     /**
      * Checks the switch times of devices and switches the closest switch times(in case 2 switch time may be same and may be closest) to current time.
      */
     boolean nop() throws IOException;

}
