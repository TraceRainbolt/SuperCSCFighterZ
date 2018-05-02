package com.fighterz.main;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Fighter {
    
    private static final int SPEED = 10;

    private Professor professor;
    private SimpleImage sprite;
    
    private HitBox hitBox;

    public Fighter(Professor professor) {
        this.professor = professor;
        this.setAnimation(AnimationState.Idle);
        Game.addFighter(this);
        
        this.hitBox = new HitBox(this.getSprite().getWidth(),
                this.getSprite().getHeight());
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public SimpleImage getSprite() {
        return this.sprite;
    }
    
    public void moveRight() {
        SimpleImage sprite = this.getSprite();
        sprite.setTranslateX(sprite.getTranslateX() + SPEED * Game.getHRatio());
    }
    
    public void moveLeft() {
        SimpleImage sprite = this.getSprite();
        sprite.setTranslateX(sprite.getTranslateX() - SPEED * Game.getHRatio());
    }
    
    public HitBox getHitBox() {
        return this.hitBox;
    }
    
    public void showHitBox() {
        
    }

    // TODO a way to get frameCount from state + name
    public void setAnimation(AnimationState state) {
        StringBuilder filePath = new StringBuilder("Sprite");
       
        filePath.append(this.getProfessor().name());
        filePath.append(state.name());
        filePath.append(".png");
        
        // Remove this once mammen sprites are in
        if(this.getProfessor() == Professor.Mammen)
            filePath = new StringBuilder("SpriteFalessiIdle.png");

        final SimpleImage sprite = new SimpleImage(filePath.toString(), false);
        final int frameCount = 29;
        final Animation animation = new SpriteAnimation(sprite, Duration.millis(1000), frameCount);

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.sprite = sprite;
    }
}