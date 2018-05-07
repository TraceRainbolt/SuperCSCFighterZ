package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OptionsMenu extends StackPane implements GameScene {

    private static final int MIN_BTN_Y = -170;

    public OptionsMenu() {

    }
    
    public ObservableList<Node> getNodes() {
    	return this.getChildren();
    }
    
    @Override
    public void render() {
        SimpleImage imgView = new SimpleImage("OptionsMenu.png", true);

        ImageView logoView = MainMenu.setupLogo();

        OptionsButton minRes = new OptionsButton("640 x 480");
        OptionsButton medRes = new OptionsButton("1280 x 720");
        OptionsButton maxRes = new OptionsButton("1920 x 1080");

        // Starting point for resolution choices
        // Magic numbers for x values simply left align the res label
        minRes.setTranslateX(-270 * Window.getHRatio());
        minRes.setTranslateY(MIN_BTN_Y * Window.getHRatio());

        medRes.setTranslateX(-259 * Window.getHRatio());
        medRes.setTranslateY((MIN_BTN_Y + 100) * Window.getHRatio());

        maxRes.setTranslateX(-241 * Window.getHRatio());
        maxRes.setTranslateY((MIN_BTN_Y + 200) * Window.getHRatio());

        SimpleImage pointer = new SimpleImage("OptionPointer.png", false);
        SimpleImage container = new SimpleImage("OptionsContainer.png", true);
        SimpleImage heading = new SimpleImage("OptionsHeading.png", true);
        BackButton backBtn = new BackButton();

        this.getChildren().addAll(imgView, backBtn, logoView, container, heading, minRes, medRes, maxRes, pointer);

        pointer.setTranslateX(-450 * Window.getHRatio());

        // Always initialize pointer pointing to correct resolution
        if (Window.getHeight() == 1080) {
            pointer.setTranslateY((MIN_BTN_Y + 200) * Window.getHRatio());
        } else if (Window.getHeight() == 720) {
            pointer.setTranslateY((MIN_BTN_Y + 100) * Window.getHRatio());
        } else {
            pointer.setTranslateY(MIN_BTN_Y * Window.getHRatio());
        }

        minRes.setupHandlers(this, pointer);
        medRes.setupHandlers(this, pointer);
        maxRes.setupHandlers(this, pointer);
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

            SimpleImage highlight = new SimpleImage("OptionHighlighter.png");
            highlight.setFitHeight(highlight.boundsInLocalProperty().getValue().getHeight() * Window.getHRatio());
            highlight.setFitWidth(highlight.boundsInLocalProperty().getValue().getWidth() * Window.getHRatio());

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    optionsMenu.getChildren().add(highlight);
                    highlight.setTranslateX(that.getTranslateX() + (-MIN_BTN_Y) * Window.getHRatio());
                    highlight.setTranslateY(that.getTranslateY());

                    // So that the highlight doesn't appear on top of us
                    that.toFront();
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    optionsMenu.getChildren().remove(highlight);
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                	Window.setWindowSize(that.heightVal);
                    that.getChildren().clear();
                    render();
                }
            });
        }
    }
}
