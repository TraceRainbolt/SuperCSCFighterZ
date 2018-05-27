package com.fighterz.main;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Fighter extends GameObject {

	FighterSounds fighterSounds;
	
    private static final int SPEED = (int) (10 * Window.getHRatio());

    protected String side;

    protected double originalX;

    protected Animation currentAnimation;
    
    private boolean flipped = false;
    
    protected int energy;
    
    public abstract void teleportBehindYou();

    public abstract void setAnimation(AnimationState state);

    public Fighter(String side) {
        this.side = side;

        hitBoxes = new LinkedList<>();
        energy = 0;
    }

    public void tick() {
        Fighter fighterRight = Window.getGame().getFightingStage().getFighterRight();
        Fighter fighterLeft = Window.getGame().getFightingStage().getFighterLeft();
        
        boolean rightLock = Window.getGame().getMovementLock("right");
        boolean leftLock = Window.getGame().getMovementLock("left");
        
        boolean locked = rightLock || leftLock;
        
        if(fighterRight.getX() < fighterLeft.getX() && !locked) {
            fighterRight.setFlip(true);
            fighterLeft.setFlip(false);
        } else if(!rightLock && !locked) {
            fighterRight.setFlip(false);
            fighterLeft.setFlip(true);
        }
    }

    @Override
    public void onCollide(HitBox hitBox) {
        // Damage the side that isn't us
        Window.getGame().getFightingStage().subtractHealth(hitBox.getDamage(), side);
        energy += 10;
    }
    
    public String getSide() {
        return this.side;
    }
    
    public void setFlip(boolean flip) {
        this.getSprite().setScaleX(flip ? -1 : 1);
        this.flipped = flip;
    }
    
    public int getFlipped() {
        return this.flipped ? -1 : 1;
    }

    public void moveRight() {
        this.setX(this.getX() + SPEED);
    }

    public void moveLeft() {
        this.setX(this.getX() - SPEED);
    }

    public void jump() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
            new KeyValue(this.getSprite().translateYProperty(), 0 * Window.getHRatio())),
            new KeyFrame(new Duration(125), new KeyValue(this.getSprite().translateYProperty(), -190 * Window.getHRatio())),
            new KeyFrame(new Duration(200), new KeyValue(this.getSprite().translateYProperty(), -250 * Window.getHRatio())),
            new KeyFrame(new Duration(275), new KeyValue(this.getSprite().translateYProperty(), -190 * Window.getHRatio())),
            new KeyFrame(new Duration(400), new KeyValue(this.getSprite().translateYProperty(), 0 * Window.getHRatio())));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public List<HitBox> getHitBoxes() {
        return this.hitBoxes;
    }

}