import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.File;

public class EntryScene {
    private Scene entryScene;
    File file = new File("assets\\welcome\\1.png");
    File titleSoundEffect = new File("assets\\effects\\Title.mp3");
    private ImageView entryView = new ImageView(new Image(String.valueOf(file.toURI())));
    AudioClip audioClip = new AudioClip(titleSoundEffect.toURI().toString());
    /**
     * Loads the entry scene of the game.
     * It sets up the visual elements, audio clip, and event handlers for key presses.
     */
    public void loadEntryScene(){
        audioClip.setVolume(1*DuckHunt.VOLUME);
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.play();

        // Set the dimensions of the entry view based on the scale factor
        entryView.setFitHeight(entryView.getImage().getHeight()*DuckHunt.SCALE);
        entryView.setFitWidth(entryView.getImage().getWidth()*DuckHunt.SCALE);

        // Create a BorderPane to hold the entry view and text elements
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(entryView);

        // Create the "PRESS ESC TO EXIT" text
        Text quitText = new Text("PRESS ESC TO EXIT");
        quitText.setFill(Color.ORANGE);
        quitText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 14*DuckHunt.SCALE));
        quitText.setTranslateX(60*DuckHunt.SCALE);
        quitText.setTranslateY(180*DuckHunt.SCALE);

        // Create the "PRESS ENTER TO START" text
        Text playText = new Text("PRESS ENTER TO START");
        playText.setStrokeWidth(2);
        playText.setFill(Color.ORANGE);
        playText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 14*DuckHunt.SCALE));
        playText.setTranslateX(45*DuckHunt.SCALE);
        playText.setTranslateY(165*DuckHunt.SCALE);

        // Apply flashing effect to the text elements
        MenuMethods.flashingEffectForText(quitText);
        MenuMethods.flashingEffectForText(playText);

        borderPane.getChildren().addAll(playText,quitText);

        // Create the entry scene with the BorderPane and set its dimensions
        entryScene = new Scene(borderPane,entryView.getFitWidth(),entryView.getFitHeight());

        DuckHunt.realStage.setScene(entryScene);

        entryScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                MainMenu mainMenu = new MainMenu();
                switch (event.getCode()){
                    case ENTER:
                        // Load the main menu when Enter key is pressed
                        mainMenu.loadMainMenu();

                }
                switch (event.getCode()){
                    case ESCAPE:
                        // Exit the game when Escape key is pressed
                        System.exit(1);
                }
            }
        });
    }
}