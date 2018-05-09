package com.fighterz.main;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int width;
    private final int height;

    private int lastIndex;

    public SpriteAnimation(ImageView imageView, Duration duration, int count) {
        this.imageView = imageView;
        this.count = count;
        this.width = (int) (imageView.getImage().getWidth() / count);
        this.height = (int) (imageView.getImage().getHeight());

        imageView.setViewport(new Rectangle2D(0, 0, width, height));
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }
    
    public SpriteAnimation(ImageView imageView, Duration duration, int count, int offset) {
    	this(imageView, duration, count);
    	imageView.setTranslateX(imageView.getTranslateX() - offset);
    }
    

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % count) * width;
            final int y = 0;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}