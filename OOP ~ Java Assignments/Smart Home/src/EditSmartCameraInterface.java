/**
 * This interface defines the methods that a smart camera can implement.
 */
public interface EditSmartCameraInterface {

    /**
     * Calculates the total amount of storage that has been used by the smart cameras.
     */
    void calculateTotalStorageHasBeenUsed();
    /**
     * Adds a smart camera to the system.
     * @param smartCamera The smart camera to add to the system.
     * @param command Appropriate command which contains information about smart camera which will be added.
     * correct command structures as follows:
     * Add <TAB> SmartCamera <TAB> <NAME> <TAB> <MEGABYTES_CONSUMED_PER_RECORD>
     * Add <TAB> SmartCamera <TAB> <NAME> <TAB> <MEGABYTES_CONSUMED_PER_RECORD> <TAB> <INITIAL_STATUS>
     * <NAME>, <INITIAL_STATUS>(On/Off) are String.
     * <MEGABYTES_CONSUMED_PER_RECORD> is positive Double.
     * Any other command structure will throw an error message.
     */
    void addSmartCamera(Smart_Camera smartCamera, String command);
}