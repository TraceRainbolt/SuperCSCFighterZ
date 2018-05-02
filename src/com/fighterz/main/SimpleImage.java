package com.fighterz.main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Class removes a lot of boilerplate for adding images
public class SimpleImage extends ImageView {
    private int height;
    private int width;

    public SimpleImage(String path) {
        this(new Image(path));
    }

    // This constructor used if images need to be scaled based on resolution
    public SimpleImage(String path, boolean fitScreen) {
        this(new Image(path));
        // Stretch to entire screen or just scale based on hRatio
        if (fitScreen) {
            this.setWidth(Game.getWidth());
            this.setHeight(Game.getHeight());
        } else {
            int init_width = (int) (this.getWidth() * Game.getHRatio());
            int init_height = (int) (this.getHeight() * Game.getHRatio());
            this.setWidth(init_width);
            this.setHeight(init_height);
            this.setPreserveRatio(true);
        }
    }

    // Grab width/height
    private SimpleImage(Image img) {
        super(img);
        this.height = (int) img.getHeight();
        this.width = (int) img.getWidth();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.setFitHeight(height);
        this.height = height;
    }

    public void setWidth(int width) {
        this.setFitWidth(width);
        this.width = width;
    }
}
