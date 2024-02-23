/**
 * This interface defines the methods that a smart lamp color can implement.
 */
public interface EditSmartColorLampInterface {

    /**
     * Adds a smart lamp color to the devices.
     * @param smartColorLamp The smart lamp color to add the system.
     * @param command Appropriate command which contains information about the smart lamp color which will be added.
     * correct command structures as follows:
     * Add <TAB> SmartColorLamp <TAB> <NAME>
     * Add <TAB> SmartColorLamp <TAB> <NAME> <TAB> <INITIAL_STATUS>
     * Add <TAB> SmartColorLamp <TAB> <NAME> <TAB> <INITIAL_STATUS> <TAB> <KELVIN_VALUE> <TAB> <BRIGHTNESS>
     * Add <TAB> SmartColorLamp <TAB> <NAME> <TAB> <INITIAL_STATUS> <TAB> <COLOR_CODE> <TAB> <BRIGHTNESS>
     * <NAME> and <INITIAL_STATUS>(On/Off) are String.
     * <KELVIN_VALUE>(between 2000-6500) and <BRIGHTNESS>(between 0-100) are Integer.
     * <COLOR_CODE>(between 0x0-0xFFFFFF).
     */
    void addSmartColorLamp(Smart_Color_Lamp smartColorLamp, String command);

    /**
     * Sets the color of the smart lamp color.
     * @param command Appropriate command to execute to set the color of the smart lamp color.
     * Correct command structures as follows: SetColor <TAB> <NAME> <TAB> <COLOR_CODE> <TAB> <BRIGHTNESS>
     * <NAME> is String.
     * <BRIGHTNESS> is Integer(between 0-100).
     * <COLOR_CODE> is HexDecimal(between 0x0-0xFFFFFF)
     */
    void setColor(String command);
    /**
     * Sets the color code of the smart lamp color.
     * @param command The command to execute to set the color code of the smart lamp color.
     */
    void setColorCode(String command);
}