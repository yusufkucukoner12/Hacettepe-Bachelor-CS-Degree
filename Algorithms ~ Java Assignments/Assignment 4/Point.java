import java.io.Serializable;

public class Point implements Serializable {
    static final long serialVersionUID = 22L;
    
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
