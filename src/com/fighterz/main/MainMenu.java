package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class MainMenu extends StackPane {
    public MainMenu() {
        SimpleImage background = new SimpleImage("MainMenuBG.png", true);
        background.setHeight(Game.getHeight());
        background.setWidth(Game.getWidth());

        ImageView logoView = setupLogo();

        MainMenuButton play = new MainMenuButton("Play", new CharacterSelectScreen());
        MainMenuButton leaderboards = new MainMenuButton("Leaderboards", 
                new FightingStage(Professor.Falessi, (Professor.Mammen)));
        MainMenuButton options = new MainMenuButton("Options", new OptionsMenu());
        MainMenuButton exit = new MainMenuButton("Exit");

        int maxX = (int) (Game.getHRatio() * 420);
        play.setTranslateX(-maxX);
        leaderboards.setTranslateX(-maxX / 2 + Game.getHRatio() * 90); // Leaderboard btn is offset b/c it's so long
        options.setTranslateX(maxX / 2);
        exit.setTranslateX(maxX);

        this.getChildren().addAll(background, logoView, play, leaderboards, options, exit);
    }
    
    protected static ImageView setupLogo() {
        SimpleImage logo = new SimpleImage("MainMenuLogo.png");
        logo.setFitHeight(Game.getHeight());
        logo.setFitWidth(Game.getWidth());

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(logo.rotateProperty(), -2)),
                new KeyFrame(new Duration(800), new KeyValue(logo.rotateProperty(), 2)),
                new KeyFrame(new Duration(1600), new KeyValue(logo.rotateProperty(), -2)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        return logo;
    }
    

	private class MainMenuButton extends Label {
	
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
	        this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, Game.getHRatio() * 54));
	        this.setTextFill(Color.WHITE);
	        this.setCursor(Cursor.HAND);
	
	        // Relative height to window size, 1/4 of the way up from the bottom
	        this.setTranslateY(Game.getHeight() / 2 - Game.getHeight() / 4);
	    }
	
	    private void setupHandlers() {
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
}
