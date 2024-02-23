import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class DucksAnimation extends Level {
    /**
     * Initiates the animation for the ducks by creating a timeline for each duck in the given list.
     * The timeline repeatedly invokes the 'animateWithImages' method to animate the duck using images.
     * The timeline is set to cycle indefinitely.
     * The animation timeline is started and assigned to the duck's 'animationOfDuck' property.
     * @param ducks The list of ducks to animate.
     */
    public static void animationOfDuck(ArrayList<Duck> ducks){
        for(Duck duck : ducks){
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> DucksAnimation.animateWithImages(duck)),
                    new KeyFrame(Duration.millis(100))

            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            duck.setAnimationOfDuck(timeline);
        }
    }
    /**
     * Animates the given duck by updating its image, position, and rotation.
     * The method increments the duck's image index to display the next frame of the animation.
     * It loads the corresponding image file based on the duck's image index and type.
     * The loaded image is set as the new image for the duck's view, and the view's dimensions are adjusted.
     * The method updates the duck's position by applying its velocity.
     * It also handles the reflection and rotation of the duck based on its movement and boundaries.
     * @param duck The duck to animate.
     */
    public static void animateWithImages(Duck duck ){
        // Checks if the duck has reached its maximum image index
        if(duck.getDuckIndex() == duck.getMaxIndex()){
            // Resets the image index to the minimum value to restart the animation
            duck.setDuckIndex(duck.getMinIndex());
        }

        duck.setDuckIndex(duck.getDuckIndex()+1);
        File file = new File("assets\\"+duck.getTypeOfDuck()+"\\"+ duck.getDuckIndex() +".png");
        Image duck1  = new Image(file.toURI().toString());

        duck.getDuckView().setImage(duck1);
        duck.getDuckView().setFitWidth(duck.getDuckView().getImage().getWidth()*DuckHunt.SCALE);
        duck.getDuckView().setFitHeight(duck.getDuckView().getImage().getHeight()*DuckHunt.SCALE);

        duck.getDuckView().setLayoutX(duck.getDuckView().getLayoutX()+duck.getDuckVelocityX());
        duck.getDuckView().setLayoutY(duck.getDuckView().getLayoutY()+duck.getDuckVelocityY());

        double duckX = duck.getDuckView().getLayoutX();
        double duckY = duck.getDuckView().getLayoutY();

        duck.setRotateY(new Rotate(0,duck.getDuckView().getFitWidth()/2, duck.getDuckView().getFitHeight()/2,0,Rotate.Y_AXIS));
        duck.getDuckView().getTransforms().add(duck.getRotateY());

        duck.setRotateX(new Rotate(0,duck.getDuckView().getFitWidth()/2, duck.getDuckView().getFitHeight()/2,0,Rotate.X_AXIS));
        duck.getDuckView().getTransforms().add(duck.getRotateX());

        // Checks if the duck needs to be reflected horizontally
        if(duck.isReflectByX()){
            duck.getRotateY().setAngle(180);
            duck.setReflectByX(false);

        }
        // Checks if the duck needs to be reflected vertically
        if(duck.isReflectByY()){
            duck.getRotateX().setAngle(180);
            duck.setReflectByY(false);

        }
        // Checks if the duck has reached the boundaries of the game window
        if(duckX<0 || duckX > 256*DuckHunt.SCALE-duck.getDuckView().getFitWidth()){
            // Reverses the duck's horizontal velocity and flips it vertically
            duck.setDuckVelocityX(-duck.getDuckVelocityX());
            duck.getRotateY().setAngle(180);

        }
        if(duckY<0 || duckY > 240*DuckHunt.SCALE-duck.getDuckView().getFitHeight()){
            // Reverses the duck's vertical velocity and flips it horizontally
            duck.setDuckVelocityY(-duck.getDuckVelocityY());
            duck.getRotateX().setAngle(180);
            duck.setTurnOver(!duck.isTurnOver());

        }
    }
}