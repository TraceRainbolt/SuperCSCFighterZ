package com.fighterz.main;

import javafx.scene.Node;

public abstract class GameObject {
    
	private Node sprite;
	private double x;
	private double y;

    public abstract void tick();

	public abstract HitBox getHitBox();
	
	public abstract void onCollide(GameObject collider);
	
	public void setSprite(Node sprite) {
	    this.sprite = sprite;
	}

    public Node getSprite() {
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
