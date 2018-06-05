package com.fighterz.main;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Fighter extends GameObject {
	private static final Logger logger = Logger.getLogger(Fighter.class.getName());

	FighterSounds fighterSounds;

	private static final int SPEED = (int) (10 * Window.getHRatio());

	protected String side;

	protected double originalX;

	protected Animation currentAnimation;

	private boolean flipped = false;

	protected int energy;

	protected boolean poweredUp;

	// this value is checked in FightingStage.subtractHealth()
	protected boolean isBlocking;

	protected double health;
	
	protected abstract void setupSprite(AnimationState state);

	public Fighter(String side) {
		this.side = side;

		hitBoxes = new LinkedList<>();
		energy = 0;
		health = 100;
	}
	
	public void setAnimation(AnimationState state) {
        setupSprite(state);
    }
	
	public void setupFighterSounds(String fighterName) {
		try {
			fighterSounds = new FighterSounds(fighterName);
		} catch (NoSuchFighterException e) {
			logger.severe("Unable to create FighterSounds for " + fighterName);
			System.exit(1);
		}
	}

	public void tick() {
		Fighter fighterRight = Window.getGame().getFightingStage().getFighterRight();
		Fighter fighterLeft = Window.getGame().getFightingStage().getFighterLeft();

		boolean rightLock = Window.getGame().getMovementLock("right");
		boolean leftLock = Window.getGame().getMovementLock("left");

		boolean locked = rightLock || leftLock;

		if (fighterRight.getX() < fighterLeft.getX() && !locked) {
			fighterRight.setFlip(true);
			fighterLeft.setFlip(false);
		} else if (!rightLock && !locked) {
			fighterRight.setFlip(false);
			fighterLeft.setFlip(true);
		}
	}

	@Override
	public void onCollide(HitBox hitBox) {
		// Damage the side that isn't us
		// this if makes unit testing for these values possible without needed to create
		// hitboxes
		if (hitBox != null) {
			Window.getGame().getFightingStage().subtractHealth(hitBox.getDamage(), side);
		} else if (!isBlocking) {
			this.subtractHealth(10);
		}
		energy += 10;
	}

	public FighterSounds getFighterSounds() // use for integration testing to access the class
	{
		return fighterSounds;
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
		timeline.getKeyFrames()
				.addAll(new KeyFrame(Duration.ZERO,
						new KeyValue(this.getSprite().translateYProperty(), 0 * Window.getHRatio())),
						new KeyFrame(new Duration(125),
								new KeyValue(this.getSprite().translateYProperty(), -190 * Window.getHRatio())),
						new KeyFrame(new Duration(200),
								new KeyValue(this.getSprite().translateYProperty(), -250 * Window.getHRatio())),
						new KeyFrame(new Duration(275),
								new KeyValue(this.getSprite().translateYProperty(), -190 * Window.getHRatio())),
						new KeyFrame(new Duration(400),
								new KeyValue(this.getSprite().translateYProperty(), 0 * Window.getHRatio())));
		timeline.setCycleCount(1);
		timeline.play();
	}

	public void teleportBehindYou() {
		Window.getGame().setMovementLock(true, side);
		FadeTransition fade = new FadeTransition(Duration.millis(300), this.getSprite());
		fade.setFromValue(1.0);
		fade.setToValue(0.0);
		fade.setCycleCount(1);
		fighterSounds.playTeleportSound();
		fade.play();
		fade.setOnFinished(e -> nothingPersonnelKid());
	}

	// TODO get other fighter instead of left
	private void nothingPersonnelKid() {
		Fighter otherFighter = this.side == "left" ? Window.getGame().getFightingStage().getFighterRight()
				: Window.getGame().getFightingStage().getFighterLeft();
		double x = otherFighter.getX();
		this.setX(x - 230 * this.getFlipped() * Window.getHRatio());
		this.setFlip(this.getFlipped() == 1 ? true : false);
		FadeTransition fade = new FadeTransition(Duration.millis(300), this.getSprite());
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.setCycleCount(1);
		fade.play();
		fade.setOnFinished(e -> Window.getGame().setMovementLock(false, side));
	}

	public List<HitBox> getHitBoxes() {
		return this.hitBoxes;
	}

	public void setPoweredUp() {
		if (energy >= 100) {
			poweredUp = true;
		} else {
			logger.log(Level.INFO, "Not enough energy!");
		}
	}

	public void subtractHealth(double amount) {
		health -= amount;
	}

	public void setBlockOn() {
		isBlocking = true;
	}

	public boolean getBlock() {
		return isBlocking;
	}

	public int getEnergy() {
		return energy;
	}

	public double getHealth() {
		return health;
	}

}