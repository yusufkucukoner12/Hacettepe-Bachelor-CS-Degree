import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.File;


public class MainMenu extends EntryScene {
    private Scene scene;
    private int backGroundIndex = 0;
    private int crossHairIndex = 0;
    private Image backGroundImage = new Image(new File("assets\\background\\"+ ++backGroundIndex+".png").toURI().toString());
    private Image foreGroundImage = new Image(new File("assets\\foreground\\"+ backGroundIndex+".png").toURI().toString());
    private Image crossHair = new Image(new File("assets\\crosshair\\"+ ++crossHairIndex + ".png").toURI().toString());
    /**
     * Loads the main menu of the game.
     * The method creates a border pane and sets it as the root of the scene.
     * It initializes and configures the background, foreground, and crosshair image views.
     * The dimensions of the views are adjusted based on the scaling factor.
     * The method sets up a media player to play the background music.
     * It registers an event handler to handle key presses for interaction.
     * The method updates the background and foreground images when the left or right arrow keys are pressed.
     * It also updates the crosshair image when the up or down arrow keys are pressed.
     * When the enter key is pressed, the background music is played and the first level is loaded.
     * When the escape key is pressed, the background music is stopped and the entry scene is loaded.
     * The method adds the image views and text nodes to the border pane and sets the scene on the primary stage.
     */
    public void loadMainMenu() {
        BorderPane borderPane = new BorderPane();

        scene = new Scene(borderPane,backGroundImage.getWidth()*DuckHunt.SCALE,backGroundImage.getHeight()*DuckHunt.SCALE);

        ImageView backGroundView = new ImageView(backGroundImage);
        ImageView foreGroundView = new ImageView(foreGroundImage);
        ImageView crossHairView = new ImageView(crossHair);

        crossHairView.setFitHeight(crossHairView.getImage().getHeight()*DuckHunt.SCALE);
        crossHairView.setFitWidth(crossHairView.getImage().getWidth()*DuckHunt.SCALE);

        backGroundView.setFitHeight(backGroundView.getImage().getHeight()*DuckHunt.SCALE);
        backGroundView.setFitWidth(backGroundView.getImage().getWidth()*DuckHunt.SCALE);

        foreGroundView.setFitHeight(foreGroundView.getImage().getHeight()*DuckHunt.SCALE);
        foreGroundView.setFitWidth(foreGroundView.getImage().getWidth()*DuckHunt.SCALE);

        String asd = "assets/effects/Intro.mp3";

        Media media = new Media(new File(asd).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT: {
                        if(backGroundIndex == 1){
                            backGroundIndex = 7;
                        }
                        backGroundImage = new Image(new File("assets\\background\\"+ --backGroundIndex +".png").toURI().toString());
                        foreGroundImage = new Image(new File("assets\\foreground\\"+ backGroundIndex+".png").toURI().toString());
                        backGroundView.setImage(backGroundImage);
                        foreGroundView.setImage(foreGroundImage);

                        break;
                    }
                    case RIGHT:{
                        if(backGroundIndex == 6){
                            backGroundIndex = 0;
                        }
                        Image backGroundImage = new Image(new File("assets\\background\\"+ ++backGroundIndex +".png").toURI().toString());
                        Image foreGroundImage = new Image(new File("assets\\foreground\\"+ backGroundIndex+".png").toURI().toString());
                        backGroundView.setImage(backGroundImage);
                        foreGroundView.setImage(foreGroundImage);

                        break;
                    }
                    case UP: {
                        if(crossHairIndex == 7){
                            crossHairIndex = 0;
                        }
                        Image crossHair = new Image(new File("assets\\crosshair\\"+ ++crossHairIndex + ".png").toURI().toString());
                        crossHairView.setImage(crossHair);

                        break;
                    }
                    case DOWN: {
                        if(crossHairIndex== 1){
                            crossHairIndex = 8;
                        }
                        Image crossHair = new Image(new File("assets\\crosshair\\"+ --crossHairIndex + ".png").toURI().toString());
                        crossHairView.setImage(crossHair);

                        break;

                    }
                    case ENTER:{
                        audioClip.stop();
                        mediaPlayer.setVolume(1*DuckHunt.VOLUME);
                        mediaPlayer.play();

                        mediaPlayer.setOnEndOfMedia(() ->{
                                FirstLevel firstLevel = new FirstLevel(backGroundView,crossHairView);
                                firstLevel.loadFirstLevel(backGroundView,foreGroundView,crossHairView);
                            }
                        );

                        break;
                    }
                    case ESCAPE:{
                        audioClip.stop();
                        EntryScene entryScene = new EntryScene();
                        entryScene.loadEntryScene();

                        break;
                    }
                }
            }
        });

        borderPane.getChildren().add(backGroundView);
        borderPane.getChildren().add(foreGroundView);
        borderPane.setCenter(crossHairView);

        Text navigateText = new Text("USE ARROW KEYS TO NAVIGATE");
        Text enterText = new Text("PRESS ENTER TO START");
        Text escText = new Text("PRESS ESC TO EXIT");

        navigateText.setFill(Color.ORANGE);
        navigateText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 8*DuckHunt.SCALE));

        enterText.setFill(Color.ORANGE);
        enterText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 8*DuckHunt.SCALE));

        escText.setFill(Color.ORANGE);
        escText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 8*DuckHunt.SCALE));

        navigateText.setTranslateX(60*DuckHunt.SCALE);
        navigateText.setTranslateY(18*DuckHunt.SCALE);

        enterText.setTranslateX(75*DuckHunt.SCALE);
        enterText.setTranslateY(26*DuckHunt.SCALE);

        escText.setTranslateX(85*DuckHunt.SCALE);
        escText.setTranslateY(34*DuckHunt.SCALE);

        borderPane.getChildren().addAll(navigateText,escText,enterText);

        scene.setRoot(borderPane);
        DuckHunt.realStage.setScene(scene);
    }
    public Scene getScene(){
        return scene;
    }
}