/**
 * A Smart Lamp class representing a smart lamp device.
 * It extends the SmartDevices class and adds additional properties such as brightness and kelvin.
 */
public class Smart_Lamp extends SmartDevices {

    private int brightness = 100;
    private int kelvin = 4000;

    /**
     * Constructs a Smart_Lamp object with a type, name, state, kelvin, and brightness.
     * @param type the type of the smart device which is smart lamp.
     * @param name the name of the smart lamp
     * @param state the state of the smart lamp
     * @param kelvin the kelvin of the smart lamp(default is 4000)
     * @param brightness the brightness percentage of the lamp (default is 100)
     */
    Smart_Lamp(String type, String name, String state, int kelvin, int brightness) {
        super(type, name, state);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }
    /**
     * Constructs a Smart_Lamp object with a type, name, and state.
     * @param type the type of the smart device which is smart lamp.
     * @param name the name of the smart lamp
     * @param state the state of the smart lamp
     */
    Smart_Lamp(String type, String name, String state) {
        super(type, name, state);
    }
    /**
     * Constructs a Smart_Lamp object with a type and name.
     * @param type the type of the smart lamp
     * @param name the name of the smart lamp
     */
    Smart_Lamp(String type, String name) {
        super(type, name);
    }
    /**

     Default constructor for Smart_Lamp objects.
     */
    Smart_Lamp() {}
    /**
     * Sets the brightness percentage of the smart lamp.
     * @param brightness the brightness percentage of the lamp (default is 100)
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
    /**
     * Sets the kelvin of the smart lamp.
     * @param kelvin the kelvin of the smart lamp(default is 4000)
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }
    /**
     * Gets the brightness percentage of the smart lamp.
     * @return the brightness percentage of the lamp (default is 100)
     */
    public int getBrightness() {
        return brightness;
    }
    /**
     * Gets the kelvin of the smart lamp.
     * @return the kelvin of the smart lamp(default is 4000)
     */
    public int getKelvin() {
        return kelvin;
    }
}
