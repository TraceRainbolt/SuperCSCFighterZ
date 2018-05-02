package com.fighterz.main;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Fighter {
	
	private Professor professor;
	private ImageView sprite;
	
	public Fighter(Professor professor) {
    	this.professor = professor;
    	this.setAnimation(AnimationState.Idle);
	}
	
	public String getProfessor() {
		return this.professor.name();
	}
	
	public ImageView getSprite() {
		return this.sprite;
	}
	
	public void setAnimation(AnimationState state) {
		StringBuilder filePath = new StringBuilder("Sprite");
		filePath.append(this.getProfessor());
		filePath.append(state.name());
		filePath.append(".png");

        final SimpleImage sprite = new SimpleImage(filePath.toString(), false);
        final int frameCount =  29;
        final Animation animation = new SpriteAnimation(
        		sprite,
                Duration.millis(1000),
                frameCount
        );
        
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.sprite = sprite;
	}
}