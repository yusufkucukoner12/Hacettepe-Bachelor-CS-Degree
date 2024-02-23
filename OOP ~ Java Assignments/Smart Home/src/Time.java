import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    /**
     * Sets the initial time of the program using the given date format "yyyy-MM-dd_HH:mm:ss".
     * If the format is incorrect, the program will terminate with an error message.
     * If the given command isn't a valid 'setInitialTime' format, the program will terminate with an error message.
     * @param command Appropriate command contains the initial time to set in the format "yyyy-MM-dd_HH:mm:ss"
     * @throws ParseException if the format of the initial date is incorrect
     * @throws ArrayIndexOutOfBoundsException if the format of the command is incorrect.
     */
    public static void InitializeTime(String command) throws ParseException, IOException  {
        String date = null;

        try{
        date = command.split("\t")[1];
        }
        catch (ArrayIndexOutOfBoundsException e){
            IO.myWriter.write("ERROR: First command must be set initial time! Program is going to terminate!"+"\n");
            IO.myWriter.close();
            System.exit(1);
        }

        DateFormat properDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        try{
            properDateFormat.setLenient(false);
            Date newTime = properDateFormat.parse(date);
            MethodResolver.currentTime = newTime;
            IO.myWriter.write("SUCCESS: Time has been set to " + Time.dateConverter(MethodResolver.currentTime) +"!"+"\n");
        }
        catch (ParseException e) {
            IO.myWriter.write("ERROR: Format of the initial date is wrong! Program is going to terminate!"+"\n");
            IO.myWriter.close();
            System.exit(1);
        }
    }
    /**
     * Sets the time of the program using the given date format "yyyy-MM-dd_HH:mm:ss".
     * If the format is incorrect, the method throws an error message.
     * If the given command isn't a valid 'setTime' format, the method throws an error message.
     * If the given date is beyond the current date method throws an error message.
     * @param command Appropriate command contains the initial time to set in the format "yyyy-MM-dd_HH:mm:ss"
     * @throws ParseException if the format of the date is incorrect
     * @throws ArrayIndexOutOfBoundsException if the format of the command is incorrect.
     */
    public static boolean setTime(String command) throws ParseException, IOException {
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand != 2){
            IO.myWriter.write("ERROR: Erroneous command!"+"\n");
            return true;
        }

        String date = command.split("\t")[1];
        DateFormat properDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        try{
            properDateFormat.setLenient(false);
            Date newTime = properDateFormat.parse(date);

            if(newTime.getTime() > MethodResolver.currentTime.getTime()){
                EditSmartDevices editSmartDevices = new EditSmartDevices();

                SmartSystemMethods.sortDeviceList();
                Date devicesWithClosestSwitchTimes = MethodResolver.deviceList.get(0).getNewSwitchTime();

                // If switch times will be executed after setting time. Helps to calculate energy and memory exactly with advancing time with nop command step by step.
                // (Explained in report more detailed.)

                if(devicesWithClosestSwitchTimes != null) {
                    while (newTime.getTime() > devicesWithClosestSwitchTimes.getTime()) {
                        editSmartDevices.nop();

                        SmartSystemMethods.sortDeviceList();
                        devicesWithClosestSwitchTimes = MethodResolver.deviceList.get(0).getNewSwitchTime();

                        if(devicesWithClosestSwitchTimes == null){
                            break;
                        }
                    }
                }
                MethodResolver.currentTime = newTime;
                return false;
            }
            else if (newTime.getTime() == MethodResolver.currentTime.getTime()){
                IO.myWriter.write("ERROR: There is nothing to change!"+"\n");
                return true;
            }
            else {
                IO.myWriter.write("ERROR: Time cannot be reversed!"+"\n");
                return true;
            }

        }
        catch (ParseException e) {
            IO.myWriter.write("ERROR: Time format is not correct!"+"\n");
            return true;
        }
    }
    /**
     * This method is used to skip a certain amount of minutes in the simulation time.
     * If the given command isn't valid the method will throw an error message.
     * If amount of minutes provided less than zero or zero by command the method will throw an error message.
     * If amount of minutes isn't provided integer by command the method will throw an error message.
     @param command Appropriate command which contains amount of minutes which will be skipped.
     @return void
     */
    public static boolean skipMinutes(String command) throws IOException {
        int skipMinute = 0;
        int lengthOfTheCommand = command.split("\t").length;

        if(lengthOfTheCommand != 2){
            IO.myWriter.write("ERROR: Erroneous command!"+"\n");
            return true;
        }

        try{
            skipMinute = Integer.parseInt(command.split("\t")[1]);
            if(skipMinute<0){
                IO.myWriter.write("ERROR: Time cannot be reversed!"+"\n");
                return true;
            }
            else if (skipMinute == 0){
                IO.myWriter.write("ERROR: There is nothing to skip!"+"\n");
                return true;
            }
        }
        catch (Exception e){
            IO.myWriter.write("ERROR: Erroneous command!"+"\n");
            return true;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(MethodResolver.currentTime);
        calendar.add(Calendar.MINUTE, skipMinute);

        EditSmartDevices editSmartDevices = new EditSmartDevices();

        SmartSystemMethods.sortDeviceList();
        Date devicesWithClosestSwitchTimes = MethodResolver.deviceList.get(0).getNewSwitchTime();

        Date newTime = calendar.getTime();

        // If switch times will be executed after setting time. Helps to calculate energy and memory exactly with advancing time with nop command step by step.
        // (Explained in report more detailed.)

        if(devicesWithClosestSwitchTimes != null) {
            while (newTime.getTime() > devicesWithClosestSwitchTimes.getTime()) {
                editSmartDevices.nop();

                SmartSystemMethods.sortDeviceList();
                devicesWithClosestSwitchTimes = MethodResolver.deviceList.get(0).getNewSwitchTime();

                if(devicesWithClosestSwitchTimes == null){
                    break;
                }
            }
        }

        MethodResolver.currentTime = newTime;
        return false;
    }
    /**
     * This method converts a given Date object to a string format of "yyyy-MM-dd_HH:mm:ss".
     * If the input date is null, the method returns null. Otherwise, it first converts the input date to a string format of
     * "EEE MMM dd HH:mm:ss zzz yyyy". Then it turns "EEE MMM dd HH:mm:ss zzz yyyy"
     * string to "yyyy-MM-dd_HH:mm:ss" with using dateFormatToTurn.
     * @param date A Date object to be converted to a string date format.
     * @return A string date format of "yyyy-MM-dd_HH:mm:ss" if the input date is not null. Otherwise, returns null.
     */
    public static String dateConverter(Date date){
        if(date == null) {
            return null;
        }
        else{
            String stringDate = date.toString();
            SimpleDateFormat dateFormatToCreate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            try {
                Date formattedDate = dateFormatToCreate.parse(stringDate);
                SimpleDateFormat dateFormatToTurn = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String dateInWantedForm = dateFormatToTurn.format(formattedDate);
                return dateInWantedForm;
            } catch (Exception e) {
                e.printStackTrace(); // Creates same message for exception as Java.
            }
            return null;
        }
    }
}
