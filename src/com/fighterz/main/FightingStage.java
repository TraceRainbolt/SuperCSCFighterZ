package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class FightingStage extends StackPane implements GameScene {
	
    public FightingStage(Professor player1, Professor player2) {
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
        	
        });
    }
    
    public void render() {
        BackButton backBtn = new BackButton();
        SimpleImage background = new SimpleImage("StageBasketball.jpg", true);

        Fighter falessi = new Fighter(Professor.Falessi);
        Fighter mammen = new Fighter(Professor.Mammen);
        
        falessi.setX(550 * Window.getHRatio());
        mammen.setX(-550 * Window.getHRatio());
        mammen.getSprite().setScaleX(-1);

        this.getNodes().addAll(background, backBtn);
        Window.getGame().addObjects(mammen, falessi);
    }
    
    public ObservableList<Node> getNodes() {
    	return this.getChildren();
    }
    
}
