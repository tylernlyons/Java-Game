package cs1302.game;


import java.util.TimerTask;
import java.util.Timer;
import java.lang.Thread;
import java.util.Random;
import java.util.logging.Level;
import javafx.application.Platform;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.lang.System;
import java.lang.Math;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.ImagePattern;
import javafx.scene.control.TextField;



public class DemoGame extends Game {
    private int rng; //random number generator
    private ImageView player = new ImageView("file:resources/frog.jpg");
    private ImageView l = new ImageView("file:resources/log.jpg");
    private ImageView w = new ImageView("file:resources/water.jpg");
    private ImageView f = new ImageView("file:resources/frog.jpg");
    private ImageView g = new ImageView("file:resources/grass.jpg");
    private ImageView c = new ImageView("file:resources/car.jpg");
    private ImageView r = new ImageView("file:resources/road.jpg");
    private ImageView go = new ImageView("file:resources/gmov.jpg");
    private ImageView win = new ImageView("file:resources/win.jpg");
    private double ts = 73;
    private GridPane gridPane = new GridPane();
    private ImageView[][] pGrid = new ImageView[9][10];
    private int x = 4;
    private int y = 9;
    private ImageView lm = g;
    private int score = 0;
    private TextField scoreField;
    private final long period = 1000;
    private long oTime = System.currentTimeMillis() - period;
    private Timer timer = new Timer("Timer");
    private long delay = 3000L;
    private ImageView move = new ImageView();
    private ImageView surf = new ImageView();
    private int rng1;

    /**
     * Construct a {@code DemoGame} object.
     * @param width scene width
     * @param height scene height
     */

    public DemoGame(int width, int height) {
        super(width, height, 1000);            // call parent constructor
        setLogLevel(Level.INFO);             // enable logging
        go.setFitHeight((double)height);
        go.setFitWidth((double)width);
    } // DemoGame

    TimerTask task = new TimerTask() {
            public void run() {
                Platform.exit();
            } //run
        }; //TimerTask
    /**
     * Creates a delay for updating {@link GridPane}.
     */

    public void tick() {
        long cTime = System.currentTimeMillis();
        if ((cTime - oTime) >= period) {
            oTime = cTime;
            carLogUpdate();
            spawnCar();
            spawnLog();
        } //if
    } //tick

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component

        for (int i = 0; i < pGrid.length; i++) {
            for (int j = 0; j < pGrid[0].length; j++) {
                rng = (int)(Math.random() * 5) + 1; // random number generator
                Region rect = new Region();
                rect.setMinSize(ts,ts);
                rect.setPrefSize(ts,ts);
                rect.setMaxSize(ts,ts);
                pGrid[i][j] = r;
                if (j == pGrid[0].length - 1 || j == 2) {
                    pGrid[i][j] = g;
                } else if (j == 0) {
                    if (i % 2 == 0) {
                        pGrid[i][j] = win;
                    } else {
                        pGrid[i][j] = w;
                    }
                } else if (j == 1 || j == 3) {
                    if (i % 2 == 0) {
                        pGrid[i][j] = l;
                    } else {
                        pGrid[i][j] = w;
                    }
                } else if (pGrid[i][j] != w && pGrid[i][j] != g && pGrid[i][j] != l
                    && pGrid[i][j] != win && rng == 1) {
                    pGrid[i][j] = c;
                } //if
                pGrid[4][9] = f;
                rect.setBackground(new Background(new BackgroundFill(new ImagePattern
                (pGrid[i][j].getImage()), CornerRadii.EMPTY, Insets.EMPTY)));
                gridPane.add(rect, i, j);
            } //for
        } //for
        getChildren().addAll(gridPane);
    } // init

    /**
     * Generates a car {@link ImageView} randomly at the end of the {@link GridPane}
     * in one of the road rows.
     */

    protected void spawnCar() {
        rng = (int)(Math.random() * 5) + 4;
        pGrid[8][rng] = c;
        uImage(rng, 8);
    } //spawnCars;

    /**
     * Generates a log {@link ImageView} randomly at the end of the {@link GridPane}
     * in either the 2nd or 4th row.
     */

    protected void spawnLog() {
        rng = (int)(Math.random() * 3) + 1;
        if (rng == 3) {
            pGrid[8][rng] = l;
            uImage(rng,8);
        } else if (rng == 1) {
            pGrid[8][rng] = l;
            uImage(rng,8);
        } //if
    } //spawnLog

    /**
     * Creates and applies the {@link ImageView} {@link Region} to the {@link GridPane}.
     * @param x The x value of the {@link ImageView}
     * @param y The y value of the {@link ImageView}
     */

    protected void uImage(int x, int y) {
        Region img = new Region();
        img.setBackground(new Background(new BackgroundFill(new ImagePattern
            (pGrid[x][y].getImage()), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.add(img,x,y);
    } //uImage

    /**
     * Moves the frog up in the {@link GridPane}.
     */

    protected void upMove() {
        if (pGrid[x][y - 1] == c || pGrid[x][y - 1] == w) {
            getChildren().add(go);
            timer.schedule(task, delay);
        } //if
        if (y == pGrid[0].length - 1) {
            pGrid[x][y] = g;
            uImage(x,y);
            y--;
            pGrid[x][y] = f;
            uImage(x,y);
        } else if (pGrid[x][y - 1] == win) {
            pGrid[x][y - 1] = f;
            pGrid[x][y] = l;
            uImage(x,y);
            y--;
            uImage(x,y);
            x = 4;
            y = 8;
            pGrid[x][y] = f;
            uImage(x,y);
            score += 5;
            scoreField = new TextField("Score: " + score);
            getChildren().add(scoreField);
        } else if (pGrid[x][y - 1] == l && pGrid[x][y - 3] == win) {
            pGrid[x][y - 1] = f;
            pGrid[x][y] = l;
            uImage(x,y);
            y--;
            uImage(x,y);
        } else {
            pGrid[x][y] = r;
            uImage(x,y);
            y--;
            pGrid[x][y] = f;
            uImage(x,y);
        } //if
    } //upMove

     /**
     * Moves the frog down in the {@link GridPane}.
     */

    protected void downMove() {
        if (pGrid[x][y + 1] == c || pGrid[x][y + 1] == w) {
            getChildren().add(go);
            timer.schedule(task, delay);
        } //if
        pGrid[x][y + 1] = f;
        if (y == pGrid[0].length - 1) {
            pGrid[x][y] = g;
        } else {
            pGrid[x][y] = r;
        } //if
        uImage(x,y);
        y++;
        uImage(x,y);
    }

    /**
     * Moves the frog right in the {@link GridPane}.
     */

    protected void rightMove() {
        if (pGrid[x + 1][y] == c || pGrid[x + 1][y] == w) {
            getChildren().add(go);
            timer.schedule(task, delay);
        } //if

        pGrid[x + 1][y] = f;
        if (y == pGrid[0].length - 1) {
            pGrid[x][y] = g;
        } else {
            pGrid[x][y] = r;
        } //if
        uImage(x,y);
        x++;
        uImage(x,y);
    }

     /**
     * Moves the frog left in the {@link GridPane}.
     */

    protected void leftMove() {
        if (pGrid[x - 1][y] == c || pGrid[x - 1][y] == w) {
            getChildren().add(go);
            timer.schedule(task, delay);
        } //if
        pGrid[x - 1][y] = f;
        if (y == pGrid[0].length - 1) {
            pGrid[x][y] = g;
        } else {
            pGrid[x][y] = r;
        } //if
        uImage(x,y);
        x--;
        uImage(x,y);
    }

    /**
     * Updates the position of the logs and cars in the {@link GridPane}.
     */

    public void carLogUpdate() {
        for (int i = 0; i < pGrid.length; i++) {
            for (int j = 0; j < pGrid[0].length; j++) {
                if (pGrid[i][j] == c || pGrid[i][j] == l) {
                    move = pGrid[i][j];
                    if (pGrid[i][j] == c) {
                        surf = r;
                    } else {
                        surf = w;
                    } //if
                    if (i == 0) {
                        pGrid[i][j] = surf;
                        uImage(i,j);
                    } else {
                        if (pGrid[i - 1][j] == f) {
                            getChildren().add(go);
                            timer.schedule(task, delay);
                        } //if
                        pGrid[i][j] = surf;
                        pGrid[i - 1][j] = move;
                        uImage(i,j);
                        uImage(i - 1, j);
                    } //if
                    if (pGrid[i][j] == f && pGrid[i + 3][j] == win) {
                        pGrid[i][j] = w;
                        x--;
                        uImage(i,j);
                        i--;
                        pGrid[i][j] = f;
                        uImage(i,j);
                    } //if
                } //if
            } //for
        } //for
    } //carUpdate


    /** {@inheritDoc} */
    @Override
    protected void update() {
        // (x, y)         In computer graphics, coordinates along an x-axis and
        // (0, 0) -x--->  y-axis are used. When compared to the standard
        // |              Cartesian plane that most students are familiar with,
        // y              the x-axis behaves the same, but the y-axis increases
        // |              in the downward direction! Keep this in mind when
        // v              adjusting the x and y positions of child nodes.

        tick();

        // update player position
        isKeyPressed(KeyCode.S, () -> downMove());
        isKeyPressed(KeyCode.W, () -> upMove());
        isKeyPressed(KeyCode.A, () -> leftMove());
        isKeyPressed(KeyCode.D, () -> rightMove());

    } // update
} // DemoGame
