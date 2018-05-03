package com.fighterz.main;

import javafx.scene.layout.StackPane;

public class FightingStage extends StackPane {
    public FightingStage(Professor player1, Professor player2) {
    	initUi(player1, player2);
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
        	this.getChildren().removeAll();
        	initUi(player1, player2);
        });
    }
    
    private void initUi(Professor player1, Professor player2) {
        BackButton backBtn = new BackButton();
        SimpleImage background = new SimpleImage("StageBasketball.jpg", true);

        Fighter falessi = new Fighter(Professor.Falessi);
        Fighter mammen = new Fighter(Professor.Mammen);
        
        falessi.getSprite().setTranslateX(550 * Game.getHRatio());
        mammen.getSprite().setTranslateX(-550 * Game.getHRatio());
        mammen.getSprite().setScaleX(-1);

        this.getChildren().addAll(background, falessi.getSprite(), mammen.getSprite(), backBtn);
    }
}
