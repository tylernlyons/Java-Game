package main.appDriver;

import main.game.FroggerGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.ImagePattern;
/**
 * Recreation of the classical arcade game Frogger. Allows the user
 * to play the classical game with WASD, or other input.
 */

public class App extends Application {

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public App() {}

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        // some labels to display information
        Label notice = new Label("Written for WASD, aka for normal people.");
        Label instructions
            = new Label("In order to ensure proper movemnet, make deliberate keystrokes.");
        Label notice2 = new Label("Moveables (Logs and cars) move in 1.5 second intervals.");
        Label notice3 = new Label("Decreasing the delay will make the game much harder.");
        // demo game provided with the starter code
        FroggerGame game = new FroggerGame(800, 720);
        // setup scene
        VBox root = new VBox( notice, instructions, notice2, notice3, game);
        Scene scene = new Scene(root);

        // setup stage
        stage.setTitle("Frogger");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();


        // play the game
        game.play();


    } // start

} // OmegaApp
