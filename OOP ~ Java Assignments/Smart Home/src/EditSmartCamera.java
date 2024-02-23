/**
 * This class implements the SmartCameraInterface and provides methods for adding a smart camera device
 * and editing the properties of a smart camera device.
 */
public class EditSmartCamera implements EditSmartCameraInterface{
    /**
     * Creates a new Smart_Camera object with the specified type, name, and megabytes per minute, and adds it to the device list.
     * If the command string contains a state parameter, the method creates a Smart_Camera object with the specified state as well.
     * @param smartCamera the Smart_Camera object to create and add to the device list
     * @param command Appropriate command contains type, name, megabytes per minute, state of the Smart_Camera object.
     */
    public void addSmartCamera(Smart_Camera smartCamera, String command){
        int lengthOfTheCommand = command.split("\t").length;
        String type = command.split("\t")[1];
        String name = command.split("\t")[2];
        double megabytesPerMinutes = Double.parseDouble(command.split("\t")[3]);

        if (lengthOfTheCommand == 4 ) {
            smartCamera = new Smart_Camera(type,name,megabytesPerMinutes);
            MethodResolver.deviceList.add(smartCamera);
        }
        else if (lengthOfTheCommand == 5 ) {
            String state = command.split("\t")[4];

            smartCamera = new Smart_Camera(type, name, megabytesPerMinutes, state);
            MethodResolver.deviceList.add(smartCamera);
        }
    }
 /**
  * Calculates the total amount of storage that has been used for each camera individually.
  * This method iterates through the list of smart devices and checks if each device is a smart camera that is currently on.
  * If it is, it calculates the time difference since the camera was switched on, and uses it to calculate the amount of storage used.
  */
    public void calculateTotalStorageHasBeenUsed(){
        for(SmartDevices smartDevice : MethodResolver.deviceList){
             if(smartDevice.getType().equals("SmartCamera")){
                 Smart_Camera smartCamera = (Smart_Camera) smartDevice;
                 if(smartCamera.getState().equals("On")){
                        smartCamera.setDifferenceMilliSeconds(MethodResolver.currentTime.getTime()-smartCamera.getStateSwitchedTime().getTime());
                        smartCamera.setTotalStorageHasBeenUsed(smartCamera.getTotalStorageHasBeenUsed() + smartCamera.getMegaBytesUsingPerMinute() * (smartCamera.getDifferenceMilliSeconds()/(1000*60)));
                 }
                 smartCamera.setStateSwitchedTime(MethodResolver.currentTime);
             }
         }
    }
}
