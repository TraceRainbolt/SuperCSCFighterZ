package com.fighterz.main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CharacterSelectScreen extends StackPane {
    public CharacterSelectScreen() {
        Image background = new Image("CharacterSelect.png");
        ImageView imgView = new ImageView(background);
        imgView.setFitHeight(Game.getHeight());
        imgView.setFitWidth(Game.getWidth());

        BackButton backBtn = new BackButton();

        this.getChildren().addAll(imgView, backBtn);
    }
}
