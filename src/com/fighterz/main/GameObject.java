package com.fighterz.main;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;

public abstract class GameObject {
	
    public abstract void tick();

    public abstract List<HitBox> getHitBoxes();

    private SimpleImage sprite = new SimpleImage("spriteFalessiIdle.png");

    private double x;
    private double y;
    
    protected double sizeRatio = Window.getHRatio() * 0.8;

    protected abstract void onCollide(HitBox hitBox);

    protected LinkedList<HitBox> hitBoxes;

    public abstract int getFlipped();
    
    public void setSprite(Image image) {
        this.sprite.setImage(image);
        this.sprite.setWidth((int) (image.getWidth() * sizeRatio));
        this.sprite.setHeight((int) (image.getHeight() * sizeRatio));
        this.sprite.setPreserveRatio(true);
    }

    public SimpleImage getSprite() {
        return this.sprite;
    }

    public void addHitBox(HitBox hitBox) {
        this.getHitBoxes().add(hitBox);
        Window.getGame().addHitBox(hitBox);
    }

    public void setX(double x) {
        this.x = x;
        this.sprite.setTranslateX(x);
    }

    public void setY(double y) {
        this.y = y;
        this.sprite.setTranslateY(y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
