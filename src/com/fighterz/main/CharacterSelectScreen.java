package com.fighterz.main;

import java.util.Set;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class CharacterSelectScreen extends StackPane implements GameScene {

	private SimpleImage redSelect;
	private SimpleImage blueSelect;

	private SimpleImage falessiCharRed;
	private SimpleImage mammenCharRed;

	private SimpleImage falessiCharBlue;
	private SimpleImage mammenCharBlue;

	private SimpleImage falessiIcon;
	private SimpleImage mammenIcon;

	private SimpleImage redSelected;
	private SimpleImage blueSelected;

	public CharacterSelectScreen() {
		// Empty constructor
	}

	public ObservableList<Node> getNodes() {
		return this.getChildren();
	}

	@Override
	public void render() {
		setupCharSelect();
	}

	private void setupCharSelect() {

		SimpleImage background = new SimpleImage("CharacterSelectBG.png", true);

		SimpleImage blackBarLeft = new SimpleImage("CharacterSelectBlackBar.png", false);
		SimpleImage blackBarRight = new SimpleImage("CharacterSelectBlackBar.png", false);
		blackBarRight.setScaleX(-1);
		blackBarRight.setTranslateX(-42 * Window.getHRatio());

		redSelect = new SimpleImage("CharacterSelectMarkerRed.png", false);
		blueSelect = new SimpleImage("CharacterSelectMarkerBlue.png", false);
		redSelect.setTranslateX(-50 * Window.getHRatio());
		blueSelect.setTranslateX(12 * Window.getHRatio());

		falessiCharRed = new SimpleImage("CharacterSelectFalessi.png", false);
		mammenCharRed = new SimpleImage("CharacterSelectMammen.png", false);
		mammenCharRed.setScaleX(-1);
		mammenCharRed.setTranslateX(-40 * Window.getHRatio());

		falessiCharBlue = new SimpleImage("CharacterSelectFalessi.png", false);
		mammenCharBlue = new SimpleImage("CharacterSelectMammen.png", false);
		falessiCharBlue.setScaleX(-1);
		falessiCharBlue.setTranslateX(-40 * Window.getHRatio());

		falessiIcon = new SimpleImage("CharacterSelectFalessiIcon.png", false);
		mammenIcon = new SimpleImage("CharacterSelectMammenIcon.png", false);

		BackButton backBtn = new BackButton(Window.getGame().getMainMenu());

		this.getChildren().addAll(falessiCharRed, mammenCharRed, falessiCharBlue, mammenCharBlue, background,
				blackBarLeft, blackBarRight, blueSelect, redSelect, falessiIcon, mammenIcon, backBtn);
	}

	public void handleKeys(Set<KeyCode> pressedKeys) {
		boolean switched = false;

		if (pressedKeys.contains(KeyCode.A)) {
			redSelect.moveTo(-290, 290);
			redSelected = falessiCharRed;
			blueSelect.toFront();
			redSelect.toFront();
			switched = true;
		}

		if (pressedKeys.contains(KeyCode.D)) {
			redSelect.moveTo(290, 290);
			redSelect.toFront();
			redSelected = mammenCharRed;
			switched = true;
		}

		if (pressedKeys.contains(KeyCode.J)) {
			blueSelect.moveTo(-330, 290);
			falessiIcon.toFront();
			redSelect.toFront();
			blueSelect.toFront();
			blueSelected = falessiCharBlue;
			switched = true;
		}

		if (pressedKeys.contains(KeyCode.L)) {
			blueSelect.moveTo(250, 290);
			blueSelect.toFront();
			blueSelected = mammenCharBlue;
			switched = true;
		}

		if (pressedKeys.contains(KeyCode.W)) {
			redSelected = null;
			mammenCharRed.toBack();
			falessiCharRed.toBack();
			redSelect.moveTo(-50 * Window.getHRatio(), 0);
		}

		if (pressedKeys.contains(KeyCode.I)) {
			blueSelected = null;
			mammenCharBlue.toBack();
			falessiCharBlue.toBack();
			blueSelect.moveTo(12 * Window.getHRatio(), 0);
		}

		if (pressedKeys.contains(KeyCode.ENTER) && redSelected != null && blueSelected != null) {
			createSleeperThread();
		}

		if (switched) {
			didSwitch();
		}
	}

	public void createSleeperThread() {
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Window.switchScene(new LoadingScreen());
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// No more code smell with this comment added?
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				Window.switchScene(Window.getGame().getFightingStage());
				if (redSelected == falessiCharRed) {
					Window.getGame().addFighter(new FighterFalessi("left"));
				} else {
					Window.getGame().addFighter(new FighterMammen("left"));
				}
				if (blueSelected == falessiCharBlue) {
					Window.getGame().addFighter(new FighterFalessi("right"));
				} else {
					Window.getGame().addFighter(new FighterMammen("right"));
				}
			}
		});
		new Thread(sleeper).start();
	}

	public void didSwitch() {
		if (redSelected == falessiCharRed) {
			falessiCharRed.toFront();
			mammenCharRed.toBack();
		}

		if (blueSelected == falessiCharBlue) {
			falessiCharBlue.toFront();
			mammenCharBlue.toBack();
		}

		if (redSelected == mammenCharRed) {
			mammenCharRed.toFront();
			falessiCharRed.toBack();
		}

		if (blueSelected == mammenCharBlue) {
			mammenCharBlue.toFront();
			falessiCharBlue.toBack();
		}

		falessiIcon.toFront();
		mammenIcon.toFront();
		redSelect.toFront();
		blueSelect.toFront();
	}
}
