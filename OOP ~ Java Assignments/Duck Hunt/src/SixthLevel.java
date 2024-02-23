import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Collections;
/**
 * The SixthLevel class represents the sixth level of the game.
 * It contains specific ducks and their positions for this level.
 */
public class SixthLevel extends Level{
    private Duck blackDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_black\\"+4+".png").toURI().toString())),
            "duck_black",6,3,4,51.2,0);
    private Duck blackDuck2 = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_black\\"+1+".png").toURI().toString())),
            "duck_black",3,0,1,-51.2,-51.2);
    private Duck blueDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_blue\\"+4+".png").toURI().toString())),
            "duck_blue",6,3,4,-51.2,0);
    private Duck blueDuck2 = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_blue\\"+1+".png").toURI().toString())),
            "duck_blue",3,0,1,+51.2,-51.2);
    private Duck redDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_red\\"+1+".png").toURI().toString())),
            "duck_red",3,0,1,51.2,-51.2);
    private Duck redDuck2 = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_red\\"+4+".png").toURI().toString())),
            "duck_red",6,3,4,51.2,0);
    /**
     * Constructs a SixthLevel object with the specified background and crosshair images.
     * @param backGround The ImageView representing the background image.
     * @param crossHair  The ImageView representing the crosshair image.
     */
    SixthLevel(ImageView backGround, ImageView crossHair) {
        super(backGround, crossHair, 6);
    }
    /**
     * Loads the sixth level of the game by setting up the ducks' positions and animations.
     */
    public void loadSixthLevel(ImageView backGround, ImageView foreGround, ImageView crossHair){
        Collections.addAll(ducks,blackDuck,blackDuck2,redDuck,redDuck2,blueDuck,blueDuck2); // Adds all ducks to arraylist.

        borderPane.getChildren().addAll(blackDuck.getDuckView(),
                blackDuck2.getDuckView(),redDuck.getDuckView(),redDuck2.getDuckView(),blueDuck.getDuckView(),blueDuck2.getDuckView());

        borderPane.getChildren().add(foreGround);

        blackDuck.getDuckView().setLayoutY(DuckHunt.SCALE*34);
        blackDuck.getDuckView().setLayoutX(DuckHunt.SCALE*34);

        redDuck2.getDuckView().setLayoutY(DuckHunt.SCALE*70);
        redDuck2.getDuckView().setLayoutX(DuckHunt.SCALE*70);

        blueDuck.getDuckView().setLayoutY(DuckHunt.SCALE*100);
        blueDuck.getDuckView().setLayoutX(DuckHunt.SCALE*100);

        blackDuck2.getDuckView().setLayoutX(DuckHunt.SCALE*100);
        blackDuck2.getDuckView().setLayoutY(DuckHunt.SCALE*50);

        redDuck.getDuckView().setLayoutX(DuckHunt.SCALE*34);
        redDuck.getDuckView().setLayoutY(DuckHunt.SCALE*34);

        blueDuck2.getDuckView().setLayoutX(DuckHunt.SCALE*60);
        blueDuck2.getDuckView().setLayoutY(DuckHunt.SCALE*60);

        DucksAnimation.animationOfDuck(ducks); // Sets animations of ducks.
        setLevelArchitecture(ducks,backGround,foreGround,crossHair);
    }
}
