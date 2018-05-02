package com.fighterz.main;

import javafx.scene.layout.StackPane;

public class FightingStage extends StackPane {
    public FightingStage(Professor player1, Professor player2) {
        BackButton backBtn = new BackButton();
        SimpleImage background = new SimpleImage("StageBasketball.jpg", true);

        Fighter falessi = new Fighter(Professor.Falessi);
        Fighter mammen = new Fighter(Professor.Mammen);
        
        falessi.getSprite().setTranslateX(350);
        mammen.getSprite().setTranslateX(-350);
        mammen.getSprite().setScaleX(-1);

        this.getChildren().addAll(background, falessi.getSprite(), mammen.getSprite(), backBtn);
    }
}
