/**
 * This class implements the SmartPlugInterface and provides methods for adding a smart lamp device
 * and editing the properties of a smart plug device.
 */
public class EditSmartPlug implements EditSmartPlugInterface{
    /**
     * Creates a new Smart_Plug object with the specified type, name, state, ampere and adds it to the device list.
     * If the command string contains a state, ampere parameter, the method creates a Smart_Camera object with the specified state, ampere as well.
     * @param smartPlug the Smart_Plug object to create and add to the device list
     * @param command  Appropriate command contains the type, name, state, ampere of the Smart_Plug object.
     */
    public void addSmartPlug(Smart_Plug smartPlug, String command){
        int lengthOfTheCommand = command.split("\t").length;
        String type = command.split("\t")[1];
        String name = command.split("\t")[2];

        if(lengthOfTheCommand == 3) {
            smartPlug = new Smart_Plug(type,name);
            MethodResolver.deviceList.add(smartPlug);
        }


        if (lengthOfTheCommand == 4 ) {
            String state = command.split("\t")[3];

            smartPlug = new Smart_Plug(type,name,state);
            MethodResolver.deviceList.add(smartPlug);
        }



        if (lengthOfTheCommand == 5 ) {
            String state = command.split("\t")[3];
            double ampere = Double.parseDouble(command.split("\t")[4]);

            smartPlug = new Smart_Plug(type,name,state,ampere);
            smartPlug.setPlugState(true);
            MethodResolver.deviceList.add(smartPlug);
        }
    }

    /**
     * Calculates the total amount of energy that has been consumed by each plug individually.
     * This method iterates through the list of smart devices and checks if each device is a smart plug that is currently on and plugged in.
     * If it is, it calculates the time difference since the smart plug was switched on and plugged in, and uses it to calculate the amount of energy consumed.
     */

    public void calculateTotalWattEnergy(){
        for(SmartDevices smartDevice : MethodResolver.deviceList){
            if(smartDevice.getType().equals("SmartPlug")){
                Smart_Plug smartPlug = (Smart_Plug) smartDevice;
                if(smartPlug.isPlugState() && smartPlug.getState().equals("On")){
                    smartPlug.setDifferenceMilliSeconds(
                            MethodResolver.currentTime.getTime()-smartPlug.getStateSwitchedTime().getTime()
                    );
                    smartPlug.setTotalWattEnergy(smartPlug.getTotalWattEnergy() +
                                    (((smartPlug.getDifferenceMilliSeconds() / (1000*60)) * smartPlug.getAmpere() * 220)/60));
                }
                smartPlug.setStateSwitchedTime(MethodResolver.currentTime);
            }
        }
    }

    /**
     * The method finds the device with corresponding name and cast it into 'Smart_Plug'.
     * Plugs in a Smart_Plug device and updates its current value based on command.
     * @param command Appropriate command to plug in and give new current value.
     */
    public void plugIn(String command){
        String nameOfTheDevice = command.split("\t")[1];
        double currentValue = Double.parseDouble(command.split("\t")[2]);

        Smart_Plug smartPlug = (Smart_Plug) SmartSystemMethods.findDeviceByName(nameOfTheDevice);

        smartPlug.setPlugState(true);
        smartPlug.setAmpere(currentValue);
    }
    /**
     * The method finds the device with corresponding name and cast it into 'Smart_Plug'.
     * Plugs out a Smart_Plug device.
     * @param command Appropriate command to plug out.
     */
    public void plugOut(String command){
        String nameOfTheDevice = command.split("\t")[1];

        Smart_Plug smartPlug = (Smart_Plug) SmartSystemMethods.findDeviceByName(nameOfTheDevice);

        smartPlug.setPlugState(false);
    }
}
