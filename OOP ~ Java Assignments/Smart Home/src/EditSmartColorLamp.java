
/**
 * This class implements the SmartColorLampInterface and provides methods for adding a smart color lamp device
 * and editing the properties of a smart color lamp device.
 */
public class EditSmartColorLamp implements EditSmartColorLampInterface {
    /**
     * Creates a new Smart_Color_Lamp object with the specified type, name, state, kelvin or color code, brightness and adds it to the device list.
     * If the command string contains a state, kelvin or color code, brightness parameter,
     * the method creates a Smart_Camera object with the specified state, kelvin or color code and brightness as well.
     * @param smartColorLamp the Smart_Color_Lamp object to create and add to the device list
     * @param command  Appropriate command contains the type, name, state, ampere of the Smart_Plug object.
     */
    public void addSmartColorLamp(Smart_Color_Lamp smartColorLamp, String command){
        int lengthOfTheCommand = command.split("\t").length;
        String type = command.split("\t")[1];
        String name = command.split("\t")[2];
        if(lengthOfTheCommand == 3) {
            smartColorLamp = new Smart_Color_Lamp(type,name);
            MethodResolver.deviceList.add(smartColorLamp);
        }

        if (lengthOfTheCommand == 4 ) {
            String state = command.split("\t")[3];

            smartColorLamp = new Smart_Color_Lamp(type,name,state);
            MethodResolver.deviceList.add(smartColorLamp);
        }

        if (lengthOfTheCommand == 6 ) {
            try{
                String state = command.split("\t")[3];
                int kelvin = Integer.parseInt(command.split("\t")[4]);
                int brightness = Integer.parseInt(command.split("\t")[5]);

                smartColorLamp= new Smart_Color_Lamp(type,name,state,kelvin,brightness);
                MethodResolver.deviceList.add(smartColorLamp);
            }
            catch (Exception e){
                String state = command.split("\t")[3];
                String colorCode = command.split("\t")[4];
                int brightness = Integer.parseInt(command.split("\t")[5]);

                smartColorLamp= new Smart_Color_Lamp(type,name,state,colorCode,brightness);
                MethodResolver.deviceList.add(smartColorLamp);
            }
        }
    }

    /**
     * The method finds the device with corresponding name and cast it into 'Smart_Color_Lamp'.
     * Then, sets the color and brightness of a smart color lamp with a color code and brightness percentage provided by command.
     * @param command Appropriate command to change the color and brightness percentage of a smart lamp
     * and turning into color mode with setting kelvin to '0'.
     */
    public void setColor(String command){
        String nameOfTheDevice = command.split("\t")[1];
        String colorCode = command.split("\t")[2];
        int brightnessPercentage = Integer.parseInt(command.split("\t")[3]);

        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(nameOfTheDevice);
        Smart_Color_Lamp smartColorLamp = (Smart_Color_Lamp) smartDevice;

        smartColorLamp.setBrightness(brightnessPercentage);
        smartColorLamp.setKelvin(0);
        smartColorLamp.setColorCodeO(colorCode);
    }
    /**
     * The method finds the device with corresponding name and cast it into 'Smart_Color_Lamp'.
     * Then, sets the color code of a smart color lamp with a color code provided by command.
     * @param command Appropriate command to change the color code of a smart lamp.
     */
    public void setColorCode(String command){
        String nameOfTheDevice = command.split("\t")[1];
        String colorCode = command.split("\t")[2];

        SmartDevices smartDevice = SmartSystemMethods.findDeviceByName(nameOfTheDevice);
        Smart_Color_Lamp smartColorLamp = (Smart_Color_Lamp) smartDevice;

        smartColorLamp.setColorCodeO(colorCode);
    }
}
