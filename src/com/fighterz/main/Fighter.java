package com.fighterz.main;

import java.util.LinkedList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Fighter extends GameObject {

    private static final int SPEED = (int) (10 * Window.getHRatio());

    protected String side;

    protected double originalX;

    protected Animation currentAnimation;

    public Fighter(String side) {
        this.side = side;

        Window.getGame().addFighter(this);
        hitBoxes = new LinkedList<>();
    }

    public void tick() {
        // tick tock
    }

    @Override
    public void onCollide(HitBox hitBox) {
        // Damage the side that isn't us
        Window.getGame().getFightingStage().subtractHealth(hitBox.getDamage(), side == "left" ? "right" : "left");
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

    public List<HitBox> getHitBoxes() {
        return this.hitBoxes;
    }
}