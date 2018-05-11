package com.fighterz.main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitBox {

    private Rectangle hitBoxRect;
    private GameObject object;

    private HitBoxType type;
    private double offsetX = 0;
    private double offsetY = 0;
    private double xVelocity = 0;
    private boolean independent = false;
    private double damage = 0;

    private boolean lock = false;
    private int delay = 0;
    private int duration = 0;
    private int maxDuration = -1;

    public HitBox(GameObject object, HitBoxType type, double width, double height) {
        this.object = object;
        this.hitBoxRect = new Rectangle(0, 0, width * Window.getHRatio(), height * Window.getHRatio());
        this.type = type;

        // Super jank but it works. Basically the rectangle is always instantiated at 0,
        // 0
        // This means that it will briefly contact any hitboxes at 0, 0, which is bad.
        // So we translate it down before hitBox detection is activated.
        // In the tick method we are brought to our correct location.
        this.hitBoxRect.setTranslateY(-10000);

        setColorProperties();
    }

    public HitBox(GameObject object, HitBoxType type, double width, double height, double offsetX, double offsetY) {
        this(object, type, width, height);
        this.offsetX = offsetX * Window.getHRatio();
        this.offsetY = offsetY * Window.getHRatio();
    }

    private void setColorProperties() {
        this.hitBoxRect.setFill(Color.GREEN);
        // Show hit boxes if in debug mode
        double opacity = Window.DEBUG ? 0.3 : 0;
        this.hitBoxRect.setOpacity(opacity);
    }

    public void tick() {
        this.hitBoxRect.toFront();
        if (delay <= 0) {
            updateHitboxLocation();
        } else {
            // Lazy af way to make sure hitbox doesnt appear until time
            this.hitBoxRect.setTranslateY(100000);
        }

        if (duration == maxDuration + delay) {
            this.markForDeletion();
        }

        delay--;
        if (delay < 0)
            delay = 0;
        duration++;
    }

    public GameObject getObject() {
        return object;
    }

    public void setXVelocity(double velocity) {
        this.xVelocity = velocity;
    }

    // HitBox will not follow the player
    public void setIndependent(boolean independent) {
        this.independent = independent;
    }

    public void setMaxDuration(double d) {
        // Times 60 for 1 duration = 1 frame
        this.maxDuration = (int) (d * 60);
    }

    public void setDelay(double delay) {
        // Delay is in frames, so times 60
        this.delay = (int) (delay * 60);
    }
    
    public double getDamage() {
        return damage;
    }
    
    public void setDamage(double damage) {
        this.damage = damage;
    }

    private void updateHitboxLocation() {
        if (!this.independent) {
            this.hitBoxRect.setTranslateX(this.object.getX() + offsetX);
            this.hitBoxRect.setTranslateY(this.object.getY() + offsetY);
        } else {
            if (!lock) {
                this.hitBoxRect.setTranslateX(this.object.getX() + offsetX);
                this.hitBoxRect.setTranslateY(this.object.getY() + offsetY);
                lock = true;
            } else {
                this.hitBoxRect.setTranslateX(this.hitBoxRect.getTranslateX() + xVelocity);
            }
        }
    }

    public HitBoxType getType() {
        return this.type;
    }

    private void markForDeletion() {
        Window.getGame().getHandler().removeHitBox(this);
        this.getObject().getHitBoxes().remove(this);
        Window.getGameScene().getNodes().remove(this.getRect());
    }

    // get rekt
    public Rectangle getRect() {
        return this.hitBoxRect;
    }
}
