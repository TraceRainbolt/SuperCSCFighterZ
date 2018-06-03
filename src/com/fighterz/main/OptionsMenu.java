package com.fighterz.main;

import org.w3c.dom.css.ElementCSSInlineStyle;

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

    private static final int MIN_BTN_Y = -170;
    
    private GridPane grid;

    public OptionsMenu() {
        // Empty constructor
    }

    public ObservableList<Node> getNodes() {
        return this.getChildren();
    }

    @Override
    public void render() {
    	
    	grid = new GridPane();
//    	GridPane grid = new GridPane();
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

        // Starting point for resolution choices
        // Magic numbers for x values simply left align the res label
//
//        medRes.setTranslateX(-259 * Window.getHRatio());
//        medRes.setTranslateY((MIN_BTN_Y + 100) * Window.getHRatio());
//
//        maxRes.setTranslateX(-241 * Window.getHRatio());
//        maxRes.setTranslateY((MIN_BTN_Y + 200) * Window.getHRatio());
        
//        musicVolume.setTranslateX(-270 * Window.getHRatio());
//        musicVolume.setTranslateY((MIN_BTN_Y + 300) * Window.getHRatio());
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
        	System.out.println("dumb");
        	grid.add(pointer, 1, 1);
        }
        
//        grid.getChildren().addAll(minRes, medRes, maxRes, musicVolume);
        this.getChildren().addAll(imgView, backBtn, logoView, container, heading);
        this.getChildren().remove(grid);
        this.getChildren().add(grid);
        grid.toFront();
        
        backBtn.toFront();

//        pointer.setTranslateX(-335 * Window.getHRatio());
//
//        // Always initialize pointer pointing to correct resolution
//        if (Window.getHeight() == 1080) {
//            pointer.setTranslateY((MIN_BTN_Y + 90) * Window.getHRatio());
//        } else if (Window.getHeight() == 720) {
//            pointer.setTranslateY((MIN_BTN_Y + 110) * Window.getHRatio());
//        } else {
//            pointer.setTranslateY(MIN_BTN_Y * Window.getHRatio());
//        }

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
                        
//            SimpleImage highlight = new SimpleImage("OptionHighlighter.png");
//            highlight.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    optionsMenu.getChildren().remove(highlight);
//                }
//            });
//            
//            highlight.setOpacity(0.3);
//            highlight.setFitHeight(highlight.boundsInLocalProperty().getValue().getHeight() * Window.getHRatio());
//            highlight.setFitWidth(highlight.boundsInLocalProperty().getValue().getWidth() * Window.getHRatio());

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
//                	optionsMenu.getChildren().remove(highlight);
//                    optionsMenu.getChildren().add(highlight);
////                    highlight.setTranslateX(that.getTranslateX());
//                    highlight.setTranslateX(that.getParent().getLayoutX());
////                    highlight.setTranslateY(that.getTranslateY());
//                    highlight.setTranslateY(that.getLayoutY() - (490 * Window.getHRatio()));
                	grid.getChildren().remove(pointer);
                	grid.add(pointer, 1, GridPane.getRowIndex(that));

                    // So that the highlight doesn't appear on top of us
                    that.toFront();
                    
                    AudioManager.MenuSounds.playButtonMousedOverSound();
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
//                    optionsMenu.getChildren().remove(highlight);
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
