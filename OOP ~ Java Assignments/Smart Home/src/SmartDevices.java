import java.util.Date;

/**
 * The SmartDevices class represents a smart device with a state, type, name, and time information.
 * It contains methods for getting and setting the device's attributes.
 */
public class SmartDevices {

    /**
     * The current state of the device.
     */
    private String state = "Off";
    /**
     * The name of the device.
     */
    private String name;
    /**
     * The time at which the device will be switched on or off.
     */
    private Date newSwitchTime;
    /**
     * The type of the device.
     */
    private String type;
    /**
     * The time at which the device was last switched off with time not direct command.
     */
    private Date oldSwitchTime;
    /**
     * The time at which the device's state was last switched whether with time or command.
     */
    private Date stateSwitchedTime;
    /**
     * The time difference in milliseconds between the state switched time to current time for calculating consuming energy, using storage.
     */
    private long differenceMilliSeconds;
    /**
     * Constructs a new SmartDevices object with the specified type, name, and state.
     * @param type the type of the device
     * @param name the name of the device
     * @param state the initial state of the device
     */
    SmartDevices(String type, String name, String state){
        this.type = type;
        this.name = name;
        this.state = state;
        stateSwitchedTime = MethodResolver.currentTime;
    }
    /**
     * Constructs a new SmartDevices object with the specified type and name.
     * @param type the type of the device
     * @param name the name of the device
     */
    SmartDevices(String type ,String name){
        this.type = type;
        this.name = name;
        stateSwitchedTime = MethodResolver.currentTime;
    }
    /**
     * Constructs a new SmartDevices object with default values.
     */
    SmartDevices(){}
    /**
     * Returns the name of the device.
     * @return the name of the device
     */
    public String getName(){return name;}
    /**
     * Returns the time at which the device will be switched on or off.
     * @return the time at which the device will be switched on or off
     */
    public Date getNewSwitchTime(){return newSwitchTime;}
    /**
     * Returns the current state of the device.
     * @return the current state of the device
     */
    public String getState(){return state;}
    /**
     * Returns the time at which the device was last switched off with time not direct command.
     * @return the time at which the device was last switched off with time not direct command.
     */
    public Date getOldSwitchTime(){return oldSwitchTime;}
    /**
     * Returns the time at which the device's state was last switched whether with time or command.
     * @return the time at which the device's state was last switched whether with time or command
     */
    public Date getStateSwitchedTime(){return stateSwitchedTime;}
    /**
     * Returns the type of the device.
     * @return the type of the device
     */
    public String getType(){return type;}
    /**
     * Returns the time difference in milliseconds between the new switch time and state switched time.
     * @return the time difference in milliseconds between the new switch time and state switched time
     */
    public long getDifferenceMilliSeconds(){return differenceMilliSeconds;}
    /**
     * Sets the name of the device.
     * @param name the new name of the device
     */
    public void setName(String name){this.name = name;}
    /**
     * Sets the current state of the device.
     * @param state the new state of the device
     */
    public void setState(String state){this.state = state;}
    /**
     * Sets the time at which the device will be switched on or off with time.
     * @param newSwitchTime the new switch time of the device
     */
    public void setNewSwitchTime(Date newSwitchTime){this.newSwitchTime = newSwitchTime;}
    /**
     * Sets the time at which the device's state was last switched.
     * @param stateSwitchedTime the new state switched time of the device
     */
    public void setStateSwitchedTime(Date stateSwitchedTime){this.stateSwitchedTime = stateSwitchedTime;}
    /**
     * Sets the time at which the device was last switched off with time.
     * @param oldSwitchTime the new old switch time of the device
     */
    public void setOldSwitchTime(Date oldSwitchTime){this.oldSwitchTime = oldSwitchTime;}
    /**
     * Sets the time difference in milliseconds between the state switched time to current time for calculating consuming energy, using storage.
     * @param differenceMilliSeconds the new difference in milliseconds of the device
     */
    public void setDifferenceMilliSeconds(long differenceMilliSeconds) {this.differenceMilliSeconds = differenceMilliSeconds;}
}
