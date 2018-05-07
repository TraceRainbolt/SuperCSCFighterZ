package com.fighterz.main;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitBox extends GameObject {
	
	private Rectangle hitBox;
	private GameObject object;

	public HitBox(GameObject object, double width, double height) {
		this.object  = object;
		this.hitBox = new Rectangle(object.getX(), object.getY(), width * Window.getHRatio(), height * Window.getHRatio());
        this.hitBox.setFill(Color.TRANSPARENT);
        this.hitBox.setStrokeWidth(2);
	}

	@Override
	public void tick() {
        this.hitBox.toFront();
		updateHitboxLocation();
	}
	
	private void updateHitboxLocation() {
		this.hitBox.setTranslateX(this.object.getX());
		this.hitBox.setTranslateY(this.object.getY());
	}
	
	// get rekt
	public Rectangle getRect() {
		return this.hitBox;
	}

	@Override
	public Node getSprite() {
		return this.hitBox;
	}

	@Override
	public double getX() {
		return this.hitBox.getTranslateX();
	}

	@Override
	public double getY() {
		return this.hitBox.getTranslateY();
	}

	@Override
	public HitBox getHitBox() {
		return null;
	}
}
