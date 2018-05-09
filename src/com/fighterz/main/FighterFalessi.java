package com.fighterz.main;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FighterFalessi extends Fighter {
	
    private final Image powerBallImage = new Image("SpriteFalessiPowerBall.png", false);
    private final Image idleImage = new Image("SpriteFalessiIdle.png", false);
	
    public FighterFalessi(String side) {
    	super(side);
        this.setAnimation(AnimationState.IDLE);
        
    	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 250, 830);
    	hitBoxes.add(hurtBox);
    }
    
    public void setAnimation(AnimationState state) {
    	setupSprite(state);
    }
    
    private void setupSprite(AnimationState state) {
        if(state == AnimationState.POWER_BALL) {
        	if(currentAnimation != null) {
        		Window.getGame().setFalessiMovementLock(true);
        		originalX = this.getX();
        		
        		currentAnimation.jumpTo(Duration.millis(2000));
        		currentAnimation.pause();

        		this.setSprite(idleImage);
        		setPowerBall();
        	}
        } else {
	    	this.setSprite(idleImage);
	    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
	    	currentAnimation.setCycleCount(Animation.INDEFINITE);
	    	currentAnimation.play();
        }
    }
    
    private void setPowerBall() {
		HitBox hitBox = createPowerBallHitbox();
		this.hitBoxes.add(hitBox);
    	this.setSprite(powerBallImage);
    	this.getSprite().setX(this.getSprite().getX() + 100);
    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29, 375);
    	currentAnimation.setCycleCount(1);
    	currentAnimation.play();
    	currentAnimation.setOnFinished(e -> setIdle());
    }
    
    // Sets all the params for the power ball hit box. Trial and error was used.
    private HitBox createPowerBallHitbox() {
    	HitBox hitBox = new HitBox(this, HitBoxType.HIT, 100, 100, 100, -150);
		hitBox.setIndependent(true);
		hitBox.setDelay(2);
		hitBox.setXVelocity(-7);
		hitBox.setMaxDuration(4.1);
		return hitBox;
    }
    
    private void setIdle() {
    	this.getSprite().setTranslateX(originalX);
    	this.setSprite(idleImage);
    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
    	currentAnimation.setCycleCount(Animation.INDEFINITE);
    	currentAnimation.play();
		Window.getGame().setFalessiMovementLock(false);
    }
    
}
