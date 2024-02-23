/**
 * This interface defines the methods that a smart lamp can implement.
 */
public interface EditSmartLampInterface {

    /**
     * Adds a smart lamp to the system.
     * @param smartLamp The smart lamp to add to the system.
     * @param command Appropriate command which contain information about the smart lamp which will be added.
     * Correct command structures as follows:
     * Add <TAB> SmartLamp <TAB> <NAME>
     * Add <TAB> SmartLamp <TAB> <NAME> <TAB> <INITIAL_STATUS>
     * Add <TAB> SmartLamp <TAB> <NAME> <TAB> <INITIAL_STATUS> <TAB> <KELVIN_VALUE> <TAB> <BRIGHTNESS>
     * <NAME> and <INITIAL_STATUS>(On/Off) are String.
     * <KELVIN_VALUE>(between 2000-6500) and <BRIGHTNESS>(between 0-100) are Integer.
     */
    void addSmartLamp(Smart_Lamp smartLamp, String command);
    /**
     * Changes the Kelvin temperature of the smart lamp.
     * @param command Appropriate command to execute the change kelvin value of the smart lamp.
     * Correct command structure as follows:
     * SetKelvin <TAB> <NAME> <TAB> <KELVIN>
     * <NAME> is String.
     * <KELVIN_VALUE>(between 2000-6500) is Integer.
     */
    void changeKelvin(String command);
    /**
     * Changes the brightness percentage of the smart lamp.
     * @param command The command to execute to change the brightness percentage of the smart lamp.
     * SetBrightness <TAB> <NAME> <TAB> <BRIGHTNESS>
     * <NAME> is String.
     * <BRIGHTNESS>(between 0-100) is Integer.
     */
    void changeBrightness(String command);

    /**
     * Sets the smart lamp color to kelvin mode.
     * @param command Appropriate command to execute to set the smart lamp color to kelvin mode.
     * Correct command structures as follows: SetWhite <TAB> <NAME> <TAB> <KELVIN> <TAB> <BRIGHTNESS>
     * <NAME> is String.
     * <KELVIN>(between 2000-6500) and <BRIGHTNESS>(between 0-100) are Integer.
     */
    void setWhite(String command);
}