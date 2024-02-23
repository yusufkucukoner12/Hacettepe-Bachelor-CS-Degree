import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
/**
 * This class contains various methods that implement smart system's different parts.
 */
public class SmartSystemMethods {
    /**
     * Searches for a smart device in the device list by its name.
     * Iterate over the device list and return the first device with a matching name.
     * If no device is found with a matching name, return null.
     * @param nameOfTheDevice the name of the device to search for
     * @return the first smart device with a matching name, or null if no device is found
     */
    public static SmartDevices findDeviceByName(String nameOfTheDevice){
        for(SmartDevices smartDevice : MethodResolver.deviceList){
            if(smartDevice.getName().equals(nameOfTheDevice)){
                return smartDevice;
            }
        }
        return null;
    }
    /**
     * Generates a report with special properties of each smart devices.
     * The report includes information such as the device type, name, state, kelvin or color code, brightness, watt consumption, storage usage and the time to switch its status.
     * @param smartDevice the smart device to generate the report for
     */
    public static void zReport(SmartDevices smartDevice) throws IOException {

        if(smartDevice.getType().equals("SmartLamp")){
            Smart_Lamp smartLamp = (Smart_Lamp) smartDevice;

            String ZReportOfLamp = stringConverter(smartLamp.getType()) + " " + smartLamp.getName() + " is " +
                    smartLamp.getState().toLowerCase() + " and its kelvin value is " + smartLamp.getKelvin()+"K with " +
                    smartLamp.getBrightness()+"% brightness, and its time to switch its status is " +
                    Time.dateConverter(smartLamp.getNewSwitchTime())+"."+"\n"  ;

            IO.myWriter.write(ZReportOfLamp);
        }

        if(smartDevice.getType().equals("SmartColorLamp")){
            Smart_Color_Lamp smartColorLamp = (Smart_Color_Lamp) smartDevice;

            if(smartColorLamp.getKelvin() == 0){
                String ZReportOfCLamp =
                        stringConverter(smartColorLamp.getType()) + " " + smartColorLamp.getName() + " is "
                        + smartColorLamp.getState().toLowerCase() + " and its color value is " + smartColorLamp.getColorCodeO()+
                        " with " +smartColorLamp.getBrightness() + "% brightness, and its time to switch its status is " +
                        Time.dateConverter(smartColorLamp.getNewSwitchTime()) +"."+"\n" ;

                IO.myWriter.write(ZReportOfCLamp);
            }

            else{
                String ZReportOfCLamp =
                        stringConverter(smartColorLamp.getType()) + " " + smartColorLamp.getName() + " is " +
                        smartColorLamp.getState().toLowerCase() + " and its color value is " + smartColorLamp.getKelvin()+
                        "K with " +smartColorLamp.getBrightness()+"% brightness, and its time to switch its status is " +
                        Time.dateConverter(smartColorLamp.getNewSwitchTime()) +"."+"\n" ;

                IO.myWriter.write(ZReportOfCLamp);
            }
        }

        if(smartDevice.getType().equals("SmartPlug")){
            Smart_Plug smartPlug = (Smart_Plug) smartDevice;

            String ZReportOfSmartPlug =
                    stringConverter(smartPlug.getType()) + " " + smartPlug.getName() + " is " + smartPlug.getState().toLowerCase() +
                    " and consumed " + smartPlug.getDisplayOfTotalWattEnergy().replace(".",",")+
                    "W so far (excluding current device)" + ", and its time to switch its status is " +
                    Time.dateConverter(smartPlug.getNewSwitchTime())+"."+"\n" ;

            IO.myWriter.write(ZReportOfSmartPlug);
        }

        if(smartDevice.getType().equals("SmartCamera")){
            Smart_Camera smartCamera = (Smart_Camera) smartDevice;

            String ZReportOfSmartCamera =
                    stringConverter(smartCamera.getType()) + " " + smartCamera.getName() + " is " + smartCamera.getState().toLowerCase() +
                    " and used " + smartCamera.getDemonstrationOfMb().replace(".",",")+
                    " MB of storage so far (excluding current status)" + ", and its time to switch its status is " +
                    Time.dateConverter(smartCamera.getNewSwitchTime())+"."+"\n";

            IO.myWriter.write(ZReportOfSmartCamera);
        }
    }
    /**
     * This method sorts the list of smart devices based on their switch times (new or old). If both new switch times are null,
     * the method compares their old switch times. If both old switch times are null, the method considers the devices equal(Natural order).
     * If one device has a null old switch time, that device will be placed after other device. If the new switch times are
     * equal, the method returns 0. If the first device's new switch time is greater than the second device's new switch time,
     * the method returns a negative integer. If the second device's new switch time is greater than the first device's new
     * switch time, the method returns a positive integer.(-1 means o1 comes before o2, +1 means o2 comes before o1, 0 means natural order)
     */
    public static void sortDeviceList(){
        Collections.sort(MethodResolver.deviceList, new Comparator<SmartDevices>() {
            @Override
            public int compare(SmartDevices o1, SmartDevices o2) {
                if (o1.getNewSwitchTime() == null || o2.getNewSwitchTime() == null){
                    if(o1.getNewSwitchTime() == null && o2.getNewSwitchTime() == null){
                        if (o1.getOldSwitchTime() == null || o2.getOldSwitchTime() == null){
                            if(o1.getOldSwitchTime() == null){return +1;}
                            else if (o2.getOldSwitchTime() == null) {return -1;}
                            else{return 0;}
                        }
                        else{
                            int result = o1.getOldSwitchTime().compareTo(o2.getOldSwitchTime());
                            if(result == 0){return 0;}
                            return -result/Math.abs(result);
                        }
                    }
                    else if (o2.getNewSwitchTime() == null) {return -1;}

                    else if (o1.getNewSwitchTime() == null){return +1;}
                }
                else{return o1.getNewSwitchTime().compareTo(o2.getNewSwitchTime());}

                return 0;
            }
        });
    }
    /**
     * (?<=[a-z]) This assertion catches the position after lowercase.
     * (?=[A-Z]) This assertion catches the position before uppercase.
     * Combination of them help to catch position between lowercase and uppercase for example 'tL' in 'SmartLamp'.
     * @param typeOfTheDevice the type which will turn to arranged type SmartLamp, SmartPlug etc.
     */
    public static String stringConverter(String typeOfTheDevice){
        String arrangedType = typeOfTheDevice.replaceAll("(?<=[a-z])(?=[A-Z])", " ");
        return arrangedType;
    }
}
