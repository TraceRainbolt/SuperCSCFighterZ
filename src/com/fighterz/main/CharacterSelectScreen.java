package com.fighterz.main;

import java.util.HashSet;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class CharacterSelectScreen extends StackPane implements GameScene {
	
	private SimpleImage redSelect;
	private SimpleImage blueSelect;
	
	private SimpleImage falessiChar;
	private SimpleImage mammenChar;
	
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
        blackBarRight.setTranslateX(-28);
        
        redSelect = new SimpleImage("CharacterSelectMarkerRed.png", false);
        blueSelect = new SimpleImage("CharacterSelectMarkerBlue.png", false);
        redSelect.setTranslateX(-50 * Window.getHRatio());
        blueSelect.setTranslateX(12 * Window.getHRatio());
    
        falessiChar = new SimpleImage("CharacterSelectFalessi.png", false);
        mammenChar = new SimpleImage("CharacterSelectMammen.png", false);
        
        falessiIcon = new SimpleImage("CharacterSelectFalessiIcon.png", false);
        mammenIcon = new SimpleImage("CharacterSelectMammenIcon.png", false);

        BackButton backBtn = new BackButton();
        this.getChildren().addAll(falessiChar, mammenChar, background, blackBarLeft, blackBarRight, 
        		blueSelect, redSelect, falessiIcon, mammenIcon, backBtn);
    }

	public void handleKeys(HashSet<KeyCode> pressedKeys) {
		boolean switched = false;
		
		if(pressedKeys.contains(KeyCode.A)) {
			redSelect.moveTo(-290, 290);
			redSelected = falessiChar;
			blueSelect.toFront();
			redSelect.toFront();
			switched = true;
		}
		
		if(pressedKeys.contains(KeyCode.D)) {
			redSelect.moveTo(290, 290);
			redSelect.toFront();
			redSelected = mammenChar;
			switched = true;
		}
		
		if(pressedKeys.contains(KeyCode.J)) {
			blueSelect.moveTo(-330, 290);
			falessiIcon.toFront();
			redSelect.toFront();
			blueSelect.toFront();
			blueSelected = falessiChar;
			switched = true;
		}
		
		if(pressedKeys.contains(KeyCode.L)) {
			blueSelect.moveTo(250, 290);
			blueSelect.toFront();
			blueSelected = mammenChar;
			switched = true;
		}
		
		if(switched) {
			if(redSelected != null) {
				redSelected.toFront();
			}
			if(blueSelected != null) {
				blueSelected.toFront();
			}
			falessiIcon.toFront();
			redSelect.toFront();
			blueSelect.toFront();
			
			if(redSelected != falessiChar && blueSelected != falessiChar) {
				falessiChar.toBack();
			}
			if(redSelected != mammenChar && blueSelected != mammenChar) {
				mammenChar.toBack();
			}
		}
	}
}
