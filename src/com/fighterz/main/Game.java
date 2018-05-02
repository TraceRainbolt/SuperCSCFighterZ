package com.fighterz.main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {
    // 16 : 9 Ratio based off height
    private final static int INIT_HEIGHT = 720;
    private final static int INIT_WIDTH = INIT_HEIGHT * 16 / 9;

    // Used to determine values based off ratio of current res (HEIGHT) to standard res (1080p)
    private static double hRatio = INIT_HEIGHT / 1080.0;

    // Mutable height and with variables
    private static int height = INIT_HEIGHT, width = INIT_WIDTH;
    
    private static int previousScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        StackPane mainMenuLayout = new MainMenu();
        previousScene = 0;

        Scene root = new Scene(mainMenuLayout);

        stage.setResizable(false);
        stage.setTitle("Super CSC FighterZ");
        stage.setHeight(INIT_HEIGHT);
        stage.setWidth(INIT_WIDTH);
        stage.setScene(root);
        stage.show();
    }

    public static void setWindowSize(Stage stage, int newHeight) {
        Game.height = newHeight;
        Game.width = newHeight * 16 / 9;
        Game.hRatio = newHeight / 1080.0;

        stage.setHeight(height);
        stage.setWidth(width);
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
    
    public static void setPreviousScene(int scene) {
    	previousScene = scene;
    }
    
    public static Parent getPreviousScene() {
    	if(previousScene == 1)
    		return null;
    	return new MainMenu();
    }
}