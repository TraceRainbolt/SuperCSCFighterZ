package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class LoadingScreen extends StackPane implements GameScene  {

	@Override
	public void render() {
		SimpleImage background = new SimpleImage("LoadingScreen.png", true);
		
		this.getChildren().addAll(background);
	}

	@Override
	public ObservableList<Node> getNodes() {
		return this.getChildren();
	}

}
