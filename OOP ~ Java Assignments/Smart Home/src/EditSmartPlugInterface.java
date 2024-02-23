/**
 This interface defines the methods that a smart plug can implement.
 */
public interface EditSmartPlugInterface {

    /**
     * Calculates the watt of the smart plug has been consumed.
     */
    void calculateTotalWattEnergy();
    /**
     * Adds a smart plug to the system.
     * @param smartPlug The smart plug to add to the system.
     * @param command Appropriate command which contains information about the smart plug which will be added.
     * Correct command structures as follows:
     * Add <TAB> SmartPlug <TAB> <NAME>
     * Add <TAB> SmartPlug <TAB> <NAME> <TAB> <INITIAL_STATUS>
     * Add <TAB> SmartPlug <TAB> <NAME> <TAB> <INITIAL_STATUS> <TAB> <AMPERE>
     * <NAME> and <INITIAL_STATUS>(On/Off) are String.
     * <AMPERE> is positive Double.
     */
    void addSmartPlug(Smart_Plug smartPlug, String command);
    /**
     * Plugs in the smart plug.
     * @param command Appropriate command to execute to plug in the smart plug.
     * PlugIn <TAB> <NAME> <TAB> <AMPERE>
     * <NAME> is String.
     * <AMPERE> is positive Double.
     */
    void plugIn(String command);
    /**
     * Plugs out the smart plug.
     * @param command Appropriate command to execute to plug out the smart plug.
     * Correct command structure as follows:
     * PlugOut <TAB> <NAME>
     * <NAME> is String.
     */
    void plugOut(String command);
}