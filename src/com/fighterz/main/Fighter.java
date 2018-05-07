package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
        
        if(this.professor == Professor.MAMMEN) {
        	this.hitBox = new HitBox(this, 290, 500, 0, -100);
        } else {
        	this.hitBox = new HitBox(this, 250, 820);
        }
    }
    
    public void tick() {

    }
    
	@Override
	public void onCollide(GameObject collider) {
		Window.getGame().getFightingStage().subtractLeftHealth(0.03);
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
    	String filePath;
    	int frameCount;
        if(this.professor == Professor.MAMMEN) {
            filePath = "SpriteMammenIdle.png";
            frameCount = 27;
            
        } else {
            filePath = "SpriteFalessiIdle.png";
            frameCount = 29;
        }

        final SimpleImage fighterSprite = new SimpleImage(filePath.toString(), false);
        final Animation animation = new SpriteAnimation(fighterSprite, Duration.millis(1000), frameCount);

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.sprite = fighterSprite;
        
        if(this.professor == Professor.MAMMEN) {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
            		new KeyFrame(Duration.ZERO, new KeyValue(this.sprite.translateYProperty(), -88)),
            		new KeyFrame(new Duration(600), new KeyValue(this.sprite.translateYProperty(), -100)),
                    new KeyFrame(new Duration(1200), new KeyValue(this.sprite.translateYProperty(), -88)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
}