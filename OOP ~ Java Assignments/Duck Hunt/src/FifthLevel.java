import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Collections;

/**
 * The FifthLevel class represents the fifth level of the game.
 * It contains specific ducks and their positions for this level.
 */
public class FifthLevel extends Level{
    private Duck redDuck = new Duck(
            new ImageView(
                    new Image(
                            new File("assets\\duck_red\\"+1+".png")
                                    .toURI().toString())),
            "duck_red",6,3,4,-51.2,0);
    private Duck blueDuck = new Duck(
            new ImageView(
                    new Image(
                            new File("assets\\duck_blue\\"+1+".png")
                                    .toURI().toString())),
            "duck_blue",3,0,1,-51.2,-51.2);
    private Duck blackDuck = new Duck(
            new ImageView(
                    new Image(
                            new File("assets\\duck_black\\"+4+".png")
                                    .toURI().toString())),
            "duck_black",6,3,4,51.2,0);
    private Duck redDuck2 = new Duck(
            new ImageView(
                    new Image(
                            new File("assets\\duck_red\\"+4+".png")
                                    .toURI().toString())),
            "duck_red",6,3,4,51.2,0);
    /**
     * Constructs a FifthLevel object with the specified background and crosshair images.
     * @param backGround The ImageView representing the background image.
     * @param crossHair  The ImageView representing the crosshair image.
     */
    FifthLevel(ImageView backGround, ImageView crossHair) {
        super(backGround, crossHair, 4);
    }

    /**
     * Loads the fifth level of the game by setting up the ducks' positions and animations.
     */
    public void loadFifthLevel(ImageView backGround, ImageView foreGround, ImageView crossHair){
        Collections.addAll(ducks,redDuck,redDuck2,blackDuck,blueDuck); // Adds all ducks to arraylist.

        borderPane.getChildren().addAll(blueDuck.getDuckView(),redDuck.getDuckView(),blackDuck.getDuckView(),redDuck2.getDuckView());
        borderPane.getChildren().add(foreGround);

        blueDuck.getDuckView().setLayoutY(DuckHunt.SCALE*100);
        blueDuck.getDuckView().setLayoutX(DuckHunt.SCALE*100);

        redDuck.getDuckView().setLayoutX(DuckHunt.SCALE*34);
        redDuck.getDuckView().setLayoutY(DuckHunt.SCALE*34);

        blackDuck.getDuckView().setLayoutX(DuckHunt.SCALE*70);
        blackDuck.getDuckView().setLayoutY(DuckHunt.SCALE*70);

        redDuck2.getDuckView().setLayoutX(DuckHunt.SCALE*100);
        redDuck2.getDuckView().setLayoutY(DuckHunt.SCALE*100);

        DucksAnimation.animationOfDuck(ducks); // Sets animations of ducks.
        setLevelArchitecture(ducks,backGround,foreGround,crossHair);
    }
}
