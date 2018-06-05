package com.fighterz.main;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FighterFalessi extends Fighter {

    private static final Image powerBallImage = new Image("SpriteFalessiPowerBall.png", false);
    private static final Image idleImage = new Image("SpriteFalessiIdle.png", false);
    private static final Image weakBallImage = new Image("SpriteFalessiWeakBall.png", false);

    public FighterFalessi(String side) {
        super(side);
        this.setAnimation(AnimationState.IDLE);

        HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 240, 750, 20, 0);
        this.addHitBox(hurtBox);
        
        setupFighterSounds("Falessi");
    }

    @Override
	protected void setupSprite(AnimationState state) {
        if (state == AnimationState.NORMAL_MOVE) {
            if (currentAnimation != null) {
                Window.getGame().setMovementLock(true, side);
                originalX = this.getX();

                // If we don't do the jumpTo then the animation will flash to the wrong frames
                // I don't really know why this fixes it but it does
                currentAnimation.jumpTo(Duration.millis(2000));
                currentAnimation.pause();

                this.setSprite(idleImage);
                setWeakBall();
            }
        } else if(state == AnimationState.POWER_MOVE) {
            if (currentAnimation != null) {
            	if(Window.getGame().getFightingStage().checkEnergy(side, 2)) {
            		Window.getGame().getFightingStage().decreaseEnergy(side);
            		Window.getGame().getFightingStage().decreaseEnergy(side);
            	} else {
            		return;
            	}
            	
                Window.getGame().setMovementLock(true, side);
                originalX = this.getX();

                // Gotta do the jumpTo here as well
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
    
    private void setWeakBall() {
        HitBox hitBox = createWeakBallHitbox();
        this.addHitBox(hitBox);
        this.getSprite().toFront();
        this.setSprite(weakBallImage);
        this.getSprite().setX(this.getSprite().getX() + 100);
        currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29,
                (int) (375 * this.sizeRatio * this.getFlipped()));
        
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
        currentAnimation.setOnFinished(e -> setIdle(idleImage, 29));
        
        // Play ranged attack sound
        fighterSounds.playRangedSound();
    }

    private void setPowerBall() {
        HitBox hitBox = createPowerBallHitbox();
        this.addHitBox(hitBox);
        this.getSprite().toFront();
        this.setSprite(powerBallImage);
        this.getSprite().setX(this.getSprite().getX() + 100);
        currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29,
                (int) (375 * this.sizeRatio * this.getFlipped()));
        
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
        currentAnimation.setOnFinished(e -> setIdle(idleImage, 29));
        
        // Play ranged attack sound
        fighterSounds.playRangedSound();
    }
    
    private HitBox createWeakBallHitbox() {
        HitBox hitBox = new HitBox(this, HitBoxType.HIT, 100, 100, 100, -150);
        hitBox.setDamage(12);
        hitBox.setIndependent(true);
        hitBox.setDelay(0.52);
        hitBox.setXVelocity(-30 * this.sizeRatio);
        hitBox.setMaxDuration(1.23);
        return hitBox;
    }

    // Sets all the params for the power ball hit box. Trial and error was used.
    private HitBox createPowerBallHitbox() {
        HitBox hitBox = new HitBox(this, HitBoxType.HIT, 100, 100, 100, -150);
        hitBox.setDamage(34);
        hitBox.setIndependent(true);
        hitBox.setDelay(0.52);
        hitBox.setXVelocity(-30 * this.sizeRatio);
        hitBox.setMaxDuration(1.23);
        return hitBox;
    }


}
