package com.fighterz.main;

import java.util.LinkedList;

import javafx.scene.image.Image;

public abstract class GameObject {
    
	private SimpleImage sprite = new SimpleImage("spriteFalessiIdle.png");
	
	private double x;
	private double y;

    public abstract void tick();

	public abstract LinkedList<HitBox> getHitBoxes();
	
	public abstract void onCollide(GameObject gameObject);
	
	public void setSprite(Image image) {
	    this.sprite.setImage(image);
	    this.sprite.setWidth((int) (image.getWidth() * Window.getHRatio()));
	    this.sprite.setHeight((int) (image.getHeight() * Window.getHRatio()));
	    this.sprite.setPreserveRatio(true);
	}

    public SimpleImage getSprite() {
        return this.sprite;
    }
	
    public void setX(double x) {
        this.x = x;
        this.sprite.setTranslateX(x);
    }
    
    public void setY(double y) {
        this.y = y;
        this.sprite.setTranslateY(y);
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
}
