import java.io.Serializable;

public class RouteDirection implements Serializable {
    static final long serialVersionUID = 44L;

    public String startStationName;
    public String endStationName;
    public double duration;
    boolean trainRide; // true if it's a ride on train, false if it's a walk

    public RouteDirection(String startStationName, String endStationName, double duration, boolean trainRide) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.duration = duration;
        this.trainRide = trainRide;
    }
} 