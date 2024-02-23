import java.awt.*;

/**
 * This class represents a Smart Color Lamp. It extends the Smart_Lamp class.
 * It contains a colorCodeO attribute that represents the color code of the lamp, and additional constructors and methods to handle color properties.
 */
public class Smart_Color_Lamp extends Smart_Lamp {
    private String colorCodeO;

    /**
     * Constructor that initializes the Smart_Color_Lamp object with type and name attributes.
     * @param type the type of the Smart_Color_Lamp object.
     * @param name the name of the Smart_Color_Lamp object.
     */
    Smart_Color_Lamp(String type, String name) {
        super(type, name);
    }

    /**
     * Constructor that initializes the Smart_Color_Lamp object with type, name, and state attributes.
     * @param type the type of the Smart_Color_Lamp object.
     * @param name the name of the Smart_Color_Lamp object.
     * @param state the state of the Smart_Color_Lamp object.
     */
    Smart_Color_Lamp(String type, String name, String state) {
        super(type, name, state);
    }

    /**
     * Constructor that initializes the Smart_Color_Lamp object with type, name, state, kelvin, and brightness attributes.
     * @param type a string representing the type of the Smart_Color_Lamp object.
     * @param name the name of the smart color lamp.
     * @param state the state of the smart color lamp.
     * @param kelvin the kelvin of the smart color lamp.
     * @param brightness the brightness percentage of the smart color lamp.
     */
    Smart_Color_Lamp(String type, String name, String state, int kelvin, int brightness) {
        super(type, name, state, kelvin, brightness);
    }

    /**
     * Constructor that initializes the Smart_Color_Lamp object with type, name, state, colorCode, and brightness attributes.
     * @param type the type of the smart device which is smart color lamp.
     * @param name the name of the Smart_Color_Lamp object.
     * @param state the state of the Smart_Color_Lamp object.
     * @param colorCode the color code of the Smart_Color_Lamp object.
     * @param brightness the brightness value of the Smart_Color_Lamp object.
     */
    Smart_Color_Lamp(String type, String name, String state, String colorCode, int brightness) {
        super(type, name, state);
        setBrightness(brightness);
        this.colorCodeO = colorCode;
        setKelvin(0);
    }

    /**
     * Default constructor that initializes a Smart_Color_Lamp object with no attributes.
     */
    Smart_Color_Lamp() {}

    /**
     * Sets the color code of the Smart_Color_Lamp object.
     * @param colorCodeO the color code of the Smart_Color_Lamp object.
     */
    public void setColorCodeO(String colorCodeO) {
        this.colorCodeO = colorCodeO;
    }

    /**
     * Returns the color code of the Smart_Color_Lamp object.
     * @return the color code of the Smart_Color_Lamp object.
     */
    public String getColorCodeO() {
        return colorCodeO;
    }
}