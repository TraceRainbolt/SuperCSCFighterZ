package com.fighterz.main;

import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Fighter extends GameObject {
    
    private static final int SPEED = (int) (10 * Window.getHRatio());

    private Professor professor;
    
    private LinkedList<HitBox> hitBoxes;
    private String side;
    
    private double originalX;
    
    private Animation currentAnimation;
    
    private final Image falessiPowerBallImage = new Image("SpriteFalessiPowerBall.png", false);
    
    private final Image falessiIdleImage = new Image("SpriteFalessiIdle.png", false);
    private final Image mammenIdleImage = new Image("SpriteMammenIdle.png", false);

    public Fighter(Professor professor, String side) {
        this.professor = professor;
        this.setAnimation(AnimationState.IDLE);
        Window.getGame().addFighter(this);
        this.side = side;
        
        hitBoxes = new LinkedList<HitBox>();
        
        if(this.professor == Professor.MAMMEN) {
        	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 290, 510, 0, -65);
        	hitBoxes.add(hurtBox);
        } else {
        	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 250, 830);
        	hitBoxes.add(hurtBox);
        }
    }
    
    public void tick() {
        // tick tock
    }
    
	@Override
	public void onCollide(GameObject object) {
	    Window.getGame().getFightingStage().subtractHealth(0.03, side);
	}

	public Professor getProfessor() {
        return this.professor;
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

    // TODO a way to get frameCount from state + name
    public void setAnimation(AnimationState state) {

    	final Animation animation;
    	
        if(this.professor == Professor.MAMMEN) {
        	setupMammenSprite(state);
        } else {
        	setupFalessiSprite(state);
        }
    }
    
    private void setupMammenSprite(AnimationState state) {
        SpriteAnimation animation;
        
        this.setSprite(mammenIdleImage);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
        		new KeyFrame(Duration.ZERO, new KeyValue(this.getSprite().translateYProperty(), -88 * Window.getHRatio())),
        		new KeyFrame(new Duration(600), new KeyValue(this.getSprite().translateYProperty(), -100 * Window.getHRatio())),
                new KeyFrame(new Duration(1200), new KeyValue(this.getSprite().translateYProperty(), -88 * Window.getHRatio())));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        animation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void setupFalessiSprite(AnimationState state) {
        if(state == AnimationState.POWER_BALL) {
        	if(currentAnimation != null) {
        		Window.getGame().setLock(true);
        		originalX = this.getX();
        		
        		currentAnimation.jumpTo(Duration.millis(2000));
        		currentAnimation.pause();

        		this.setSprite(falessiIdleImage);
        	

        		setPowerBall();
        	}
        } else {
	    	this.setSprite(falessiIdleImage);
	    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
	    	currentAnimation.setCycleCount(Animation.INDEFINITE);
	    	currentAnimation.play();
        }
    }
    
    private void setPowerBall() {
		HitBox hitBox = new HitBox(this, HitBoxType.HIT, 900, 100, -590, -200);
		this.hitBoxes.add(hitBox);
    	this.setSprite(falessiPowerBallImage);
    	this.getSprite().setX(this.getSprite().getX() + 100);
    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
    	currentAnimation.setCycleCount(1);
    	currentAnimation.play();
    	currentAnimation.setOnFinished(e -> setIdle());
    }
    
    private void setIdle() {
    	this.hitBoxes.getLast().markForDeletion();
    	this.getSprite().setTranslateX(originalX);
    	this.setSprite(falessiIdleImage);
    	currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
    	currentAnimation.setCycleCount(Animation.INDEFINITE);
    	currentAnimation.play();
		Window.getGame().setLock(false);
    }
}