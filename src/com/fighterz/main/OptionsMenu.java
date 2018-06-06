package com.fighterz.main;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OptionsMenu extends StackPane implements GameScene {
	private static final Logger logger = Logger.getLogger(OptionsMenu.class.getName());

    private static final int MIN_BTN_Y = -170;
    
    private GridPane grid;

    
    public ObservableList<Node> getNodes() {
        return this.getChildren();
    }

    @Override
    public void render() {
    	
    	grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets((MIN_BTN_Y + 440) * Window.getHRatio(), 0, 0, (MIN_BTN_Y + 570) * Window.getHRatio()));
    	
        SimpleImage imgView = new SimpleImage("OptionsMenu.png", true);

        ImageView logoView = MainMenu.setupLogo();

        OptionsButton minRes = new OptionsButton("640 x 480");
        OptionsButton medRes = new OptionsButton("1280 x 720");
        OptionsButton maxRes = new OptionsButton("1920 x 1080");
        Slider musicVolume = new Slider(0.0, 1.0, AudioManager.Music.getVolume());
        musicVolume.setStyle("-fx-control-inner-background: orange; -fx-highlight-fill: red;");

        musicVolume.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				AudioManager.Music.setVolume(newValue.doubleValue());
			}
		});

        SimpleImage pointer = new SimpleImage("OptionPointer.png", false);
        SimpleImage container = new SimpleImage("OptionsContainer.png", true);
        SimpleImage heading = new SimpleImage("OptionsHeading.png", true);
        BackButton backBtn = new BackButton(Window.getGame().getMainMenu());

        OptionsLabel resLabel = new OptionsLabel("Resolution");
        OptionsLabel musicVolLabel = new OptionsLabel("Music Volume");
        OptionsLabel creditsLabel = new OptionsLabel("Credits");
        
        OptionsLabel viewCreditsButton = new OptionsLabel("View Credits");
        viewCreditsButton.setTextFill(Color.WHITE);
        viewCreditsButton.setCursor(Cursor.HAND);
        GameScene that = this;
        viewCreditsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	Window.switchScene(new CreditsScreen(that));
                AudioManager.MenuSounds.playButtonClickedSound();
            }
        });
        viewCreditsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                AudioManager.MenuSounds.playButtonMousedOverSound();
            }
        });
        
        OptionsLabel instructionsLabel = new OptionsLabel("Instructions");
        OptionsLabel viewInstructionsButton = new OptionsLabel("View Controls");
        viewInstructionsButton.setTextFill(Color.WHITE);
        viewInstructionsButton.setCursor(Cursor.HAND);
        viewInstructionsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	Window.switchScene(new InstructionsScreen(that));
                AudioManager.MenuSounds.playButtonClickedSound();
            }
        });
        viewInstructionsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                AudioManager.MenuSounds.playButtonMousedOverSound();
            }
        });
        
        MainMenu.addGlowEffect(viewCreditsButton);
        MainMenu.addGlowEffect(viewInstructionsButton);
        MainMenu.addGlowEffect(minRes);
        MainMenu.addGlowEffect(medRes);
        MainMenu.addGlowEffect(maxRes);
        MainMenu.addGlowEffect(musicVolume);
        
        grid.add(resLabel, 0, 0);
        grid.add(minRes, 2, 0);
        grid.add(medRes, 2, 1);
        grid.add(maxRes, 2, 2);
        grid.add(musicVolLabel, 0, 4);
        grid.add(musicVolume, 2, 4);
        if (Window.getHeight() == 480) {
        	grid.add(pointer, 1, 0);
        } else if (Window.getHeight() == 720) {
        	grid.add(pointer, 1, 1);
        } else if (Window.getHeight() == 1080) {
        	grid.add(pointer, 1, 2);
        } else {
        	logger.severe("Window resolution not recognized");
        	grid.add(pointer, 1, 1);
        }
        
        grid.add(creditsLabel, 0, 5);
        grid.add(viewCreditsButton, 2, 5);
        
        grid.add(instructionsLabel, 0, 6);
        grid.add(viewInstructionsButton, 2, 6);
        
        this.getChildren().addAll(imgView, backBtn, logoView, container, heading);
        this.getChildren().remove(grid);
        this.getChildren().add(grid);
        grid.toFront();
        
        backBtn.toFront();

        minRes.setupHandlers(this, pointer);
        medRes.setupHandlers(this, pointer);
        maxRes.setupHandlers(this, pointer);
    }
    
    private class OptionsLabel extends Label {

        public OptionsLabel(String label) {
            super(label);
            this.setupAppearence();
        }

        private void setupAppearence() {
            this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, Window.getHRatio() * 54 - 10));
            this.setTextFill(Color.WHITE);
        }
    }

    private class OptionsButton extends Label {

        private int heightVal;

        public OptionsButton(String label) {
            super(label);
            this.heightVal = Integer.parseInt(label.split(" ")[2]);
            this.setupAppearence();
        }

        private void setupAppearence() {
            this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, Window.getHRatio() * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);
        }

        public void setupHandlers(StackPane optionsMenu, SimpleImage pointer) {
            OptionsButton that = this;

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                	grid.getChildren().remove(pointer);
                	grid.add(pointer, 1, GridPane.getRowIndex(that));

                    // So that the highlight doesn't appear on top of us
                    that.toFront();
                    
                    AudioManager.MenuSounds.playButtonMousedOverSound();
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Window.setWindowSize(that.heightVal);
                    that.getChildren().clear();
                    render();
                    
                    AudioManager.MenuSounds.playButtonClickedSound();
                }
            });
        }
    }
}
