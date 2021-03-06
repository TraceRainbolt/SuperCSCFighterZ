package com.fighterz.main;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
	
	private static final String RIGHT = "right";
	private static final String LEFT = "left";

	public Fighter(String side) {
		this.side = side;

		hitBoxes = new LinkedList<>();
		energy = 0;
		health = 100;
	}
	
	public void setAnimation(AnimationState state) {
        setupSprite(state);
    }
	
	protected void setIdle(Image idleImage, int count) {
        this.getSprite().setTranslateX(originalX);
        this.setSprite(idleImage);
        currentAnimation = new SpriteAnimation(this.getSprite(), Duration.millis(1000), count);
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
        Window.getGame().setMovementLock(false, side);
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

		boolean rightLock = Window.getGame().getMovementLock(RIGHT);
		boolean leftLock = Window.getGame().getMovementLock(LEFT);

		boolean locked = rightLock || leftLock;

		if (fighterRight.getX() < fighterLeft.getX() && !locked) {
			fighterRight.setFlip(true);
			fighterLeft.setFlip(false);
		} else if (!rightLock && !locked) {
			fighterRight.setFlip(false);
			fighterLeft.setFlip(true);
		}
		
		if(this.energy > 20) {
			Window.getGame().getFightingStage().increaseEnergy(side);
			this.energy = 0;
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
		
		fighterSounds.playTakeDamageSound();
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

	public void teleportBehindYou() {
		if(Window.getGame().getFightingStage().checkEnergy(side, 1))
			Window.getGame().getFightingStage().decreaseEnergy(side);
		else
			return;
		Window.getGame().setMovementLock(true, side);
		FadeTransition fade = new FadeTransition(Duration.millis(300), this.getSprite());
		fade.setFromValue(1.0);
		fade.setToValue(0.0);
		fade.setCycleCount(1);
		fighterSounds.playTeleportSound();
		fade.play();
		fade.setOnFinished(e -> nothingPersonnelKid());
	}

	private void nothingPersonnelKid() {
		Fighter otherFighter = this.side == LEFT ? Window.getGame().getFightingStage().getFighterRight()
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

		if (this.health <= 0)
			playVictory();
	}
	
	private void playVictory() {
		MediaPlayer victoryLine = fighterSounds.getVictorySound();
		
		Fighter fighterRight = Window.getGame().getFighterRight();
		Fighter fighterLeft = Window.getGame().getFighterLeft();
		
		fighterRight.fighterSounds.kill();
		fighterLeft.fighterSounds.kill();
		
		victoryLine.seek(Duration.ZERO);
		victoryLine.play();

	    Window.getGame().nullFighters();

		if(side == LEFT && fighterRight instanceof FighterMammen) {
			playMammenVictory();
		}
		if(side == RIGHT && fighterLeft instanceof FighterMammen) {
			playMammenVictory();
		}
		if(side == LEFT && fighterRight instanceof FighterFalessi) {
			playFalessiVictory();
		}
		if(side == RIGHT && fighterLeft instanceof FighterFalessi) {
			playFalessiVictory();
		}
	}
	
	public void playFalessiVictory() {
		SimpleImage falessiWins = new SimpleImage("FalessiWins.png", true);
		falessiWins.setOpacity(0);

		FadeTransition fade = new FadeTransition(Duration.millis(300), falessiWins);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.setCycleCount(1);
		fade.setDelay(Duration.millis(1000));
		fade.play();
		Window.getGame().getFightingStage().getChildren().add(falessiWins);
		fade.setOnFinished(e -> goMainMenu());
	}
	
	private void goMainMenu() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.severe("Thread inturrupted.");
			Thread.currentThread().interrupt();
		}
		Window.switchScene(Window.getGame().getMainMenu());
	}
	
	public void playMammenVictory() {
		Rectangle blackBackground = new Rectangle(Window.getWidth(), Window.getHeight());
		blackBackground.setFill(Color.BLACK);
		
		File actualFile = new File("Resources/MammenBeamFinisher.mp4");
	    File emptyfile = new File("Resources/empty.mp4");
	    Media media = new Media(emptyfile.toURI().toString());

	    copyData(media, actualFile);
	    MediaPlayer mediaPlayer = null;
	    try {
	        mediaPlayer = new MediaPlayer(media);
		    mediaPlayer.setAutoPlay(true);
	    } catch (Exception e) {
	        logger.severe("Couldn't create media player.");
	    }
	    MediaView mediaView = new MediaView(mediaPlayer);

	    DoubleProperty mvw = mediaView.fitWidthProperty();
	    DoubleProperty mvh = mediaView.fitHeightProperty();
	    
	    mediaView.setPreserveRatio(true);
	    if (mediaPlayer != null) {
		    mediaPlayer.setOnEndOfMedia(() -> Window.switchScene(Window.getGame().getMainMenu()));
	    }
	    
	    Window.getGame().getFightingStage().getChildren().addAll(blackBackground, mediaView);
	    
	    mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
	    mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
	}

	private void copyData(Media media, File f) {
	    try {
	        Field locatorField = media.getClass().getDeclaredField("jfxLocator");
	        
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(locatorField, locatorField.getModifiers() & ~Modifier.FINAL);
            locatorField.setAccessible(true);
            
	        CustomLocator customLocator = new CustomLocator(f.toURI());
	        customLocator.init();
	        customLocator.hack("video/mp4", 100000, f.toURI());
	        locatorField.set(media, customLocator);
	    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | URISyntaxException e) {
	    	logger.severe("Error playing data.");
	    }
	}
	
	@SuppressWarnings("restriction")
	static class CustomLocator extends com.sun.media.jfxmedia.locator.Locator {
	    public CustomLocator(URI uri) throws URISyntaxException {
	        super(uri);
	    }
	
	    @Override
	    public void init() {
	        try {
	            super.init();
	        } catch (Exception e) {
	        	logger.severe("Couldn't create locator.");
	        }
	    }
	
	    public void hack(String type, long length, URI uri){
	        this.contentType = type;
	        this.contentLength = length;
	        this.uri = uri;
	        this.cacheMedia();
	    }
	}

	public void setBlockOn() {
		if(isBlocking)
			isBlocking = false;
		else {
			isBlocking = true;
		}
		
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