package com.fighterz.main;

import java.util.HashSet;
import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Handler {
    LinkedList<GameObject> objects = new LinkedList<>();
    LinkedList<GameScene> scenes = new LinkedList<>();
    LinkedList<HitBox> hitBoxes = new LinkedList<>();
    HashSet<Integer> damaged = new HashSet<>();

    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
            checkHitBoxes();
        }
    }

    public void addObjects(GameObject... objects) {
        for (GameObject object : objects)
            this.objects.add(object);
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }

    public void addHitBox(HitBox hitBox) {
        this.hitBoxes.add(hitBox);
    }

    // TODO make this not O(n^2), probably can get O(nlogn)
    public void checkHitBoxes() {
        for (GameObject object : objects) {
            if(object.getHitBoxes() != null) {
                for(HitBox hitBox : object.getHitBoxes()) {
                    hitBox.tick();
                    checkBounds(hitBox);
                }
            }
        }
    }

    public void checkBounds(HitBox hitBox) {
        boolean collisionDetected = false;
        LinkedList<HitBox> hits = new LinkedList<>();
        
        for (HitBox otherHitBox : hitBoxes) {
            if (otherHitBox.getObject() != hitBox.getObject()) {
                Color hitBoxColor = otherHitBox.getType() == HitBoxType.HURT ? Color.GREEN : Color.BLUE;
                otherHitBox.getRect().setStroke(hitBoxColor);
                if (hitBox.getRect().getBoundsInParent().intersects(otherHitBox.getRect().getBoundsInParent())) {
                    collisionDetected = true;
                    hits.add(otherHitBox);
                }
            }
        }
        if (collisionDetected) {
            for(HitBox hit : hits) {
                if(hit.getType() == HitBoxType.HIT && hitBox.getType() == HitBoxType.HURT && !damaged.contains(hit.hashCode())) {
                    hitBox.getObject().onCollide(hit.getObject());
                    damaged.add(hit.hashCode());
                }
                hit.getRect().setStroke(Color.RED);
            }
            hitBox.getRect().setStroke(Color.RED);
        }
    }
    
    public void removeHitBox(HitBox hitBox) {
    	this.hitBoxes.remove(hitBox);
    }

    public void switchScene(GameScene scene) {
        this.hitBoxes.clear();
        scene.getNodes().clear();
        this.objects.clear();
        if (!this.scenes.contains(scene)) {
            this.scenes.add(scene);
        }
        scene.render();
    }
}
