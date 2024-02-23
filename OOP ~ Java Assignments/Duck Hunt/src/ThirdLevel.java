import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Collections;
/**
 * The ThirdLevel class represents the third level of the game.
 * It contains specific ducks and their positions for this level.
 */
public class ThirdLevel extends Level{
    private Duck blueDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_blue\\"+4+".png").toURI().toString())),
            "duck_blue",6,3,4,-32,0);
    private Duck blackDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_black\\"+1+".png").toURI().toString())),
            "duck_black",3,0,1,-20,-20);
    private Duck redDuck = new Duck(
            new ImageView(
                    new Image(new File("assets\\duck_red\\"+1+".png").toURI().toString())),
            "duck_red",3,0,1,20,-20);
    /**
     * Constructs a ThirdLevel object with the specified background and crosshair images.
     * @param backGround The ImageView representing the background image.
     * @param crossHair  The ImageView representing the crosshair image.
     */
    ThirdLevel(ImageView backGround, ImageView crossHair) {
        super(backGround, crossHair,3);

    }
    /**
     * Loads the third level of the game by setting up the ducks' positions and animations.
     */
    public void loadThirdLevel(ImageView backGround, ImageView foreGround, ImageView crossHair){
        Collections.addAll(ducks,blackDuck,redDuck,blueDuck); // Adds all ducks to arraylist.

        borderPane.getChildren().addAll(blackDuck.getDuckView(),blueDuck.getDuckView(),redDuck.getDuckView());
        borderPane.getChildren().add(foreGround);

        blueDuck.getDuckView().setLayoutY(DuckHunt.SCALE*70);
        blueDuck.getDuckView().setLayoutX(DuckHunt.SCALE*70);

        blackDuck.getDuckView().setLayoutX(DuckHunt.SCALE*40);
        blackDuck.getDuckView().setLayoutY(DuckHunt.SCALE*40);

        redDuck.getDuckView().setLayoutX(DuckHunt.SCALE*60);
        redDuck.getDuckView().setLayoutY(DuckHunt.SCALE*60);

        DucksAnimation.animationOfDuck(ducks); // Sets animations of ducks.
        setLevelArchitecture(ducks,backGround,foreGround,crossHair);
    }
}