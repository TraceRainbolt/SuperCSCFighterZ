package com.fighterz.main;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface GameScene {
    public void render();

    public ObservableList<Node> getNodes();
}
