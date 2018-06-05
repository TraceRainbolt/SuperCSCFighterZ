package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class MainMenu extends StackPane implements GameScene {

    private CharacterSelectScreen charSelectScreen;
    private FightingStage fightingStage;
    private OptionsMenu optionsMenu;
    private LeaderBoards leaderBoards;

    public MainMenu(CharacterSelectScreen charSelectScreen, FightingStage fightingStage, OptionsMenu optionsMenu, LeaderBoards leaderBoards) {
        this.charSelectScreen = charSelectScreen;
        this.fightingStage = fightingStage;
        this.optionsMenu = optionsMenu;
        this.leaderBoards = leaderBoards;
        
        AudioManager.Music.playMenuMusic();
    }

    public ObservableList<Node> getNodes() {
        return this.getChildren();
    }

    @Override
    public void render() {
        SimpleImage background = new SimpleImage("MainMenuBG.png", true);
        background.setHeight(Window.getHeight());
        background.setWidth(Window.getWidth());

        ImageView logoView = setupLogo();

        MainMenuButton play = new MainMenuButton("Play", charSelectScreen);
        MainMenuButton leaderboards = new MainMenuButton("Leaderboards", leaderBoards);
        MainMenuButton options = new MainMenuButton("Options", optionsMenu);
        MainMenuButton exit = new MainMenuButton("Exit");

        int maxX = (int) (Window.getHRatio() * 420);
        play.setTranslateX(-maxX);
        leaderboards.setTranslateX((float) -maxX / 2 + Window.getHRatio() * 90); // Leaderboard btn is offset b/c it's
                                                                                 // so long
        options.setTranslateX(maxX / 2);
        exit.setTranslateX(maxX);

        this.getChildren().addAll(background, logoView);
        setupYellowBar();
        this.getChildren().addAll(play, leaderboards, options, exit);
    }
    
    private void setupYellowBar() {
    	SimpleImage yellowBar = new SimpleImage("MainMenuYellowBar.png", false);
        SimpleImage menuMask = new SimpleImage("MainMenuMask.png", false);
    	
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
    		new KeyFrame(Duration.ZERO, new KeyValue(yellowBar.translateYProperty(), -2 * Window.getHRatio())),
            new KeyFrame(new Duration(1600), new KeyValue(yellowBar.translateYProperty(), -80 * Window.getHRatio()))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        yellowBar.setOpacity(0.7);
     
        this.getChildren().addAll(yellowBar, menuMask);
    }

    protected static ImageView setupLogo() {
        SimpleImage logo = new SimpleImage("MainMenuLogo.png");
        logo.setFitHeight(Window.getHeight());
        logo.setFitWidth(Window.getWidth());

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(logo.rotateProperty(), -2)),
                new KeyFrame(new Duration(800), new KeyValue(logo.rotateProperty(), 2)),
                new KeyFrame(new Duration(1600), new KeyValue(logo.rotateProperty(), -2)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return logo;
    }

    public static void addGlowEffect(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                button.setEffect(new Glow(0.8));
            }
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                button.setEffect(null);
            }
        });
    }

    private class MainMenuButton extends Label {
    	
    	private DropShadow dropShadow;

        public MainMenuButton(String label, GameScene newScene) {
            super(label);

            this.setupAppearence();
            this.setupHandlers();

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					Window.switchScene(newScene);

					AudioManager.MenuSounds.playButtonClickedSound();
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
            this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, Window.getHRatio() * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);
            
            this.dropShadow = new DropShadow();
            dropShadow.setRadius(1.6);
            dropShadow.setOffsetX(2.0);
            dropShadow.setOffsetY(2.0);
            dropShadow.setColor(Color.BLACK);
            
            this.setEffect(dropShadow);

            // Relative height to window size, 1/4 of the way up from the bottom
            this.setTranslateY(Window.getHeight() / 2 - Window.getHeight() / 4);
        }

        private void setupHandlers() {
            MainMenuButton that = this;

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.dropShadow.setInput(new Glow(0.8));
                    
                    AudioManager.MenuSounds.playButtonMousedOverSound();
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                	that.dropShadow.setInput(null);
                    that.setEffect(that.dropShadow);
                }
            });
        }
    }
}
