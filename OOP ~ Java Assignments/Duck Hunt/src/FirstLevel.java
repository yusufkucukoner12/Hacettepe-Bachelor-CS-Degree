
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * The FirstLevel class represents the first level of the game.
 * It contains specific ducks and their positions for this level.
 */
public class FirstLevel extends Level{
    /**
     * Constructs a FirstLevel object with the specified background and crosshair images.
     * @param backGroundView The ImageView representing the background image.
     * @param crossHairView  The ImageView representing the crosshair image.
     */
    FirstLevel(ImageView backGroundView, ImageView crossHairView){
        super(backGroundView,crossHairView,1);
    }

    private Duck blackDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_black\\"+4+".png").toURI().toString())),
            "duck_black",6,3,4,12.8,0);

    /**
     * Loads the first level of the game by setting up the ducks' positions and animations.
     */
    public void loadFirstLevel(ImageView backGround, ImageView foreGround, ImageView crossHair){
        ducks.add(blackDuck); // Adds duck to arraylist.

        borderPane.getChildren().add(blackDuck.getDuckView());
        borderPane.getChildren().add(foreGround);

        blackDuck.getDuckView().setLayoutY(DuckHunt.SCALE*34);
        blackDuck.getDuckView().setLayoutX(DuckHunt.SCALE*34);

        DucksAnimation.animationOfDuck(ducks); // Sets animations of ducks.
        setLevelArchitecture(ducks,backGround,foreGround,crossHair);

        DuckHunt.realStage.setScene(getScene());
    }
}
