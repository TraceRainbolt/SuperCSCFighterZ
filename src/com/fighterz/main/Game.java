package com.fighterz.main;

import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Game {
	
	private String RIGHT_STR = "right";

    private Handler handler;

    private HashSet<KeyCode> pressedKeys;

    private Fighter fighterRight;
    private Fighter fighterLeft;

    private CharacterSelectScreen charSelectScreen;
    private FightingStage fightingStage;
    private MainMenu mainMenu;

    private Scene scene;
    private Stage pStage;

    private boolean movementLockRight = false;
    private boolean movementLockLeft = false;

    public Game() {
        this.handler = new Handler();
        this.pressedKeys = new HashSet<>();
        initUI();
    }

    private void initUI() {
        this.fightingStage = new FightingStage();
        this.charSelectScreen = new CharacterSelectScreen();
        
        OptionsMenu optionsMenu = new OptionsMenu();
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

    private void resolveKeyPresses() {
        if (fighterRight != null && !movementLockRight) {
            handleFighterRight();
        }
        if(fighterLeft != null && !movementLockLeft) {
            handleFighterLeft();
        }
        if(Window.getGameScene() instanceof CharacterSelectScreen)
        	charSelectScreen.handleKeys(pressedKeys);
    }

    private void handleFighterRight() {
    	if(!fighterRight.getBlock()) {
    		if (pressedKeys.contains(KeyCode.L) && !pressedKeys.contains(KeyCode.J)) {
            	fighterRight.moveRight();
        	} else if (pressedKeys.contains(KeyCode.J) && !pressedKeys.contains(KeyCode.L)) {
            	fighterRight.moveLeft();
        	}
        	if (pressedKeys.contains(KeyCode.O)) {
            	fighterRight.setAnimation(AnimationState.POWER_MOVE);
        	} else if(pressedKeys.contains(KeyCode.I) && pressedKeys.contains(KeyCode.L)) {
            	fighterRight.teleportBehindYou();
        	}
        	if (pressedKeys.contains(KeyCode.H) && !movementLockRight) {
        		fighterRight.setPoweredUp();
        	}
    	}
    	
        if(pressedKeys.contains(KeyCode.N) && !movementLockRight) {
        	fighterRight.setBlockOn();
        }
    }

    private void handleFighterLeft() {
    	if(!fighterLeft.getBlock()) {
    		if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            	fighterLeft.moveRight();
        	} else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            	fighterLeft.moveLeft();
        	}
        	if (pressedKeys.contains(KeyCode.E)) {
            	fighterLeft.setAnimation(AnimationState.POWER_MOVE);
        	} else if(pressedKeys.contains(KeyCode.D) && pressedKeys.contains(KeyCode.W)) {
        		fighterLeft.teleportBehindYou();
        	}
        	if (pressedKeys.contains(KeyCode.F) && !movementLockLeft) {
        		fighterLeft.setPoweredUp();
        	}
    	}
        if(pressedKeys.contains(KeyCode.C) && !movementLockLeft) {
        	fighterLeft.setBlockOn();
        }
    }

    public void setMovementLock(boolean value, String side) {
        if(side == RIGHT_STR) {
            movementLockRight = value;
        } else if (side == "left") {
            movementLockLeft = value;
        }
    }
    
    public boolean getMovementLock(String side) {
        if(side == RIGHT_STR)
            return movementLockRight;
        return movementLockLeft;
    }

    public void addObjects(GameObject... objects) {
        for (GameObject object : objects) {
            Window.getGameScene().getNodes().addAll(object.getSprite());
        }
        handler.addObjects(objects);
    }

    public void addHitBox(HitBox... hitBoxes) {
        for (HitBox hitBox : hitBoxes) {
            Window.getGameScene().getNodes().addAll(hitBox.getRect());
            handler.addHitBox(hitBox);
        }
    }
    
    public void nullFighters() {
    	fighterRight = null;
    	fighterLeft = null;
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

    public void addFighter(Fighter fighter) {
        addObjects(fighter);
        if(fighter.getSide() == RIGHT_STR) {
            fighterRight = fighter;
            fightingStage.setFighterRight(fighter);
        } else if(fighter.getSide() == "left") {
            fighterLeft = fighter;
            fightingStage.setFighterLeft(fighter);
        }
    }

    public Stage getStage() {
        return pStage;
    }

    public Handler getHandler() {
        return handler;
    }

	public GameScene getMainMenu() {
		return mainMenu;
	}

	public GameScene getCharSelect() {
		return charSelectScreen;
	}
	
	public Fighter getFighterRight() {
		return fighterRight;
	}
	
	public Fighter getFighterLeft() {
		return fighterLeft;
	}
}