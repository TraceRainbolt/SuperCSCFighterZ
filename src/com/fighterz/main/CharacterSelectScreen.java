package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CharacterSelectScreen extends StackPane implements GameScene {
    public CharacterSelectScreen() {
        // Empty constructor
    }

    public ObservableList<Node> getNodes() {
        return this.getChildren();
    }

    @Override
    public void render() {
        Image background = new Image("CharacterSelect.png");
        ImageView imgView = new ImageView(background);
        imgView.setFitHeight(Window.getHeight());
        imgView.setFitWidth(Window.getWidth());

        BackButton backBtn = new BackButton();

        this.getChildren().addAll(imgView, backBtn);
    }

}
