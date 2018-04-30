package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

    // 16 : 9 Ratio based off height
    int HEIGHT = 720;
    int WIDTH = HEIGHT * 16 / 9;
    
    // Used to determine values based off ratio of current res (HEIGHT) to standard res (1080p)
    double H_RATIO = HEIGHT / 1080.0;

    Parent mainMenuLayout, characterSelectLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        characterSelectLayout = createCharacterSelect();
        mainMenuLayout = createMainMenu();

        Scene root = new Scene(mainMenuLayout);

        stage.setResizable(false);
        stage.setTitle("Super CSC FighterZ");
        stage.setScene(root);
        stage.show();
    }

    private StackPane createMainMenu() {
        StackPane mainMenuLayout = new StackPane();

        Image background = new Image("MainMenuBG.png");
        ImageView imgView = new ImageView(background);
        imgView.setFitHeight(HEIGHT);
        imgView.setFitWidth(WIDTH);

        Image logo = new Image("MainMenuLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(HEIGHT);
        logoView.setFitWidth(WIDTH);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(logoView.rotateProperty(), -2)),
            new KeyFrame(new Duration(800), new KeyValue(logoView.rotateProperty(), 2)),
            new KeyFrame(new Duration(1600), new KeyValue(logoView.rotateProperty(), -2))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        MainMenuButton play = new MainMenuButton("Play", createCharacterSelect());
        MainMenuButton leaderboards = new MainMenuButton("Leaderboards", createCharacterSelect());
        MainMenuButton options = new MainMenuButton("Options", createCharacterSelect());
        MainMenuButton exit = new MainMenuButton("Exit");
     
        int maxX = (int) (H_RATIO * 420);
        play.setTranslateX(-maxX);
        leaderboards.setTranslateX(-maxX / 2 + H_RATIO * 90); // Leaderboard btn is offset b/c it's so long
        options.setTranslateX(maxX / 2);
        exit.setTranslateX(maxX);

        mainMenuLayout.getChildren().add(imgView);
        mainMenuLayout.getChildren().add(logoView);
        mainMenuLayout.getChildren().add(play);
        mainMenuLayout.getChildren().add(leaderboards);
        mainMenuLayout.getChildren().add(options);
        mainMenuLayout.getChildren().add(exit);

        return mainMenuLayout;
    }

    private StackPane createCharacterSelect() {
        StackPane characterSelectLayout = new StackPane();
        Image background = new Image("CharacterSelect.png");
        ImageView imgView = new ImageView(background);
        imgView.setFitHeight(HEIGHT);
        imgView.setFitWidth(WIDTH);

        // Eventually null will be replaced with a reference to the previous scene
        // For now we assume mainMenu
        BackButton backBtn = new BackButton(null);

        characterSelectLayout.getChildren().add(imgView);
        characterSelectLayout.getChildren().add(backBtn);
        return characterSelectLayout;
    }

    public class MainMenuButton extends Label {

        public MainMenuButton(String label, Parent newScene) {
            super(label);

            this.setupAppearence();
            this.setupHandlers();

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    getScene().setRoot(newScene);
                }
            });
        }

        // Currently only for exit button
        public MainMenuButton(String label) {
            super(label);

            this.setupAppearence();
            this.setupHandlers();

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.exit(0);
                }
            });
        }

        private void setupAppearence() {
            // TODO better font
            this.setFont(Font.font("Arial", FontWeight.NORMAL, H_RATIO * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);

            // Relative height to window size, 1/4 of the way up from the bottom
            this.setTranslateY(HEIGHT / 2 - HEIGHT / 4);
        }

        private void setupHandlers() {
            // B/c we lose reference to ourself inside event handler
            MainMenuButton that = this;

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(new Glow(0.8));
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(null);
                }
            });
        }
    }

    public class BackButton extends ImageView {

        public BackButton(Parent prevScene) {
            super(new Image("BackIcon.png"));  
            
            this.setCursor(Cursor.HAND);
            double side = H_RATIO * new Image("BackIcon.png").getHeight();
            this.setFitHeight(side);
            this.setFitWidth(side);

            // Save ourself for event handler
            BackButton that = this;
            this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setTranslateY(that.getTranslateY() + 2);
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    getScene().setRoot(createMainMenu());
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(new Glow(0.8));
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(null);
                }
            });

            // Place back button in left corner relative to window size
            this.setTranslateY(HEIGHT / 2 - H_RATIO * 90);
            this.setTranslateX(-WIDTH / 2 + H_RATIO * 90);
        }
    }
}