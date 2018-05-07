package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Fighter extends GameObject {
    
    private static final int SPEED = (int) (10 * Window.getHRatio());

    private Professor professor;
    
    private HitBox hitBox;

    public Fighter(Professor professor) {
        this.professor = professor;
        this.setAnimation(AnimationState.IDLE);
        Window.getGame().addFighter(this);
        
        if(this.professor == Professor.MAMMEN) {
        	this.hitBox = new HitBox(this, 290, 510, 0, -100);
        } else {
        	this.hitBox = new HitBox(this, 250, 830);
        }
    }
    
    public void tick() {
        // Tick tock
    }
    
	@Override
	public void onCollide(GameObject collider) {
		Window.getGame().getFightingStage().subtractLeftHealth(0.03);
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
    
    public HitBox getHitBox() {
        return this.hitBox;
    }

    // TODO a way to get frameCount from state + name
    public void setAnimation(AnimationState state) {
    	String filePath;
    	int frameCount;
        if(this.professor == Professor.MAMMEN && state == AnimationState.IDLE) {
            filePath = "SpriteMammenIdle.png";
            frameCount = 27;
            
        } else {
            filePath = "SpriteFalessiIdle.png";
            frameCount = 29;
        }

        final SimpleImage fighterSprite = new SimpleImage(filePath, false);
        final Animation animation = new SpriteAnimation(fighterSprite, Duration.millis(1000), frameCount);

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.setSprite(fighterSprite);
        
        if(this.professor == Professor.MAMMEN) {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
            		new KeyFrame(Duration.ZERO, new KeyValue(this.getSprite().translateYProperty(), -88 * Window.getHRatio())),
            		new KeyFrame(new Duration(600), new KeyValue(this.getSprite().translateYProperty(), -100 * Window.getHRatio())),
                    new KeyFrame(new Duration(1200), new KeyValue(this.getSprite().translateYProperty(), -88 * Window.getHRatio())));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
}