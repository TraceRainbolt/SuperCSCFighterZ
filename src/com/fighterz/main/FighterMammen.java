package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FighterMammen extends Fighter {
	
    private final Image idleImage = new Image("SpriteMammenIdle.png", false);
    private final Image pointerVision = new Image("SpriteMammenPointerVision.png", false);
    
    public FighterMammen(String side) {
    	super(side);
        this.setAnimation(AnimationState.IDLE);
        
    	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 292, 510, 0, -85);
    	this.addHitBox(hurtBox);
    }
    
    public void setAnimation(AnimationState state) {
        setupSprite(state);
    }
    
    private void setupSprite(AnimationState state) {
    	
        if(state == AnimationState.POINTER_VISION) {
        	if(currentAnimation != null) {
        		Window.getGame().setMammenMovementLock(true);
        		originalX = this.getX();
        		
        		// Gotta do the jumpTo here as well
        		currentAnimation.jumpTo(Duration.millis(2000));
        		currentAnimation.pause();

        		this.setSprite(idleImage);
        		setPointerVision();
        	}
        } else {
            this.setSprite(idleImage);
            currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 27);
        	currentAnimation.setCycleCount(Animation.INDEFINITE);
        	currentAnimation.play();
        }
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
        		new KeyFrame(Duration.ZERO, new KeyValue(this.getSprite().translateYProperty(), -88 * Window.getHRatio())),
        		new KeyFrame(new Duration(600), new KeyValue(this.getSprite().translateYProperty(), -100 * Window.getHRatio())),
                new KeyFrame(new Duration(1200), new KeyValue(this.getSprite().translateYProperty(), -88 * Window.getHRatio())));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void setPointerVision() {
		HitBox hitBox = createPointerVisionHitbox();
		this.addHitBox(hitBox);
		this.getSprite().toFront();
    	this.setSprite(pointerVision);
    	this.getSprite().setX(this.getSprite().getX() + 100);
    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 25, (int) (-375 * Window.getHRatio()));
    	currentAnimation.setCycleCount(1);
    	currentAnimation.play();
    	currentAnimation.setOnFinished(e -> setIdle());
    }
    
    private HitBox createPointerVisionHitbox() {
    	HitBox hitBox = new HitBox(this, HitBoxType.HIT, 700, 140, 500, -110);
    	hitBox.setDelay(0.2);
    	hitBox.setMaxDuration(8.5);
		return hitBox;
    }
    
    
    private void setIdle() {
    	this.getSprite().setTranslateX(originalX);
    	this.setSprite(idleImage);
    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 27);
    	currentAnimation.setCycleCount(Animation.INDEFINITE);
    	currentAnimation.play();
		Window.getGame().setMammenMovementLock(false);
    }
    
}
