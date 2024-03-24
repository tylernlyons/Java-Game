package cs1302.omega;

import cs1302.game.DemoGame;

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

public class OmegaApp extends Application {

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {}

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        // some labels to display information
        Label notice = new Label("Written for WASD, aka for normal people.");
        Label instructions
            = new Label("In order to ensure proper movement, make deliberate keystrokes.");

        // demo game provided with the starter code
        DemoGame game = new DemoGame(900, 720);
        // setup scene
        VBox root = new VBox( notice, instructions, game);
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
