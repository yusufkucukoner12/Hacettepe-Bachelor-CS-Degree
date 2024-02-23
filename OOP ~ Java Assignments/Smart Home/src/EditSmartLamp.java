
/**
 * This class implements the SmartLampInterface and provides methods for adding a smart lamp device
 * and editing the properties of a smart lamp device.
 */
public class EditSmartLamp implements EditSmartLampInterface{
    /**
     * Creates a new Smart_Lamp object with the specified type, name, megabytes per minute, kelvin, brightness and adds it to the device list.
     * If the command string contains a state, kelvin, brightness parameter, the method creates a Smart_Camera object with the specified state, kelvin, brightness as well.
     * @param smartLamp the Smart_Lamp object to create and add to the device list
     * @param command  Appropriate command contains the type, name, state, kelvin, brightness of the Smart_Lamp object.
     */
    public void addSmartLamp(Smart_Lamp smartLamp, String command){
        int lengthOfTheCommand = command.split("\t").length;
        String type = command.split("\t")[1];
        String name = command.split("\t")[2];

        if(lengthOfTheCommand == 3) {
            smartLamp = new Smart_Lamp(type,name);
            MethodResolver.deviceList.add(smartLamp);
        }

        if (lengthOfTheCommand == 4 ) {
            String state = command.split("\t")[3];

            smartLamp = new Smart_Lamp(type, name, state);
            MethodResolver.deviceList.add(smartLamp);
        }

        if (lengthOfTheCommand == 6 ) {
            String state = command.split("\t")[3];
            int kelvin = Integer.parseInt(command.split("\t")[4]);
            int brightness = Integer.parseInt(command.split("\t")[5]);

            smartLamp= new Smart_Lamp(type, name, state, kelvin, brightness);
            MethodResolver.deviceList.add(smartLamp);
        }
    }
    /**
     * Changes the Kelvin value of a smart lamp to the given value.
     * @param command Appropriate command to change the Kelvin value of specified smart lamp.
     */
    public void changeKelvin(String command) throws NumberFormatException {
        String nameOfTheLamp = command.split("\t")[1];
        int newKelvinValue = Integer.parseInt(command.split("\t")[2]);

        Smart_Lamp smartLamp = (Smart_Lamp) SmartSystemMethods.findDeviceByName(nameOfTheLamp);
        smartLamp.setKelvin(newKelvinValue);
    }

    /**
     * Changes the brightness percentage of a smart lamp device.
     * @param command Appropriate command to change the brightness percentage of specified smart lamp.
     */
    public void changeBrightness(String command){
        String nameOfTheLamp = command.split("\t")[1];
        int brightnessPercentage = Integer.parseInt(command.split("\t")[2]);

        Smart_Lamp smartLamp = (Smart_Lamp) SmartSystemMethods.findDeviceByName(nameOfTheLamp);
        smartLamp.setBrightness(brightnessPercentage);
    }
    /**
     * The method finds the device with corresponding name and cast it into 'Smart_Color_Lamp'.
     * Then, sets a smart color lamp to white with the given kelvin value and brightness percentage provided by command.
     * @param command Appropriate command to change the brightness percentage and kelvin value of specified smart lamp
     * and turning into kelvin mode with setting color code to 'null'.
     */
    public void setWhite(String command){
        String nameOfTheDevice = command.split("\t")[1];
        int kelvinValue = Integer.parseInt(command.split("\t")[2]);
        int brightnessPercentage = Integer.parseInt(command.split("\t")[3]);

        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(nameOfTheDevice);
        Smart_Lamp smartLamp = (Smart_Lamp) smartDevice;

        smartLamp.setBrightness(brightnessPercentage);
        smartLamp.setKelvin(kelvinValue);
    }
}
