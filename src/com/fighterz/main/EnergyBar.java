package com.fighterz.main;

import javafx.scene.image.Image;

public class EnergyBar extends SimpleImage {
	
	private int level;

	public EnergyBar() {
		super("EnergyBar0.png", false);
		this.level = 0;
	}

	public void increase() {
		if(level == 2)
			return;
		this.level += 1;
		this.setBar();
	}
	
	public void decrease() {
		this.level -= 1;
		this.setBar();
	}
	
	private void setBar() {
		this.setImage(new Image("EnergyBar" + this.level + ".png"));
	}

	public boolean checkEnergy(int i) {
		return level >= i;
	}
}
