package com.fighterz.main;

import javafx.scene.layout.StackPane;

public class FightingStage extends StackPane {
    public FightingStage(Professor player1, Professor player2) {
    	BackButton backBtn = new BackButton();
    	
    	Fighter falessi = new Fighter(Professor.Falessi);
    	
        this.getChildren().addAll(falessi.getSprite(), backBtn);
    }
}
