package com.fighterz.main;

import javafx.animation.Animation;
import javafx.util.Duration;

public class Fighter extends GameObject {
    
    private static final int SPEED = (int) (10 * Window.getHRatio());

    private Professor professor;
    private SimpleImage sprite;
    
    private HitBox hitBox;
    
    private double x;
    private double y;

    public Fighter(Professor professor) {
        this.professor = professor;
        this.setAnimation(AnimationState.Idle);
        Window.getGame().addFighter(this);
        
        this.hitBox = new HitBox(this, 250, 820);
        Window.getGame().addObjects(this.hitBox);
    }
    
    public void tick() {

    }

	public Professor getProfessor() {
        return this.professor;
    }

    public SimpleImage getSprite() {
        return this.sprite;
    }
    
    public void moveRight() {
    	this.setX(this.getX() + SPEED);
    }
    
    public void moveLeft() {
        this.setX(this.getX() - SPEED);
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
    
    public HitBox getHitBox() {
        return this.hitBox;
    }

    // TODO a way to get frameCount from state + name
    public void setAnimation(AnimationState state) {
        StringBuilder filePath = new StringBuilder("Sprite");
       
        filePath.append(this.getProfessor().name());
        filePath.append(state.name());
        filePath.append(".png");
        
        // Remove this once mammen sprites are in
        if(this.getProfessor() == Professor.Mammen)
            filePath = new StringBuilder("SpriteFalessiIdle.png");

        final SimpleImage fighterSprite = new SimpleImage(filePath.toString(), false);
        final int frameCount = 29;
        final Animation animation = new SpriteAnimation(fighterSprite, Duration.millis(1000), frameCount);

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.sprite = fighterSprite;
    }
}