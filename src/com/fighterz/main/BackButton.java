package com.fighterz.main;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BackButton extends ImageView {

    public BackButton() {
        super(new Image("BackIcon.png"));

        this.setCursor(Cursor.HAND);
        double side = Game.getHRatio() * new Image("BackIcon.png").getHeight();
        this.setFitHeight(side);
        this.setFitWidth(side);

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
                getScene().setRoot(Game.getPreviousScene());
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
        this.setTranslateY((float) Game.getHeight() / 2 - Game.getHRatio() * 130);
        this.setTranslateX((float) -Game.getWidth() / 2 + Game.getHRatio() * 110);
    }
}