package com.fighterz.main;

import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Game {
    
    private Handler handler;
    
    private HashSet<KeyCode> pressedKeys;
    
    private Fighter fighterFalessi;
    private Fighter fighterMammen;
    
    private CharacterSelectScreen charSelectScreen;
    private FightingStage fightingStage;
    private OptionsMenu optionsMenu;
    private MainMenu mainMenu;
    
    private Scene scene;
    private Stage pStage;
    
    public Game() {
    	this.handler = new Handler();
    	this.pressedKeys = new HashSet<>();
    	initUI();
    }
    
    private void initUI() {
    	this.charSelectScreen = new CharacterSelectScreen();
    	this.fightingStage = new FightingStage(Professor.FALESSI, Professor.MAMMEN);
    	this.optionsMenu = new OptionsMenu();
    	this.mainMenu = new MainMenu(charSelectScreen, fightingStage, optionsMenu);
    	
        this.scene = new Scene(mainMenu);
        Window.initCurrentScene(mainMenu);
        handler.switchScene(mainMenu);

        pressedKeys = new HashSet<>();
        this.scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        this.scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }
    
    public void update() {
    	handler.tick();
        resolveKeyPresses();
    }
    
    public void addObjects(GameObject ... objects) {
    	for(GameObject object : objects) {
    		Window.getGameScene().getNodes().addAll(object.getSprite());
    		if(object.getHitBox() != null) {
    			handler.addHitBox(object.getHitBox());
    		}
    	}
    	handler.addObjects(objects);
    }
    
    public void setStage(Stage stage) {
        pStage = stage;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public FightingStage getFightingStage() {
    	return fightingStage;
    }
    
    private void resolveKeyPresses() {
        if(fighterFalessi != null) {
            handleFighterFalessi();
            handleFighterMammen();
        }
    }
    
    private void handleFighterFalessi() {
        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            fighterFalessi.moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            fighterFalessi.moveLeft();
        } else {
            // Some more shit goes here
        }
    }
    
    private void handleFighterMammen() {
        if (pressedKeys.contains(KeyCode.RIGHT) && !pressedKeys.contains(KeyCode.LEFT)) {
            fighterMammen.moveRight();
        } else if (pressedKeys.contains(KeyCode.LEFT) && !pressedKeys.contains(KeyCode.RIGHT)) {
            fighterMammen.moveLeft();
        } else {
            // Some more shit goes here
        }
    }
    
    public void addFighter(Fighter fighter) {
    	handler.addObject(fighter);
        if(fighter.getProfessor() == Professor.FALESSI)
            fighterFalessi = fighter;
        else
            fighterMammen = fighter;
    }


    public Stage getStage() {
        return pStage;
    }

    
    public Handler getHandler() {
    	return handler;
    }
    
}