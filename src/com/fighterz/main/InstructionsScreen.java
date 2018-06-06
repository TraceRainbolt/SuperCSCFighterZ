package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class InstructionsScreen extends StackPane implements GameScene {

	private GameScene optionsMenu;
	
	public InstructionsScreen(GameScene optionsMenu) {
		this.optionsMenu = optionsMenu;
	}
	
	@Override
	public void render() {
		SimpleImage imgView = new SimpleImage("Controls.png", true);

        ImageView logoView = MainMenu.setupLogo();
		BackButton backBtn = new BackButton(optionsMenu);
		
		this.getChildren().addAll(logoView, imgView, backBtn);
	}

	@Override
	public ObservableList<Node> getNodes() {
		return this.getChildren();
	}

}