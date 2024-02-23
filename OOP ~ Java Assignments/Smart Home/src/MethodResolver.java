import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The MethodResolver class is responsible for selecting and executing the appropriate method based on the input command.
 */
public class MethodResolver {
    static Date currentTime; // Date for current time.

    public static ArrayList<SmartDevices> deviceList = new ArrayList<SmartDevices>(); // To store devices into single ArrayList.
    

    /**
     * Selects and executes the appropriate method based on the input command.
     * @param input an ArrayList of strings representing the input commands to be executed
     * @throws ParseException if the input is not properly formatted
     */
    public void selectMethod(ArrayList<String> input) throws ParseException, IOException {


        boolean zReportIsLastCommand = false;

        boolean firstCommand = false;
        EditSmartDevices editSmartDevices = new EditSmartDevices(); //Creating a 'EditSmartDevice' object to edit SmartDevices.

        EditSmartCamera editSmartCamera = new EditSmartCamera(); //Creating a 'EditSmartCamera' object to edit SmartCameras.

        EditSmartPlug editSmartPlug = new EditSmartPlug(); //Creating a 'EditSmartPlug' object to edit SmartPlugs.

        EditSmartLamp editSmartLamp = new EditSmartLamp(); //Creating a 'EditSmartLamp' object to edit SmartLamps.

        EditSmartColorLamp editSmartColorLamp = new EditSmartColorLamp(); //Creating a 'EditsmartColorLamp' object to edit smartColorLamps.

        for(String command : input){
            //Creating a 'SmartDeviceErrorHandler' object to handle methods which edit SmartDevices.
            EditSmartDeviceErrorHandler smartDeviceErrorHandler = new EditSmartDeviceErrorHandler();
            //Creating a 'SmartColorLampErrorHandler' object to handle methods which edit SmartColorLamps.
            EditSmartColorLampErrorHandler smartColorLampErrorHandler = new EditSmartColorLampErrorHandler();
            //Creating a 'SmartPlugErrorHandler' object to handle methods which edit SmartPlugs
            EditSmartPlugErrorHandler smartPlugErrorHandler = new EditSmartPlugErrorHandler();
            //Creating a 'SmartLampErrorHandler' object to handle methods which edit SmartLamps.
            EditSmartLambErrorHandler smartLambErrorHandler = new EditSmartLambErrorHandler();
            //Creating a 'SmartCameraErrorHandler' object to handle methods which edit SmartCameras.
            EditSmartCameraErrorHandler smartCameraErrorHandler = new EditSmartCameraErrorHandler();

            zReportIsLastCommand = false;

            String action = command.split("\t")[0];
            IO.myWriter.write("COMMAND: "+command+"\n");
            //This if clause ensures that first command must be SetInitialTime.
            if(!firstCommand){
                if (action.equals("SetInitialTime")){
                    if(firstCommand){
                        IO.myWriter.write("ERROR: Erroneous command!"+"\n");
                        continue;
                    }

                    Time.InitializeTime(command);
                    firstCommand = true;
                    continue;
                }
                if (!(action.equals("SetInitialTime"))){
                    IO.myWriter.write("ERROR: First command must be set initial time! Program is going to terminate!"+"\n");
                    IO.myWriter.close();
                    System.exit(1);
                    }
                }

            if(firstCommand){
                switch(action) {
                    case "Add":
                        // extract the name of the device from the command
                        String name = command.split("\t")[1];

                        switch (name){
                            case "SmartPlug":
                                // handle errors for adding a SmartPlug device
                                smartPlugErrorHandler.plugAddCommandHandler(command);
                                if (smartPlugErrorHandler.errorExist) {continue;}

                                // create a new Smart_Plug object and add it to the list of devices
                                Smart_Plug smartPlug = new Smart_Plug();
                                editSmartPlug.addSmartPlug(smartPlug, command);
                                break;

                            case "SmartLamp":
                                // handle errors for adding a SmartLamp device
                                smartLambErrorHandler.smartLambAddCommandHandler(command);
                                if (smartLambErrorHandler.errorExist) {continue;}

                                // create a new Smart_Lamp object and add it to the list of devices
                                Smart_Lamp smartLamp = new Smart_Lamp();
                                editSmartLamp.addSmartLamp(smartLamp, command);
                                break;

                            case "SmartCamera":
                                // handle errors for adding a SmartCamera device
                                smartCameraErrorHandler.cameraAddCommandHandler(command);
                                if (smartCameraErrorHandler.errorExist) {continue;}

                                // create a new Smart_Camera object and add it to the list of devices
                                Smart_Camera smartCamera = new Smart_Camera();
                                editSmartCamera.addSmartCamera(smartCamera, command);
                                break;

                            case "SmartColorLamp":
                                // handle errors for adding a SmartColorLamp device
                                smartColorLampErrorHandler.smartColorLambAddCommandHandler(command);
                                if (smartColorLampErrorHandler.errorExist) {continue;}

                                // create a new Smart_Color_Lamp object and add it to the list of devices
                                Smart_Color_Lamp smartColorLamp = new Smart_Color_Lamp();
                                editSmartColorLamp.addSmartColorLamp(smartColorLamp, command);
                                break;
                        }
                        break;

                    case "Remove":
                        // handle errors for removing a device
                        smartDeviceErrorHandler.removeHandler(command);
                        if (smartDeviceErrorHandler.errorExist) {continue;}

                        // remove the device from the list of devices
                        editSmartDevices.removeDevice(command);
                        break;

                    case "Switch":
                        // handle errors for switching a device's state
                        smartDeviceErrorHandler.switchDeviceStateHandler(command);
                        if(smartDeviceErrorHandler.errorExist){continue;}

                        // switch the device's state (on/off)
                        editSmartDevices.switchDeviceState(command);
                        break;

                    case "PlugIn":
                        // handle errors for plugging in a SmartPlug device
                        smartPlugErrorHandler.plugInHandler(command);
                        if (smartPlugErrorHandler.errorExist) {continue;}

                        // set the SmartPlug device as plugged in
                        editSmartPlug.plugIn(command);
                        break;

                    case "PlugOut":
                        // handle errors for plugging out a SmartPlug device
                        smartPlugErrorHandler.plugOutHandler(command);
                        if (smartPlugErrorHandler.errorExist) {continue;}

                        // set the SmartPlug device as unplugged
                        editSmartPlug.plugOut(command);
                        break;

                    case "ChangeName":
                        // handle errors for changing a device's name
                        smartDeviceErrorHandler.changeHandler(command);
                        if (smartDeviceErrorHandler.errorExist) {continue;}

                        // change the device's name
                        editSmartDevices.changeDeviceName(command);
                        break;

                    case "SetKelvin":
                        // handle errors for setting the Kelvin value of a SmartLamp device
                        smartLambErrorHandler.setKelvinAndBrightnessHandler(command);
                        if (smartLambErrorHandler.errorExist) {continue;}
                        // set Kelvin value of the smart lamp.
                        editSmartLamp.changeKelvin(command);
                        break;
                     case "SetBrightness":
                         // handle errors for setting the brightness percentage of a SmartLamp device
                        smartLambErrorHandler.setKelvinAndBrightnessHandler(command);
                        if (smartLambErrorHandler.errorExist) {continue;}
                         // set brightness percentage of the smart lamp.
                        editSmartLamp.changeBrightness(command);
                        break;
                     case "SetWhite":
                         // handle errors for setting the Kelvin value and brightness percentage of a SmartLamp device
                        smartColorLampErrorHandler.setWhiteHandler(command);
                        if(smartColorLampErrorHandler.errorExist){continue;}
                         // set Kelvin value and brightness percentage of the smart lamp.
                        editSmartLamp.setWhite(command);
                        break;
                     case "SetColor":
                         // handle errors for setting the color code and brightness percentage of a SmartColorLamp device
                        smartColorLampErrorHandler.setColorHandler(command,4);
                        if (smartColorLampErrorHandler.errorExist) {continue;}
                         // set color code and brightness percentage of the smart color lamp.
                        editSmartColorLamp.setColor(command);
                        break;
                     case "SetColorCode":
                         // handle errors for setting the color code of a SmartColorLamp device
                        smartColorLampErrorHandler.setColorCodeHandler(command,3);
                        if (smartColorLampErrorHandler.errorExist) {continue;}
                         // set color code of the smart color lamp.
                        editSmartColorLamp.setColorCode(command);
                        break;
                     case "SetTime":
                        // Set the time for the system
                        boolean errorExistForSetTime = Time.setTime(command);
                        if(!errorExistForSetTime) {
                            // If no errors occurred during the setTime method call, calculates energy and storage usage, check switch times for devices
                            editSmartDevices.checkSwitchTimes();
                            editSmartPlug.calculateTotalWattEnergy();
                            editSmartCamera.calculateTotalStorageHasBeenUsed();
                        }
                        break;
                    case "SetSwitchTime":
                        // Calls the setSwitchTimeOfDevices method to set switch times for relevant devices
                        boolean errorExistForSetSwitchTime = editSmartDevices.setSwitchTimeOfDevices(command);
                        if(!errorExistForSetSwitchTime) {
                            // If no errors occurred during the setSwitchTimeOfDevices method call, check switch times for devices
                            editSmartDevices.setSwitchTimeOfDevices(command);
                            editSmartDevices.checkSwitchTimes();
                        }
                        break;

                    case "Nop":
                        // Calls the nop method to pull the closest switch times and updates energy and storage usage
                        boolean errorExistForNop = editSmartDevices.nop();
                        if(!errorExistForNop) {
                            // If no errors occurred during the nop method call, calculates energy and storage usage
                            editSmartPlug.calculateTotalWattEnergy();
                            editSmartCamera.calculateTotalStorageHasBeenUsed();
                        }
                        break;

                    case "SkipMinutes":
                        // Calls the skipMinutes method to skip a specified number of minutes and updates relevant devices
                        boolean errorExistForSkipMinutes = Time.skipMinutes(command);
                        if(!errorExistForSkipMinutes) {
                            // If no errors occurred during the skipMinutes method call, check switch times for devices, and calculates energy and storage usage
                            editSmartDevices.checkSwitchTimes();
                            editSmartPlug.calculateTotalWattEnergy();
                            editSmartCamera.calculateTotalStorageHasBeenUsed();
                        }
                        break;

                    case "ZReport":
                        // Sorts the device list in complicated order.
                        SmartSystemMethods.sortDeviceList();

                        // Prints the current system time and generates a report for each device in the device list
                        IO.myWriter.write("Time is:\t" + Time.dateConverter(MethodResolver.currentTime)+"\n");
                        for (SmartDevices smartDevices : MethodResolver.deviceList) {
                            SmartSystemMethods.zReport(smartDevices);
                        }

                        zReportIsLastCommand = true;
                        break;

                    default:
                        // If none of the above cases match, prints an error message
                        IO.myWriter.write("ERROR: Erroneous command!"+"\n");
                        break;
                }
            }
        }
        if (!zReportIsLastCommand){
            IO.myWriter.write("ZReport:"+"\n");
            IO.myWriter.write("Time is:\t" + Time.dateConverter(MethodResolver.currentTime)+"\n");
            for (SmartDevices element1 : MethodResolver.deviceList) {
                SmartSystemMethods.zReport(element1);
            }
        }
        IO.myWriter.close();
    }
}

