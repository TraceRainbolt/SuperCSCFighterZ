package com.fighterz.main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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

    // nvm its fine
    public void checkHitBoxes() {
        for (GameObject object : objects) {
            if (object.getHitBoxes() != null) {
                for (HitBox hitBox : object.getHitBoxes()) {
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
                otherHitBox.getRect().setFill(hitBoxColor);
                if (hitBox.getRect().getBoundsInParent().intersects(otherHitBox.getRect().getBoundsInParent())) {
                    collisionDetected = true;
                    hits.add(otherHitBox);
                }
            }
        }
        if (collisionDetected) {
            handleCollision(hits, hitBox);
        }
    }

    // Basically we only want to count a hit if we have a HIT box contact a HURT box
    // e. g. we don't want two players (hurt box) touching each other to hurt them
    // both
    // the damaged hash set is so that once a hitbox does damage it stops, so that
    // we don't do damage every frame (which would instantly kill them)
    private void handleCollision(List<HitBox> hits, HitBox hitBox) {
        for (HitBox hit : hits) {
            if (hit.getType() == HitBoxType.HIT && hitBox.getType() == HitBoxType.HURT && !damaged.contains(hit.hashCode())) {
            	hitBox.getObject().onCollide(hit);
                damaged.add(hit.hashCode());
            }
            hit.getRect().setFill(Color.RED);
        }
        hitBox.getRect().setFill(Color.RED);
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
