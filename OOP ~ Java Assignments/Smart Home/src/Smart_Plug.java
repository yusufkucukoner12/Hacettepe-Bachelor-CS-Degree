/**
 * The Smart_Plug class represents a smart plug device. It extends the SmartDevices class and adds attributes
 * for the plug state, current, total watt energy, and display of the total watt energy.
 */
public class Smart_Plug extends SmartDevices {

    private boolean plugState;
    private double ampere;
    private double totalWattEnergy;
    private String displayOfTotalWattEnergy = "0,00";

    /**
     * Constructs a Smart_Plug object with the specified type, name, state, and current.
     * @param type the type of the smart plug
     * @param name the name of the smart plug
     * @param state the state of the smart plug
     * @param ampere the current of the smart plug
     */
    public Smart_Plug(String type, String name, String state, double ampere) {
        super(type, name, state);
        this.ampere = ampere;
        setPlugState(true);
    }

    /**
     * Constructs a Smart_Plug object with the specified type, name, and state.
     * @param type the type of the smart plug
     * @param name the name of the smart plug
     * @param state the state of the smart plug
     */
    public Smart_Plug(String type, String name, String state) {
        super(type, name, state);
    }

    /**
     * Constructs a Smart_Plug object with the specified type and name.
     * @param type the type of the smart plug
     * @param name the name of the smart plug
     */
    public Smart_Plug(String type, String name) {
        super(type, name);
    }

    /**
     * Constructs a Smart_Plug object with default values.
     */
    public Smart_Plug() {
    }

    /**
     * Returns the plug state of the smart plug.
     * @return the plug state of the smart plug
     */
    public boolean isPlugState() {
        return plugState;
    }

    /**
     * Sets the plug state of the smart plug.
     * @param plugState the plug state of the smart plug
     */
    public void setPlugState(boolean plugState) {
        this.plugState = plugState;
    }

    /**
     * Returns the current of the smart plug.
     * @return the current of the smart plug
     */
    public double getAmpere() {
        return ampere;
    }

    /**
     * Sets the current of the smart plug.
     * @param ampere the current of the smart plug
     */
    public void setAmpere(double ampere) {
        this.ampere = ampere;
    }

    /**
     * Returns the total watt energy of the smart plug.
     * @return the total watt energy of the smart plug
     */
    public double getTotalWattEnergy() {
        return totalWattEnergy;
    }

    /**
     * Sets the total watt energy of the smart plug.
     * @param totalWattEnergy the total watt energy of the smart plug
     */
    public void setTotalWattEnergy(double totalWattEnergy) {
        this.totalWattEnergy = totalWattEnergy;
    }

    /**
     * Sets the display of the total watt energy of the smart plug.
     * @param displayOfTotalWattEnergy the display of the total watt energy of the smart plug.
     */
    public void setDisplayOfTotalWattEnergy(String displayOfTotalWattEnergy) {this.displayOfTotalWattEnergy = displayOfTotalWattEnergy;}

/**
 * Returns the display of the total watt energy of the smart plug.
 * @return the display of the total watt energy of the smart plug.
 */
    public String getDisplayOfTotalWattEnergy(){
        return displayOfTotalWattEnergy;
    }}
