package com.fighterz.main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitBox extends GameObject {
	
	private Rectangle hitBoxRect;
	private GameObject object;
	
	private double offsetX = 0;
	private double offsetY = 0;

	public HitBox(GameObject object, double width, double height) {
		this.object  = object;
		this.hitBoxRect = new Rectangle(object.getX(), object.getY(), width * Window.getHRatio(), height * Window.getHRatio());
		this.setSprite(hitBoxRect);
		setColorProperties();
        
        Window.getGame().addObjects(this);
	}
	
	public HitBox(GameObject object, double width, double height, double offsetX, double offsetY) {
		this(object, width + offsetX, height + offsetY);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	private void setColorProperties() {
	    this.hitBoxRect.setFill(Color.TRANSPARENT);
	    // Set stroke width to 2 to see hitBoxes
	    this.hitBoxRect.setStrokeWidth(2);
	}

	@Override
	public void tick() {
        this.hitBoxRect.toFront();
		updateHitboxLocation();
	}
	
	public GameObject getObject() {
		return object;
	}
	
	private void updateHitboxLocation() {
		this.hitBoxRect.setTranslateX(this.object.getX() + offsetX);
		this.hitBoxRect.setTranslateY(this.object.getY() + offsetY);
	}
	
	// get rekt
	public Rectangle getRect() {
		return this.hitBoxRect;
	}

	@Override
	public HitBox getHitBox() {
		return null;
	}

	@Override
	public void onCollide(GameObject collider) {
        // nothing
	}
}
