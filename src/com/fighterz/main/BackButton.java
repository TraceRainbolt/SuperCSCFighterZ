package com.fighterz.main;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BackButton extends ImageView {

    public BackButton() {
        super(new Image("BackIcon.png"));

        this.setCursor(Cursor.HAND);
        double side = Window.getHRatio() * new Image("BackIcon.png").getHeight();
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
                Window.setScene(Window.getPreviousScene());
            }
        });

        MainMenu.addGlowEffect(this);

        // Place back button in left corner relative to window size
        this.setTranslateY((float) Window.getHeight() / 2 - Window.getHRatio() * 130);
        this.setTranslateX((float) -Window.getWidth() / 2 + Window.getHRatio() * 110);
    }
}