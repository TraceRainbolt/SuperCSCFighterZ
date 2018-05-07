package com.fighterz.main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Window extends Application {
    // 16 : 9 Ratio based off height
    private static final int INIT_HEIGHT = 720;
    private static final int INIT_WIDTH = INIT_HEIGHT * 16 / 9;
    
    // Framerate information
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static Game game;
    private static Stage pStage;
    
    private static int width;
    private static int height;
    private static double hRatio;
    
    private static GameScene currentScene;
    private static GameScene previousScene;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
    	
    	width = INIT_WIDTH;
    	height = INIT_HEIGHT;
    	hRatio = INIT_HEIGHT / 1080.0;
    	pStage = stage;
    	game = new Game(INIT_WIDTH, INIT_HEIGHT);
    	
        stage.setResizable(false);
        stage.setTitle("Super CSC FighterZ");
        stage.setWidth(INIT_WIDTH);
        stage.setHeight(INIT_HEIGHT);
        stage.setScene(game.getScene());
        stage.show();
        
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> game.update(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
    public static Game getGame() {
    	return game;
    }
    
    public static Stage getStage() {
    	return pStage;
    }
    
    public static void initCurrentScene(GameScene scene) {
    	currentScene = scene;
    }
    
    public static void switchScene(GameScene scene) {
    	previousScene = currentScene;
    	currentScene = scene;
    	getGame().getHandler().switchScene(scene);
    	getStage().getScene().setRoot((Parent) scene);
    }
    
    public static GameScene getPreviousScene() {
    	return previousScene;
    }
    
    public static GameScene getGameScene() {
    	return currentScene;
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
    
    public static void setWindowSize(int newHeight) {
    	height = newHeight;
    	width = newHeight * 16 / 9;
    	hRatio = newHeight / 1080.0;

        Window.getStage().setHeight(height);
        Window.getStage().setWidth(width);
    }
}
