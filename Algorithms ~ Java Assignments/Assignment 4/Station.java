import java.io.Serializable;

public class Station implements Serializable {
    static final long serialVersionUID = 55L;

    public Point coordinates;
    public String description;

    public Station(Point coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
    }

    public String toString() {
        return this.description;
    }
}
