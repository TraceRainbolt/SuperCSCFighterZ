package com.fighterz.main;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;

public class Fighter extends GameObject {
    
    private static final int SPEED = (int) (10 * Window.getHRatio());
    
    protected LinkedList<HitBox> hitBoxes;
    protected String side;
    
    protected double originalX;
    
    protected Animation currentAnimation;
    
    public Fighter(String side) {
    	this.side = side;
    	
        Window.getGame().addFighter(this);
        hitBoxes = new LinkedList<>();
    }
    
    public void tick() {
        // tick tock
    }
    
	@Override
	public void onCollide(GameObject object) {
	    Window.getGame().getFightingStage().subtractHealth(16, side);
	}

    public void moveRight() {
    	this.setX(this.getX() + SPEED);
    }
    
    public void moveLeft() {
        this.setX(this.getX() - SPEED);
    }
    
    public List<HitBox> getHitBoxes() {
        return this.hitBoxes;
    }
}