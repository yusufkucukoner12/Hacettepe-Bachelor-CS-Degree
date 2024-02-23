import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Collections;

/**
 * The SecondLevel class represents the second level of the game.
 * It contains specific ducks and their positions for this level.
 */
public class SecondLevel extends Level{
    private Duck blueDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_blue\\"+4+".png").toURI().toString())),
            "duck_blue",6,3,4,16,0);
    private Duck blackDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_black\\"+1+".png").toURI().toString())),
            "duck_black",3,0,1,12.8,-12.8);
    /**
     * Constructs a SecondLevel object with the specified background and crosshair images.
     * @param backGround The ImageView representing the background image.
     * @param crossHair  The ImageView representing the crosshair image.
     */
    SecondLevel(ImageView backGround, ImageView crossHair) {
        super(backGround, crossHair,2);
    }
    /**
     * Loads the second level of the game by setting up the ducks' positions and animations.
     */
    public void loadSecondLevel(ImageView backGround, ImageView foreGround, ImageView crossHair){
        Collections.addAll(ducks,blackDuck,blueDuck);

        borderPane.getChildren().addAll(blackDuck.getDuckView(),blueDuck.getDuckView());
        borderPane.getChildren().add(foreGround);

        blueDuck.getDuckView().setLayoutY(DuckHunt.SCALE*65);
        blueDuck.getDuckView().setLayoutX(DuckHunt.SCALE*65);

        blackDuck.getDuckView().setLayoutX(DuckHunt.SCALE*34);
        blackDuck.getDuckView().setLayoutY(DuckHunt.SCALE*34);

        DucksAnimation.animationOfDuck(ducks);
        setLevelArchitecture(ducks,backGround,foreGround,crossHair);

    }
}