package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class LeaderBoards extends StackPane implements GameScene {

	@Override
	public void render() {
		SimpleImage background = new SimpleImage("LeaderBoardsBG.png", true);
		BackButton backBtn = new BackButton(Window.getGame().getMainMenu());
		
		this.getChildren().addAll(background, backBtn);
	}

	@Override
	public ObservableList<Node> getNodes() {
		return this.getChildren();
	}

}
