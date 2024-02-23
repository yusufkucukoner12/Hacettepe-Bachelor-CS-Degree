
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class Duck {
    private String typeOfDuck; // Type of the duck
    private ImageView duckView; // ImageView representing the duck
    private int maxIndex; // Maximum index value
    private int minIndex; // Minimum index value
    private int duckIndex; // Current index value
    private double duckVelocityX; // Velocity of the duck in the X direction
    private double duckVelocityY; // Velocity of the duck in the Y direction
    private Timeline animationOfDuck; // Animation timeline for the duck
    private Rotate rotateX; // Rotate transform for X-axis rotation
    private Rotate rotateY; // Rotate transform for Y-axis rotation
    private boolean turnOver; // Flag indicating if the duck has turned over
    private boolean reflectByX; // Flag indicating if the duck should be reflected horizontally
    private boolean reflectByY; // Flag indicating if the duck should be reflected vertically

    /**
     * Constructor for the Duck class.
     * Set reflectByX flag based on duckVelocityX(direction of velocity.)
     * Set reflectByY flag based on duckVelocityY(direction of velocity.)
     * @param duckView       The ImageView representing the duck.
     * @param typeOfDuck     The type of the duck.
     * @param maxIndex       The maximum index value.
     * @param minIndex       The minimum index value.
     * @param duckIndex      The current index value.
     * @param duckVelocityX  The velocity of the duck in the X direction.
     * @param duckVelocityY  The velocity of the duck in the Y direction.
     */
    Duck(ImageView duckView, String typeOfDuck, int maxIndex, int minIndex, int duckIndex, double duckVelocityX, double duckVelocityY) {
        this.duckView = duckView;
        this.typeOfDuck = typeOfDuck;
        this.maxIndex = maxIndex;
        this.minIndex = minIndex;
        this.duckIndex = duckIndex;
        this.duckVelocityX = duckVelocityX * DuckHunt.SCALE / 3;
        this.duckVelocityY = duckVelocityY * DuckHunt.SCALE / 3;

        if (duckVelocityX < 0) {
            reflectByX = true;
        }

        if (duckVelocityY > 0) {
            reflectByY = true;
        }
    }
    public String getTypeOfDuck() {
        return typeOfDuck;
    }
    public ImageView getDuckView() {
        return duckView;
    }
    public int getMaxIndex() {
        return maxIndex;
    }
    public int getMinIndex() {
        return minIndex;
    }
    public int getDuckIndex() {
        return duckIndex;
    }
    public double getDuckVelocityX() {
        return duckVelocityX;
    }
    public double getDuckVelocityY() {
        return duckVelocityY;
    }
    public Timeline getAnimationOfDuck() {
        return animationOfDuck;
    }
    public void setAnimationOfDuck(Timeline animationOfDuck) {
        this.animationOfDuck = animationOfDuck;
    }
    public void setDuckIndex(int duckIndex) {
        this.duckIndex = duckIndex;
    }
    public void setDuckVelocityX(double duckVelocityX) {
        this.duckVelocityX = duckVelocityX;
    }
    public void setDuckVelocityY(double duckVelocityY) {
        this.duckVelocityY = duckVelocityY;
    }
    public boolean isReflectByX() {return reflectByX;}
    public boolean isReflectByY() {return reflectByY;}
    public void setReflectByX(boolean reflectByX) {this.reflectByX = reflectByX;}
    public void setReflectByY(boolean reflectByY) {this.reflectByY = reflectByY;}
    public Rotate getRotateX() {return rotateX;}
    public Rotate getRotateY() {return rotateY;}
    public void setRotateX(Rotate rotateX) {this.rotateX = rotateX;}
    public void setRotateY(Rotate rotateY) {this.rotateY = rotateY;}
    public boolean isTurnOver() {return turnOver;}
    public void setTurnOver(boolean turnOver) {this.turnOver = turnOver;}
}

