import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class implements the SmartCameraInterface and provides methods for editing common properties of a smart devices(camera, plug, lamp).
 */

public class EditSmartDevices implements EditSmartDeviceInterface{
    /**
     * Removes a smart device from the system.
     * It uses the 'SmartSystemMethods' class to find the device in the list of devices
     * and sets its state to 'On' to ensure that all devices will turn off in 'switchDeviceState' method.
     * The method then switches the state of the device, generates a Z report and removes the device from the 'deviceList'.
     * @param command Appropriate command which contains name of the device will be removed.
     */
    public void removeDevice(String command) throws IOException {
        String nameOfTheDeviceWillBeRemoved = command.split("\t")[1];
        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(nameOfTheDeviceWillBeRemoved);

        smartDevice.setState("On");
        switchDeviceState(smartDevice);

        IO.myWriter.write("SUCCESS: Information about removed smart device is as follows:"+"\n");
        SmartSystemMethods.zReport(smartDevice);

        MethodResolver.deviceList.removeIf((SmartDevices b) ->( b.getName().equals(smartDevice.getName())));
    }


    /**
     * Updates the 'demonstrationOfMb' attribute for a smart camera and the 'displayOfTotalWattEnergy' attribute for a smart plug.
     * The method first checks the type of the passed 'smartDevice' object and casts it to the appropriate class (Smart_Camera or Smart_Plug).
     * If it is a Smart_Camera object, it sets the 'demonstrationOfMb' attribute to a formatted string representing the total storage used by the camera.
     * If it is a Smart_Plug object, it sets the 'displayOfTotalWattEnergy' attribute to a formatted string representing the total watt energy consumed by the plug.
     * @param smartDevice The smart device object for which to update the demonstrationOfMb or displayOfTotalWattEnergy attribute.
     */
    public void updateSmartPlugAndSmartCamera(SmartDevices smartDevice){
        if (smartDevice.getType().equals("SmartCamera") && smartDevice.getState().equals("Off")) {
            Smart_Camera smartCamera = (Smart_Camera) smartDevice;
            smartCamera.setDemonstrationOfMb(String.format("%.2f", smartCamera.getTotalStorageHasBeenUsed()));
        }
        else if (smartDevice.getType().equals("SmartPlug") && smartDevice.getState().equals("Off")) {
            Smart_Plug smartPlug = (Smart_Plug) smartDevice;
            smartPlug.setDisplayOfTotalWattEnergy(String.format("%.2f", smartPlug.getTotalWattEnergy()));
        }
    }

    /**
     * Switches the state of a smart device to either 'On' or 'Off', based on the given command.
     * The method first takes the name of the device from the 'command'.
     * It then uses the 'SmartSystemMethods' class to find the device in the list of devices.
     * If it is switched to off the method calls updateSmartPlugAndSmartCamera() to update
     * smart plug's total watt energy demonstration and smart camera's total storage demonstration.
     * @param command Appropriate command to switch state of the device.
     */
    public void switchDeviceState(String command){
        String intendedState = command.split("\t")[2];
        String nameOfTheSmartDeviceWillBeSwitched = command.split("\t")[1];
        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(nameOfTheSmartDeviceWillBeSwitched);

        if (intendedState.equals("Off")) {
            smartDevice.setState("Off");
            updateSmartPlugAndSmartCamera(smartDevice);
        }
        else if (intendedState.equals("On")) {
            smartDevice.setState("On");
        }
        smartDevice.setNewSwitchTime(null);
        smartDevice.setStateSwitchedTime(MethodResolver.currentTime);
    }


    /**
     * This method switches the state of a smart device from 'On' to 'Off' or vice versa.
     * If the device is currently 'Off', it sets the state to 'On'. If the device is currently 'On', it sets the state to 'Off'.
     * If the state is changed to 'Off', the method calls updateSmartPlugAndSmartCamera() to update the energy and storage of smart plugs and smart cameras.
     * @param smartDevice the smart device whose state will be switched
     */
    public void switchDeviceState(SmartDevices smartDevice){
        if(smartDevice.getState().equals("Off")){
            smartDevice.setState("On");
        }
        else if (smartDevice.getState().equals("On")) {
            smartDevice.setState("Off");
            updateSmartPlugAndSmartCamera(smartDevice);
        }
        smartDevice.setNewSwitchTime(null);
    }
    /**
     * Updates the name of a smart device in the system with the provided new name.
     * This method extracts the new name for the device from the input command and retrieves the smart device object from the system using its current name.
     * It then updates the name of the device with the new name.
     * @param command Appropriate command which contains information about current name and new name.
     */
    public void changeDeviceName(String command){
        String currentNameOfSmartDevice = command.split("\t")[1];
        String newNameOfSmartDevice = command.split("\t")[2];

        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(currentNameOfSmartDevice);
        smartDevice.setName(newNameOfSmartDevice);
    }
    /**
     * Sets the switch time for a specific device by information provided by command.
     * where deviceName is the name of the device and switchTime is a date.
     * If the command is not in the correct format, an error message throws.
     * 'dateFormat.setLenient' method helps to throw exception for inconvenience dates such as '2020-40-90_29:12321:99'.
     * If the specified switch time is in the past, before the current time, an error message throws indicates that switch time is in the past.
     * If the specified device name is not found in the device list, an error message throws.
     * @param command Appropriate command to execute setSwitchTime action.
     * @throws ParseException if the date and time in the command are not in the correct format.
     * @throws IllegalArgumentException if the command specifies a switch time in the past
     * @throws NullPointerException if the command is null or if the device with the specified name is not found in the device list
     */
    public boolean setSwitchTimeOfDevices(String command) throws ParseException, IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand!= 3){
            IO.myWriter.write("ERROR: Erroneous command!"+"\n");
            return true;
        }

        String date = command.split("\t")[2];
        DateFormat properFormatForDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        try{
            String nameOfTheDevice = command.split("\t")[1];
            SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(nameOfTheDevice);

            if(smartDevice == null){
                IO.myWriter.write("ERROR: There is not such a device!"+"\n");
                return true;
            }
            properFormatForDate.setLenient(false);
            Date switchTimeForDevice = properFormatForDate.parse(date);

            if(MethodResolver.currentTime.getTime() < switchTimeForDevice.getTime()){
                smartDevice.setNewSwitchTime(switchTimeForDevice);
                return false;
            }
            else if (MethodResolver.currentTime.equals(switchTimeForDevice)) {
                smartDevice.setNewSwitchTime(switchTimeForDevice);
               return false;
            }
            else{
                IO.myWriter.write("ERROR: Switch time cannot be in the past!"+"\n");
                return true;
            }
        }
        catch (ParseException e){
            IO.myWriter.write("ERROR: Time format is not correct!"+"\n");
            return true;
        }

    }

    /**
     * Checks the switch times of the smart devices in the system and updates their states if time has come.
     * This method iterates through the list of smart devices and checks if each device has a switch time.
     * If a switch time has been set, and time has come, the method updates the device's state and old switch time
     * and switched time using the new switch time, and sets the new switch time to null.
     */
    public void checkSwitchTimes() throws IOException {
        for(SmartDevices smartDevice : MethodResolver.deviceList){
            if(smartDevice.getNewSwitchTime()== null){
                continue;
            }

            if(smartDevice.getNewSwitchTime().getTime()<= MethodResolver.currentTime.getTime()){
                smartDevice.setStateSwitchedTime(smartDevice.getNewSwitchTime());
                smartDevice.setOldSwitchTime(smartDevice.getNewSwitchTime());
                switchDeviceState(smartDevice);
            }
        }
    }
    public boolean nop() throws IOException {
        /**
         * Determines which device in the device list has the closest switch time and sets the current time to that switch time.
         * Then, for each device in the device list, if the device has a new switch time that is equal to the closest switch time,
         * the method switches the device state, updates the device's old switch time to the new switch time, and resets the new switch time to null.
         * If the device list is empty or if there are no devices, the method prints an error message to the console.
         * @throws Exception if the device list is null, or there is no switch time to switch.
         */
        try{
            EditSmartPlug editSmartPlug = new EditSmartPlug();
            EditSmartCamera editSmartCamera = new EditSmartCamera();
            SmartSystemMethods.sortDeviceList();

            Date devicesWithClosestSwitchTimes = MethodResolver.deviceList.get(0).getNewSwitchTime();
            if(devicesWithClosestSwitchTimes == null){
                IO.myWriter.write("ERROR: There is nothing to switch!"+"\n");
                return true;
            }

            MethodResolver.currentTime = devicesWithClosestSwitchTimes;

            editSmartPlug.calculateTotalWattEnergy();
            editSmartCamera.calculateTotalStorageHasBeenUsed();

            for(SmartDevices smartDevice : MethodResolver.deviceList){
                updateSmartPlugAndSmartCamera(smartDevice);
                if(smartDevice.getNewSwitchTime() == null){
                    continue;
                }
                else if(smartDevice.getNewSwitchTime().equals(devicesWithClosestSwitchTimes)){
                    smartDevice.setStateSwitchedTime(MethodResolver.currentTime);
                    smartDevice.setOldSwitchTime(smartDevice.getNewSwitchTime());
                    switchDeviceState(smartDevice);
                    smartDevice.setNewSwitchTime(null);
                    }
                }
        }
        catch (Exception e){
            IO.myWriter.write("ERROR: There is nothing to switch!"+"\n");
            return true;
        }
        return false;
    }
}
