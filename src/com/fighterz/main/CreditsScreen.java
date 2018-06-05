package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CreditsScreen extends StackPane implements GameScene {

	private GameScene optionsMenu;
	
	public CreditsScreen(GameScene optionsMenu) {
		this.optionsMenu = optionsMenu;
	}
	
	@Override
	public void render() {
		SimpleImage imgView = new SimpleImage("OptionsMenu.png", true);
		SimpleImage creditsImage = new SimpleImage("CreditsImage.png", true);

        ImageView logoView = MainMenu.setupLogo();
		BackButton backBtn = new BackButton(optionsMenu);
		
		this.getChildren().addAll(imgView, logoView, creditsImage, backBtn);
	}

	@Override
	public ObservableList<Node> getNodes() {
		return this.getChildren();
	}

}