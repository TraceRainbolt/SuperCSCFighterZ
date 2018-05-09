package com.fighterz.main;

import java.util.LinkedList;

import javafx.animation.Animation;

public class Fighter extends GameObject {
    
    private static final int SPEED = (int) (10 * Window.getHRatio());

    private String professor;
    
    protected LinkedList<HitBox> hitBoxes;
    protected String side;
    
    protected double originalX;
    
    protected Animation currentAnimation;
    
    
    public Fighter(String side) {
    	this.side = side;
    	
        Window.getGame().addFighter(this);
        hitBoxes = new LinkedList<HitBox>();
        
        if(this.professor == "MAMMEN") {
        	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 292, 510, 0, -65);
        	hitBoxes.add(hurtBox);
        }
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
    
    public LinkedList<HitBox> getHitBoxes() {
        return this.hitBoxes;
    }
}