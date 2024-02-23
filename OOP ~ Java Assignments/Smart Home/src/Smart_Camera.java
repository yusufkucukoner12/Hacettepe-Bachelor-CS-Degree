/**
 * The Smart_Camera class extends the SmartDevices class and represents a smart camera device.
 * It has attributes for the total storage consumed by the camera and the megabytes consumed per minute. */

public class Smart_Camera extends SmartDevices {

    /**
     * The totalStorageHasBeenUsed attribute represents the total storage used by the camera.
     */
    private double totalStorageHasBeenUsed;
    /**
     * The megaBytesUsingPerMinute attribute represents the amount of megabytes used by the camera per minute.
     */
    private double megaBytesUsingPerMinute;
    /**
     * The demonstrationOfMb attribute is used to display the total storage used by the camera.
     */
    private String demonstrationOfMb = "0,00";
    /**
     * This constructor initializes a Smart_Camera object with the provided type, name, rate of consuming megabytes per minute, and state.
     * @param type The type of the smart device which is smart camera.
     * @param name The name of the smart camera.
     * @param totalStorageHasBeenUsed The rate of using megabytes per minute.
     * @param state The state of the smart camera.
     **/
    Smart_Camera(String type, String name, double totalStorageHasBeenUsed, String state) {
        super(type,name, state);
        this.megaBytesUsingPerMinute = totalStorageHasBeenUsed;
    }
    /**
     * This constructor initializes a Smart_Camera object with the provided type, name, and rate of consuming megabytes per minute.
     * @param type The type of the smart device which is smart camera.
     * @param name The name of the smart camera.
     * @param totalStorageHasBeenUsed The rate of using megabytes per minute.
     */
    Smart_Camera(String type, String name, double totalStorageHasBeenUsed) {
        super(type, name);
        this.megaBytesUsingPerMinute = totalStorageHasBeenUsed;
    }
    /**
     * This default constructor does nothing.
     */
    Smart_Camera(){}
    /**
     * This method sets the total storage used by the camera.
     * @param totalStorageHasBeenUsed The total storage used by the camera.
     */
    public void setTotalStorageHasBeenUsed(double totalStorageHasBeenUsed){
        this.totalStorageHasBeenUsed= totalStorageHasBeenUsed;
    }
    /**
     * This method returns the total storage used by the smart camera.
     * @return The total storage used by the smart camera.
     */
    public double getTotalStorageHasBeenUsed() {
        return totalStorageHasBeenUsed;
    }
    /**
     * This method returns the number of megabytes used by the camera per minute.
     * @return The rate of using megabytes per minute.
     */
    public double getMegaBytesUsingPerMinute() {
        return megaBytesUsingPerMinute;
    }
    /**
     * This method sets the string representation of the total storage used by the camera.
     * @param demonstrationOfMb The string representation of the total storage used by the camera.
     */
    public void setDemonstrationOfMb(String demonstrationOfMb){
        this.demonstrationOfMb = demonstrationOfMb;
    }
    /**
     * This method returns the formatted string representation of the total storage used by the camera.
     * @return The formatted string representation of the total storage used by the camera.
     */
    public String getDemonstrationOfMb(){
        return demonstrationOfMb;
    }
}
