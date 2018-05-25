package com.fighterz.main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class FightingStage extends StackPane implements GameScene {

    private Polygon rightHealth;
    private Polygon leftHealth;

    private SimpleImage fullHealthBarRight;
    private SimpleImage fullHealthBarLeft;
    
    private Fighter fighterRight;
    private Fighter fighterLeft;

    public FightingStage() {
        // Empty constructor
    }

    public void render() {
        BackButton backBtn = new BackButton();
        SimpleImage background = new SimpleImage("StageBasketball.jpg", true);

        this.getNodes().add(background);

        setupHealthBars();
        this.getNodes().add(backBtn);
    }
    
    public void setFighterRight(Fighter fighter) {
        fighter.setX(400 * Window.getHRatio());
        this.fighterRight = fighter;
    }
    
    public void setFighterLeft(Fighter fighter) {
        fighter.setX(-400 * Window.getHRatio());
        this.fighterLeft = fighter;
    } 
    
    public Fighter getFighterRight() {
        return fighterRight;
    }
    
    public Fighter getFighterLeft() {
        return fighterLeft;
    }

    public void subtractHealth(double amount, String side) {
        if (side.equalsIgnoreCase("right"))
            subtractRightHealth(amount);
        if (side.equalsIgnoreCase("left"))
            subtractLeftHealth(amount);
    }

    private void subtractRightHealth(double amount) {
        double percent = amount / 100 * fullHealthBarRight.getWidth();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
            new KeyValue(rightHealth.translateXProperty(), rightHealth.getTranslateX())),
            new KeyFrame(new Duration(400),
            new KeyValue(rightHealth.translateXProperty(), rightHealth.getTranslateX() + percent)));
        timeline.setCycleCount(1);
        timeline.play();

        fullHealthBarRight.setClip(rightHealth);
    }

    private void subtractLeftHealth(double amount) {
        double percent = amount / 100 * fullHealthBarLeft.getWidth();

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(leftHealth.translateXProperty(), leftHealth.getTranslateX())),
                new KeyFrame(new Duration(400),
                        new KeyValue(leftHealth.translateXProperty(), leftHealth.getTranslateX() + percent)));
        timeline.setCycleCount(1);
        timeline.play();

        fullHealthBarLeft.setClip(leftHealth);
    }

    public void setupHealthBars() {
        SimpleImage healthBarRight = new SimpleImage("EmptyHealthbar.png", false);
        healthBarRight.setTranslateY(-480 * Window.getHRatio());
        healthBarRight.setTranslateX(600 * Window.getHRatio());

        fullHealthBarRight = new SimpleImage("FullHealthBar.png", false);
        fullHealthBarRight.setTranslateY(-480 * Window.getHRatio());
        fullHealthBarRight.setTranslateX(600 * Window.getHRatio());

        // This is so we get the slant on the health bar
        rightHealth = new Polygon();
        rightHealth.getPoints()
                .addAll(new Double[] { 0.0, 0.0, (double) fullHealthBarRight.getWidth(), 0.0,
                        (double) fullHealthBarRight.getWidth(), (double) fullHealthBarRight.getHeight(),
                        -39.0 * Window.getHRatio(), (double) fullHealthBarRight.getHeight() });

        fullHealthBarRight.setClip(rightHealth);

        SimpleImage healthBarLeft = new SimpleImage("EmptyHealthbar.png", false);
        healthBarLeft.setTranslateY(-480 * Window.getHRatio());
        healthBarLeft.setTranslateX(-600 * Window.getHRatio());
        healthBarLeft.setScaleX(-1);

        fullHealthBarLeft = new SimpleImage("FullHealthBar.png", false);
        fullHealthBarLeft.setTranslateY(-480 * Window.getHRatio());
        fullHealthBarLeft.setTranslateX(-600 * Window.getHRatio());
        fullHealthBarLeft.setScaleX(-1);

        leftHealth = new Polygon();
        leftHealth.getPoints()
                .addAll(new Double[] { 0.0, 0.0, (double) fullHealthBarLeft.getWidth(), 0.0,
                        (double) fullHealthBarLeft.getWidth(), (double) fullHealthBarLeft.getHeight(),
                        -39.0 * Window.getHRatio(), (double) fullHealthBarLeft.getHeight() });

        fullHealthBarLeft.setClip(leftHealth);

        this.getNodes().addAll(healthBarRight, healthBarLeft, fullHealthBarRight, fullHealthBarLeft);
    }

    public ObservableList<Node> getNodes() {
        return this.getChildren();
    }

}
