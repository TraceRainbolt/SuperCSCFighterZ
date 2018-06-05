package com.fighterz.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FighterFalessi extends Fighter {
	private static final Logger logger = Logger.getLogger(FighterFalessi.class.getName());

    private static final Image powerBallImage = new Image("SpriteFalessiPowerBall.png", false);
    private static final Image idleImage = new Image("SpriteFalessiIdle.png", false);

    public FighterFalessi(String side) {
        super(side);
        this.setAnimation(AnimationState.IDLE);

        HitBox hurtBox = new HitBox(this, HitBoxType.HURT, 240, 750, 20, 0);
        this.addHitBox(hurtBox);
        
        setupFighterSounds("Falessi");
    }

    @Override
	protected void setupSprite(AnimationState state) {
        if (state == AnimationState.POWER_MOVE) {
            if (currentAnimation != null) {
                Window.getGame().setMovementLock(true, side);
                originalX = this.getX();

                // If we don't do the jumpTo then the animation will flash to the wrong frames
                // I don't really know why this fixes it but it does
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
        this.addHitBox(hitBox);
        this.getSprite().toFront();
        this.setSprite(powerBallImage);
        this.getSprite().setX(this.getSprite().getX() + 100);
        currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29,
                (int) (375 * this.sizeRatio * this.getFlipped()));
        
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
        currentAnimation.setOnFinished(e -> setIdle());
        
        // Play ranged attack sound
        fighterSounds.playRangedSound();
    }

    // Sets all the params for the power ball hit box. Trial and error was used.
    private HitBox createPowerBallHitbox() {
        HitBox hitBox = new HitBox(this, HitBoxType.HIT, 100, 100, 100, -150);
        if(this.poweredUp) {
        	hitBox.setDamage(30);
        	this.poweredUp = false;
        	this.energy -= 100;
        }
        else {
        	hitBox.setDamage(10);
        }
        hitBox.setIndependent(true);
        hitBox.setDelay(0.7);
        hitBox.setXVelocity(-30 * this.sizeRatio);
        hitBox.setMaxDuration(1.23);
        return hitBox;
    }

    private void setIdle() {
        this.getSprite().setTranslateX(originalX);
        this.setSprite(idleImage);
        currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), 29);
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
        Window.getGame().setMovementLock(false, side);
    }
    
    @Override
    public void onCollide(HitBox hitBox) {
        super.onCollide(hitBox);
        logger.log(Level.INFO, "Falessi Energy = {0}", energy);
        
        // Play takes damage sound
        fighterSounds.playTakeDamageSound();
    }
    
    @Override
    public void subtractHealth(double amount) {
    	super.subtractHealth(amount);
    	if(this.health <= 0) {
    		logger.log(Level.INFO, "Falessi is dead!");
    	}
    }

}
