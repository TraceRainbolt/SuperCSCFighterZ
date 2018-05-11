package com.fighterz.main;

import java.util.HashSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Game {

    private Handler handler;

    private HashSet<KeyCode> pressedKeys;

    private FighterFalessi fighterFalessi;
    private FighterMammen fighterMammen;

    private FightingStage fightingStage;

    private Scene scene;
    private Stage pStage;

    private boolean falessiMovementLock = false;
    private boolean mammenMovementLock = false;
    private boolean falessiAbilityLock = false;
    private boolean mammenAbilityLock = false;

    public Game() {
        this.handler = new Handler();
        this.pressedKeys = new HashSet<>();
        initUI();
    }

    private void initUI() {
        CharacterSelectScreen charSelectScreen = new CharacterSelectScreen();
        OptionsMenu optionsMenu = new OptionsMenu();
        this.fightingStage = new FightingStage();
        MainMenu mainMenu = new MainMenu(charSelectScreen, fightingStage, optionsMenu);

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
        if (fighterFalessi != null) {
            if (!falessiMovementLock)
                handleFighterFalessi();
            if (!mammenMovementLock)
                handleFighterMammen();
        }
    }

    private void handleFighterFalessi() {
        if (pressedKeys.contains(KeyCode.L) && !pressedKeys.contains(KeyCode.J)) {
            fighterFalessi.moveRight();
        } else if (pressedKeys.contains(KeyCode.J) && !pressedKeys.contains(KeyCode.L)) {
            fighterFalessi.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {
            fighterFalessi.jump();
        }
        if (pressedKeys.contains(KeyCode.O) && !falessiAbilityLock) {
            fighterFalessi.setAnimation(AnimationState.POWER_BALL);
        }
    }

    private void handleFighterMammen() {
        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            fighterMammen.moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            fighterMammen.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.E) && !mammenAbilityLock) {
            fighterMammen.setAnimation(AnimationState.POINTER_VISION);
        }
    }

    public void setFalessiMovementLock(boolean value) {
        falessiMovementLock = value;
    }

    public void setMammenMovementLock(boolean value) {
        mammenMovementLock = value;
    }

    public void setMammenAbilityLock(boolean value) {
        mammenAbilityLock = value;
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
        handler.addObject(fighter);
        if (fighter instanceof FighterFalessi)
            fighterFalessi = (FighterFalessi) fighter;
        else
            fighterMammen = (FighterMammen) fighter;
    }

    public Stage getStage() {
        return pStage;
    }

    public Handler getHandler() {
        return handler;
    }
}