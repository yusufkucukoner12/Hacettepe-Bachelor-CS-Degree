import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class Level {
    private Scene scene;
    protected BorderPane borderPane = new BorderPane();
    static int nextLevel = 1;
    protected ArrayList<Duck> ducks = new ArrayList<>();
    private Text ammoText;
    private Text levelText;
    private IntegerProperty numberOfAmmo;
    private IntegerProperty duckSize;
    private AudioClip gameOverSound = new AudioClip(new File("assets\\effects\\GameOver.mp3").toURI().toString());
    private AudioClip levelCompletedSound = new AudioClip(new File("assets\\effects\\LevelCompleted.mp3").toURI().toString());
    private AudioClip gameCompletedSound = new AudioClip(new File("assets\\effects\\GameCompleted.mp3").toURI().toString());
    /**
     * Creates a new Level object with the specified background, crosshair, and duck size.
     * This constructor creates default architecture for levels.
     * Arranges text of  victory defeat screens and ammo, level text.
     * @param backGround The ImageView object representing the background of the level.
     * @param crossHair  The ImageView object representing the crosshair for targeting ducks.
     * @param duckSize   The number of the ducks in the level.
     */
    Level(ImageView backGround, ImageView crossHair, int duckSize){
        this.duckSize = new SimpleIntegerProperty(duckSize);
        borderPane.getChildren().add(backGround);

        scene = new Scene(borderPane,backGround.getFitWidth(),backGround.getFitHeight());
        scene.setCursor(
                new ImageCursor(crossHair.getImage(),
                        crossHair.getImage().getWidth()*DuckHunt.SCALE,
                        crossHair.getImage().getHeight()*DuckHunt.SCALE));

        numberOfAmmo = new SimpleIntegerProperty(duckSize*3);

        arrangeTexts();
        setGameOverWinScene();
        setGameOverLoseScene();
        hitTheDucks();
    }
    Level(){}
    /**
     * Arranges and initializes the texts for ammo and level information in the user interface.
     * The ammo text displays the number of remaining ammo.
     * The level text displays the current level number and the total number of levels.
     */
    private void arrangeTexts(){
        ammoText = MenuMethods.createText("Ammo Left: "+numberOfAmmo.getValue(), (int) (DuckHunt.SCALE*5/2));

        HBox ammoTextContainer = new HBox(ammoText);
        ammoTextContainer.setAlignment(Pos.TOP_RIGHT);
        ammoTextContainer.setPadding(new Insets(DuckHunt.SCALE*2/3,DuckHunt.SCALE*10/3,0,0));
        borderPane.setTop(ammoTextContainer);

        levelText = MenuMethods.createText("Level "+nextLevel+"/"+6,(int) (DuckHunt.SCALE*5/2));

        HBox levelTextContainer = new HBox(levelText);
        levelTextContainer.setAlignment(Pos.TOP_CENTER);
        levelTextContainer.setPadding(new Insets(-9*DuckHunt.SCALE,DuckHunt.SCALE*10/3,0,0));
        borderPane.setCenter(levelTextContainer);
    }
    /**
     * Sets up the level architecture and defines the key events for the level scene with different scenarios.
     * @param ducks      The list of ducks in the level.
     * @param backGround The background image of the level.
     * @param foreGround The foreground image of the level.
     * @param crossHair  The crosshair image for targeting.
     */
    public void setLevelArchitecture(ArrayList<Duck> ducks, ImageView backGround, ImageView foreGround, ImageView crossHair){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // This if clause represents 2 case, first one is game lost, second one is game completed.
                if ((numberOfAmmo.getValue() == 0 & ducks.size() != 0) | (nextLevel == 6  & ducks.size() == 0)) {
                    switch (event.getCode()) {
                        case ESCAPE:
                            gameOverSound.stop();
                            gameCompletedSound.stop();
                            EntryScene entryScene = new EntryScene();
                            entryScene.loadEntryScene();

                            break;

                        case ENTER:
                            gameOverSound.stop();
                            gameCompletedSound.stop();
                            nextLevel = 1;
                            FirstLevel firstLevel = new FirstLevel(backGround, crossHair);
                            firstLevel.loadFirstLevel(backGround, foreGround, crossHair);

                            break;
                    }
                }
                // This if clause represents level completed.
                else if(ducks.size() == 0) {
                    switch (event.getCode()) {
                        case ENTER:
                            levelCompletedSound.stop();
                            loadNextLevel(++nextLevel, backGround, foreGround, crossHair);
                            break;
                        }
                }
            }
        });
    }
    /**
     * Sets up the texts for the game over scene.
     * Creates Text objects with the specified content and size.
     * Applies flashing effect to the "enter" and "esc" texts.
     * Positions the texts on the screen based on the provided translation values.
     * @param gameOver The content of the game over text.
     * @param enter    The content of the "enter" text.
     * @param esc      The content of the "esc" text.
     * @param coordinateForGameOverText        The translation value for the game over text on the x-axis.
     * @param coordinateForEnterText        The translation value for the "enter" text on the x-axis.
     * @param coordinateForEscText        The translation value for the "esc" text on the x-axis.
     */
    protected void setTexts(String gameOver, String enter, String esc, int coordinateForGameOverText,int coordinateForEnterText, int coordinateForEscText){
        Text gameOverText = MenuMethods.createText(gameOver,15);
        Text enterText = MenuMethods.createText(enter,15);
        Text escText = MenuMethods.createText(esc,15);

        MenuMethods.flashingEffectForText(enterText);
        MenuMethods.flashingEffectForText(escText);

        gameOverText.setTranslateX(borderPane.getWidth()/3-coordinateForGameOverText);
        gameOverText.setTranslateY(borderPane.getHeight()/2.5-45);

        enterText.setTranslateX((borderPane.getWidth()/3)-coordinateForEnterText);
        enterText.setTranslateY((borderPane.getHeight()/2.5));

        escText.setTranslateX((borderPane.getWidth()/3)-coordinateForEscText);
        escText.setTranslateY((borderPane.getHeight()/2.5)+45);

        borderPane.getChildren().addAll(gameOverText,enterText,escText);
    }
    /**
     * Sets up the game over scene for the case of losing the game (running out of ammo without eliminating all ducks).
     * Monitors the changes in the number of ammo and triggers the game over scene when the ammo reaches zero.
     * Displays the appropriate text and plays the game over sound.
     */
    private void setGameOverLoseScene(){
        numberOfAmmo.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.equals(0)){
                    if(ducks.size() != 0){
                        gameOverSound.setVolume(1*DuckHunt.VOLUME);
                        gameOverSound.play();
                        setTexts("GAME OVER!","Press ENTER to play again","Press ESC to exit",0,(int) (DuckHunt.SCALE*44), (int) (DuckHunt.SCALE*14));
                    }
                }
            }
        });
    }
    /**
     * Sets up the game over win scene.
     * Adds a change listener to the duckSize property.
     * If the duckSize reaches 0 and it is the last level, plays the game completed sound and displays the appropriate texts.
     * If the duckSize reaches 0 and it is not the last level, plays the level completed sound and displays the appropriate texts.
     */
    private void setGameOverWinScene(){
        duckSize.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.equals(0) & nextLevel == 6){
                    gameCompletedSound.setVolume(1*DuckHunt.VOLUME);
                    gameCompletedSound.play();
                    setTexts("You have completed the game!","Press ENTER to play again","Press ESC to exit", (int) (DuckHunt.SCALE*60), (int) (DuckHunt.SCALE*44), (int) (DuckHunt.SCALE*14));
                    return;
                }
                if(newValue.equals(0)){
                    levelCompletedSound.setVolume(1*DuckHunt.VOLUME);
                    levelCompletedSound.play();
                    setTexts("YOU WIN","Press ENTER to play next level","",0, (int) (DuckHunt.SCALE*70), (int) (DuckHunt.SCALE*-41));
                }
            }
        });
    }
    /**
     * Helps to load next level.
     * @param nextLevel next level.
     * @param backGround The background image of the level.
     * @param foreGround The foreground image of the level.
     * @param crossHair  The crosshair image for targeting.
     */
    private void loadNextLevel(int nextLevel, ImageView backGround, ImageView foreGround , ImageView crossHair){
        if(nextLevel == 2){
            SecondLevel secondLevel = new SecondLevel(backGround,crossHair);
            secondLevel.loadSecondLevel(backGround, foreGround, crossHair);
            DuckHunt.realStage.setScene(secondLevel.getScene());
        }
        if(nextLevel ==3){
            ThirdLevel thirdLevel = new ThirdLevel(backGround,crossHair);
            thirdLevel.loadThirdLevel(backGround, foreGround, crossHair);
            DuckHunt.realStage.setScene(thirdLevel.getScene());
        }
        if(nextLevel ==4){
            ForthLevel forthLevel = new ForthLevel(backGround,crossHair);
            forthLevel.loadForthLevel(backGround, foreGround, crossHair);
            DuckHunt.realStage.setScene(forthLevel.getScene());
        }
        if(nextLevel ==5){
            FifthLevel fifthLevel = new FifthLevel(backGround,crossHair);
            fifthLevel.loadFifthLevel(backGround, foreGround, crossHair);
            DuckHunt.realStage.setScene(fifthLevel.getScene());
        }
        if(nextLevel ==6){
            SixthLevel sixthLevel = new SixthLevel(backGround,crossHair);
            sixthLevel.loadSixthLevel(backGround, foreGround, crossHair);
            DuckHunt.realStage.setScene(sixthLevel.getScene());
        }
    }
    /**
     * Handles the event when the scene is clicked.
     * Plays the shooting sound effect.
     * Checks if the duckSize or numberOfAmmo is 0 and returns if so.
     * Retrieves the mouse coordinates and checks if any ducks are hit.
     * Removes the hit ducks, decrements the duckSize, stops their animations, and triggers the deadDucks method.
     * Decrements the numberOfAmmo and updates the ammoText.
     */
    private void hitTheDucks(){
        scene.setOnMouseClicked(event -> {
            if(duckSize.getValue() == 0 | numberOfAmmo.getValue() == 0){
                return;
            }

            AudioClip shootingSound = new AudioClip(new File("assets\\effects\\Gunshot.mp3").toURI().toString());
            shootingSound.setVolume(1*DuckHunt.VOLUME);
            shootingSound.play();

            ArrayList<Duck> deadDucks = new ArrayList<>();
            double mouseX = event.getX();
            double mouseY = event.getY();

            for (Duck duck : ducks) {
                if (duck.getDuckView().getBoundsInParent().contains(mouseX, mouseY)) {
                    deadDucks.add(duck);
                }
            }
            for (Duck deadDuck : deadDucks) {
                ducks.remove(deadDuck);
                duckSize.set(duckSize.getValue()-1);

                deadDuck.getAnimationOfDuck().stop();
                deadDucks(deadDuck);
            }
            if(numberOfAmmo.getValue() > 0) {
                numberOfAmmo.set(numberOfAmmo.getValue()-1);
                ammoText.setText("Ammo Left: "+numberOfAmmo.getValue());
            }
        });
    }
    /**
     * Handles the animation and effects when a duck is shot and killed.
     * Rotates the duck if it is turned over.
     * Sets the image of the duck to the shot duck image.
     * Plays the falling sound effect.
     * Initiates a timeline for the falling animation.
     * Updates the image of the duck during the falling animation.
     * Removes the duck's view from the border pane after the falling animation is finished.
     * Stops the timeline.
     * @param duck The duck that is shot and killed.
     */
    private void deadDucks(Duck duck){
        if(duck.isTurnOver()){
            duck.getRotateX().setAngle(180);
        }

        File pathToShotDuck = new File("assets\\"+duck.getTypeOfDuck()+"\\"+ 7 +".png");
        Image shotDuck  = new Image(pathToShotDuck.toURI().toString());
        duck.getDuckView().setImage(shotDuck);

        duck.getDuckView().setFitHeight(duck.getDuckView().getImage().getHeight()*DuckHunt.SCALE);
        duck.getDuckView().setFitWidth(duck.getDuckView().getImage().getWidth()*DuckHunt.SCALE);

        AudioClip fallingSound = new AudioClip(new File("assets\\effects\\DuckFalls.mp3").toURI().toString());
        fallingSound.setVolume(1*DuckHunt.VOLUME);
        fallingSound.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(350), event1 ->{
                    {
                        File pathToFallingDuck = new File("assets\\"+duck.getTypeOfDuck()+"\\"+ 8 +".png");
                        Image fallingDuck  = new Image(pathToFallingDuck.toURI().toString());
                        duck.getDuckView().setImage(fallingDuck);

                        duck.getDuckView().setFitHeight(duck.getDuckView().getImage().getHeight()*DuckHunt.SCALE);
                        duck.getDuckView().setFitWidth(duck.getDuckView().getImage().getWidth()*DuckHunt.SCALE);
                        duck.getDuckView().setTranslateY(duck.getDuckView().getTranslateY()+40*DuckHunt.SCALE);
                    }
                }),
                new KeyFrame(Duration.millis(100))
        );

        timeline.setCycleCount(20);
        timeline.play();

        timeline.setOnFinished(event -> {
            borderPane.getChildren().remove(duck.getDuckView());
            timeline.stop();

        });
    }


    public Scene getScene(){
        return scene;
    }
}
