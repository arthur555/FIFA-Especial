package example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;


/**
 * A basic example JavaFX program for the first lab.
 * 
 * @author Robert C. Duvall
 */
public class ExampleBounce extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;    
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 30;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 5;
    public static final Paint GROWER_COLOR = Color.BISQUE;
    public static final double GROWER_RATE = 1.1;
    public static final int GROWER_SIZE = 50;
    

    // some things we need to remember during our game
    private Scene myScene;

    private int numOfBalls = new Random().nextInt(8)+1;
    protected ImageView[] myBouncer;

    private Rectangle myMover;
    private Rectangle myGrower;
    //private int myDirection = 1;
    protected int[] speeds;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        myBouncer = new ImageView[numOfBalls];
        speeds = new int[numOfBalls];
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        for (int i= 0; i<numOfBalls; i++)
        {
            speeds[i] = new Random().nextInt(5)+1;
        }
        // make some shapes and set their properties
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        for (int i = 0; i<numOfBalls; i++){
            myBouncer[i] = new ImageView(image);
            // x and y represent the top left corner, so center it
            int xOrigin = new Random().nextInt(width)+1;
            int yOrigin = new Random().nextInt(height)+1;
            myBouncer[i].setX(xOrigin);
            myBouncer[i].setY(yOrigin);
            root.getChildren().add(myBouncer[i]);
        }
        myMover = new Rectangle(width / 2 - 25, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        myMover.setFill(MOVER_COLOR);
        myGrower = new Rectangle(width / 2 - 25, height / 2 + 50, GROWER_SIZE, GROWER_SIZE);
        myGrower.setFill(GROWER_COLOR);
        // order added to the group is the order in which they are drawn

        root.getChildren().add(myMover);
        root.getChildren().add(myGrower);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes to animate them 
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        for(int i = 0; i <numOfBalls; i++) {
            myBouncer[i].setX(myBouncer[i].getX() + BOUNCER_SPEED * speeds[i] * elapsedTime);
            if (myBouncer[i].getX()>= myScene.getWidth() || myBouncer[i].getX()<0){
                speeds[i] *= -1;
            }
        }
        myMover.setRotate(myMover.getRotate() - 1);
        myGrower.setRotate(myGrower.getRotate() + 1);

        // check for collisions
        // with shapes, can check precisely
        var intersect = Shape.intersect(myMover, myGrower);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            myMover.setFill(HIGHLIGHT);
        }
        else {
            myMover.setFill(MOVER_COLOR);
        }
        // with images can only check bounding box
        if (myGrower.getBoundsInParent().intersects(myBouncer[0].getBoundsInParent())) {
            myGrower.setFill(HIGHLIGHT);
        }
        else {
            myGrower.setFill(GROWER_COLOR);
        }

    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myMover.setX(myMover.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myMover.setX(myMover.getX() - MOVER_SPEED);
        }
        else if (code == KeyCode.UP) {
            myMover.setY(myMover.getY() - MOVER_SPEED);
        }
        else if (code == KeyCode.DOWN) {
            myMover.setY(myMover.getY() + MOVER_SPEED);
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
        }
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
