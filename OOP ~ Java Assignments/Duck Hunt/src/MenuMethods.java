import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class MenuMethods {
    /**
     * Creates a flashing effect for the specified Text.
     * @param text The Text object to apply the flashing effect.
     */
    public static void flashingEffectForText(Text text){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> text.setVisible(true)),
                new KeyFrame(Duration.seconds(2), event -> text.setVisible(false))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    /**
     * Creates a Text object with the specified string and text size.
     * @param stringForText The string value to display in the Text object.
     * @param textSize      The size of the text.
     * @return The created Text object with the specified string and text size.
     */
    public static Text createText(String stringForText, int textSize){
        Text ammoText = new Text(stringForText);
        ammoText.setFill(Color.ORANGE);
        ammoText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, textSize*DuckHunt.SCALE));

        return ammoText;
    }
}