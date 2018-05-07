package com.fighterz.main;

import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Fighter extends GameObject {
    
    private static final int SPEED = (int) (10 * Window.getHRatio());

    private Professor professor;
    
    private LinkedList<HitBox> hitBoxes;
    private String side;

    public Fighter(Professor professor, String side) {
        this.professor = professor;
        this.setAnimation(AnimationState.IDLE);
        Window.getGame().addFighter(this);
        this.side = side;
        
        hitBoxes = new LinkedList<HitBox>();
        
        if(this.professor == Professor.MAMMEN) {
        	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 290, 510, 0, -65);
            HitBox hitBox = new HitBox(this, HitBoxType.HIT, 220, 190, 120, -95);
        	hitBoxes.add(hurtBox);
            hitBoxes.add(hitBox);
        } else {
        	HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 250, 830);
        	HitBox hitBox = new HitBox(this, HitBoxType.HIT, 70, 56, -120, -75);
        	hitBoxes.add(hurtBox);
        	hitBoxes.add(hitBox);
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