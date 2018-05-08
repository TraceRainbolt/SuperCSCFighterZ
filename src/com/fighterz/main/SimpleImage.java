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
            this.setWidth(Window.getWidth());
            this.setHeight(Window.getHeight());
        } else {
            int initWidth = (int) (this.getWidth() * Window.getHRatio());
            int initHeight = (int) (this.getHeight() * Window.getHRatio());
            this.setWidth(initWidth);
            this.setHeight(initHeight);
            this.setPreserveRatio(true);
        }
    }

    // Grab width/height
    private SimpleImage(Image image) {
        super(image);
        this.height = (int) image.getHeight();
        this.width = (int) image.getWidth();
    }

    public SimpleImage(Image image, boolean fitScreen) {
		super(image);
        this.height = (int) image.getHeight();
        this.width = (int) image.getWidth();
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
