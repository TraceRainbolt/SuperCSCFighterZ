package com.fighterz.main;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitBox extends GameObject {
	
	private Rectangle hitBox;
	private GameObject object;
	
	private double offsetX = 0;
	private double offsetY = 0;

	public HitBox(GameObject object, double width, double height) {
		this.object  = object;
		this.hitBox = new Rectangle(object.getX(), object.getY(), width * Window.getHRatio(), height * Window.getHRatio());
		setColorProperties();
        
        Window.getGame().addObjects(this);
	}
	
	public HitBox(GameObject object, double width, double height, double offsetX, double offsetY) {
		this(object, width + offsetX, height + offsetY);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	private void setColorProperties() {
	    this.hitBox.setFill(Color.TRANSPARENT);
	    // Set stroke width to 2 to see hitBoxes
	    this.hitBox.setStrokeWidth(0);
	}

	@Override
	public void tick() {
        this.hitBox.toFront();
		updateHitboxLocation();
	}
	
	public GameObject getObject() {
		return object;
	}
	
	private void updateHitboxLocation() {
		this.hitBox.setTranslateX(this.object.getX() + offsetX);
		this.hitBox.setTranslateY(this.object.getY() + offsetY);
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

	@Override
	public void onCollide(GameObject collider) {

	}
}
