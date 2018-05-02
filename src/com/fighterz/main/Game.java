package com.fighterz.main;

import java.util.HashSet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    // 16 : 9 Ratio based off height
    private static final int INIT_HEIGHT = 720;
    private static final int INIT_WIDTH = INIT_HEIGHT * 16 / 9;
    
    // Framerate information
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    // private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    // Used to determine values based off ratio of current res (HEIGHT) to standard
    // res (1080p)
    private static double hRatio = INIT_HEIGHT / 1080.0;

    // Mutable height and with variables
    private static int height = INIT_HEIGHT;
    private static int width = INIT_WIDTH;

    private static Stage pStage;

    private static int previousScene;
    
    private static HashSet<KeyCode> pressedKeys;
    
    private static Fighter fighterFalessi;
    private static Fighter fighterMammen;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
        
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> update());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
    private void update() {
        resolveKeyPresses();
    }
    
    private void resolveKeyPresses() {
        if(fighterFalessi != null) {
            handleFighterFalessi();
            handleFighterMammen();
        }
    }
    
    private void handleFighterFalessi() {
        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            fighterFalessi.moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            fighterFalessi.moveLeft();
        } else {
            // Some more shit goes here
        }
    }
    
    private void handleFighterMammen() {
        if (pressedKeys.contains(KeyCode.RIGHT) && !pressedKeys.contains(KeyCode.LEFT)) {
            fighterMammen.moveRight();
        } else if (pressedKeys.contains(KeyCode.LEFT) && !pressedKeys.contains(KeyCode.RIGHT)) {
            fighterMammen.moveLeft();
        } else {
            // Some more shit goes here
        }
    }

    private static void initUI(Stage stage) {
        StackPane mainMenuLayout = new MainMenu();
        previousScene = 0;

        Scene root = new Scene(mainMenuLayout);
        pStage = stage;

        stage.setResizable(false);
        stage.setTitle("Super CSC FighterZ");
        stage.setHeight(INIT_HEIGHT);
        stage.setWidth(INIT_WIDTH);
        stage.setScene(root);
        stage.show();
        
        pressedKeys = new HashSet<>();
        root.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        root.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    public static void setWindowSize(int newHeight) {
        Game.height = newHeight;
        Game.width = newHeight * 16 / 9;
        Game.hRatio = newHeight / 1080.0;

        getStage().setHeight(height);
        getStage().setWidth(width);
    }
    
    public static void addFighter(Fighter fighter) {
        if(fighter.getProfessor() == Professor.Falessi)
            fighterFalessi = fighter;
        else
            fighterMammen = fighter;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static double getHRatio() {
        return hRatio;
    }

    public static Stage getStage() {
        return pStage;
    }

    public static void setPreviousScene(int scene) {
        previousScene = scene;
    }

    // TODO add return for scenes other than 0
    public static Parent getPreviousScene() {
        if (previousScene == 1)
            return null;
        return new MainMenu();
    }
}