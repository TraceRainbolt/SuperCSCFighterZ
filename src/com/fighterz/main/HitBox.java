package com.fighterz.main;

import java.awt.Rectangle;

public class HitBox {
    
	private Fighter fighter;
	
    private int width;
    private int height;
    
    private int x;
    private int y;
    
    public HitBox(Fighter fighter) {
    	this.fighter = fighter;
        this.width = fighter.getSprite().getWidth();
        this.height = fighter.getSprite().getHeight();
    }
    
    public boolean hitTest(HitBox hbox) {
    	Rectangle rectMe = new Rectangle(getX(), getY(), width, height);
        Rectangle rectThem = new Rectangle(hbox.getX(), hbox.getY(), hbox.getWidth(), hbox.getHeight());
        return rectMe.intersects(rectThem);
    }
    
    public int getHeight() {
		return this.height;
	}

    public int getWidth() {
		return this.width;
	}

	public int getX() {
    	return (int) fighter.getX();
    }
    
    public int getY() {
    	return (int) fighter.getY();
    }
    
    public void display() {
    	
    }
}
