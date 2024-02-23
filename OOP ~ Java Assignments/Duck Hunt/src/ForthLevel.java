import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Collections;
/**
 * The ForthLevel class represents the forth level of the game.
 * It contains specific ducks and their positions for this level.
 */
public class ForthLevel extends Level{
    private Duck redDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_red\\"+1+".png").toURI().toString())),
            "duck_red",3,0,1,-25.6,-25.6);
    private Duck blueDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_blue\\"+1+".png").toURI().toString())),
            "duck_blue",3,0,1,-25.6,-25.6);
    private Duck blackDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_black\\"+4+".png").toURI().toString())),
            "duck_black",6,3,4,-25.6,0);
    /**
     * Constructs a ForthLevel object with the specified background and crosshair images.
     * @param backGround The ImageView representing the background image.
     * @param crossHair  The ImageView representing the crosshair image.
     */
    ForthLevel(ImageView backGround, ImageView crossHair) {
        super(backGround, crossHair,3);
    }
    /**
     * Loads the forth level of the game by setting up the ducks' positions and animations.
     */
    public void loadForthLevel(ImageView backGround, ImageView foreGround, ImageView crossHair){
        Collections.addAll(ducks,blackDuck,redDuck,blueDuck); // Adds all ducks to arraylist.

        borderPane.getChildren().addAll(blueDuck.getDuckView(),redDuck.getDuckView(),blackDuck.getDuckView());
        borderPane.getChildren().add(foreGround);

        blueDuck.getDuckView().setLayoutY(DuckHunt.SCALE*90);
        blueDuck.getDuckView().setLayoutX(DuckHunt.SCALE*90);

        redDuck.getDuckView().setLayoutX(DuckHunt.SCALE*40);
        redDuck.getDuckView().setLayoutY(DuckHunt.SCALE*40);

        blackDuck.getDuckView().setLayoutX(DuckHunt.SCALE*60);
        blackDuck.getDuckView().setLayoutY(DuckHunt.SCALE*60);

        DucksAnimation.animationOfDuck(ducks); // Sets animations of ducks
        setLevelArchitecture(ducks,backGround,foreGround,crossHair);
    }
}
