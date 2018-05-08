package com.fighterz.main;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitBox {
	
	private Rectangle hitBoxRect;
	private GameObject object;
	
	private HitBoxType type;
	private double offsetX = 0;
	private double offsetY = 0;

	public HitBox(GameObject object, HitBoxType type, double width, double height) {
		this.object  = object;
		this.hitBoxRect = new Rectangle(object.getX(), object.getY(), 
		        width * Window.getHRatio(), height * Window.getHRatio());
		this.type = type;
		setColorProperties();
        
        Window.getGame().addHitBox(this);
	}
	
	public HitBox(GameObject object, HitBoxType type, double width, double height, 
	        double offsetX, double offsetY) {
		this(object, type, width, height);
		this.offsetX = offsetX * Window.getHRatio();
		this.offsetY = offsetY * Window.getHRatio();
	}
	
	private void setColorProperties() {
	    this.hitBoxRect.setFill(Color.TRANSPARENT);
	    // Show hit boxes if in debug mode
	    int strokeWidth = Window.DEBUG ? 2 : 0;
	    this.hitBoxRect.setStrokeWidth(strokeWidth);
	}

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
	
	public HitBoxType getType() {
	    return this.type;
	}
	
	public void markForDeletion() {
		Window.getGame().getHandler().removeHitBox(this);
		this.getObject().getHitBoxes().remove(this);
		Window.getGameScene().getNodes().remove(this.getRect());
	}
	
	// get rekt
	public Rectangle getRect() {
		return this.hitBoxRect;
	}
}
