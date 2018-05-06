package com.fighterz.main;

import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Handler {
	LinkedList<GameObject> objects = new LinkedList<>();
	LinkedList<GameScene> scenes = new LinkedList<>();
	LinkedList<HitBox> hitBoxes = new LinkedList<>();

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
		for(HitBox hitBox : hitBoxes) {
			checkBounds(hitBox);
		}
	}
	
	public void checkBounds(HitBox block) {
		boolean collisionDetected = false;
		HitBox hit = null;
		for (HitBox hitBox : hitBoxes) {
			if (hitBox != block) {
				hitBox.getRect().setStroke(Color.GREEN);
				if (block.getRect().getBoundsInParent().intersects(
						hitBox.getRect().getBoundsInParent())) {
					collisionDetected = true;
					hit = hitBox;
				}
			}
		}
		if (collisionDetected) {
			block.getRect().setStroke(Color.RED);
			hit.getRect().setStroke(Color.RED);
		}
	}

	public void addScene(GameScene scene) {
		if(!this.scenes.contains(scene)) {
			this.scenes.add(scene);
			scene.render();
		}
	}
}
