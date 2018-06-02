package com.fighterz.main;

public class Camera {
	
	private Fighter left;
	private Fighter right;
	

	public void updateView() {
		left = Window.getGame().getFightingStage().getFighterLeft();
		right = Window.getGame().getFightingStage().getFighterRight();
		
		double lowX = -Window.getWidth() / 2 + 110 * Window.getHRatio();
		double highX = -lowX;

		checkBounds(left, lowX, highX);
		checkBounds(right, lowX, highX);
	}
	
	private void checkBounds(Fighter fighter, double lowX, double highX) {
		double fighterX = fighter.getX();
		
		if(fighterX < lowX) {
			fighter.setX(lowX);
		} else if(fighterX > highX) {
			fighter.setX(highX);
		}
	}
}
