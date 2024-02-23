import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class DuckHunt extends Application {
    public static final double SCALE = 2; // Constant for scaling
    public static Stage realStage = new Stage(); // The main stage for the application
    public static final double VOLUME = 0.1; // Constant for volume level

    /**
     * The start method of the JavaFX application.
     * Loads the entry scene and starts the game.
     * @param stage The primary stage for the JavaFX application.
     * @throws IOException If an I/O error occurs while loading the entry scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        EntryScene entryScene = new EntryScene();
        entryScene.loadEntryScene();
        realStage.setTitle("HUBBM Duck Hunt");
        realStage.getIcons().add(new Image(String.valueOf((new File("assets\\favicon\\1.png").toURI()))));
        realStage.show();


    }

    public static void main(String[] args) {
        launch(DuckHunt.class,args);
    }
}