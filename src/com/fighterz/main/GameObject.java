package com.fighterz.main;

import javafx.scene.Node;

public abstract class GameObject {
	public abstract void tick();
	public abstract Node getSprite();
	public abstract double getX();
	public abstract double getY();
	public abstract HitBox getHitBox();
	public abstract void onCollide(GameObject collider);
}
