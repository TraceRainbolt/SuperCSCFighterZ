package com.fighterz.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    // 16 : 9 Ratio based off height
    private final static int INIT_HEIGHT = 1080;
    private final static int INIT_WIDTH = INIT_HEIGHT * 16 / 9;
    
    // Used to determine values based off ratio of current res (HEIGHT) to standard res (1080p)
    private static double hRatio = INIT_HEIGHT / 1080.0;
    
    private static int height = INIT_HEIGHT, width = INIT_WIDTH;

    Parent mainMenuLayout, characterSelectLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        characterSelectLayout = createCharacterSelect();
        mainMenuLayout = createMainMenu();

        Scene root = new Scene(mainMenuLayout);

        stage.setResizable(false);
        stage.setTitle("Super CSC FighterZ");
        stage.setHeight(INIT_HEIGHT);
        stage.setWidth(INIT_WIDTH);
        stage.setScene(root);
        stage.show();
    }
    
    private static void setWindowSize(Stage stage, int newHeight) {
    	height = newHeight;
    	width = newHeight * 16 / 9;
    	hRatio = newHeight / 1080.0;
    	
        stage.setHeight(height);
        stage.setWidth(width);
    }
    
    private static int getWidth() {
    	return width;
    }
    
    private static int getHeight() {
    	return height;
    }

    private StackPane createMainMenu() {
        StackPane mainMenuLayout = new StackPane();

        SimpleImage background = new SimpleImage("MainMenuBG.png", true);
        background.setFitHeight(Game.getHeight());
        background.setFitWidth(Game.getWidth());

        ImageView logoView = setupLogo();

        MainMenuButton play = new MainMenuButton("Play", createCharacterSelect());
        MainMenuButton leaderboards = new MainMenuButton("Leaderboards", createCharacterSelect());
        MainMenuButton options = new MainMenuButton("Options", createOptionsMenu());
        MainMenuButton exit = new MainMenuButton("Exit");
     
        int maxX = (int) (hRatio * 420);
        play.setTranslateX(-maxX);
        leaderboards.setTranslateX(-maxX / 2 + hRatio * 90); // Leaderboard btn is offset b/c it's so long
        options.setTranslateX(maxX / 2);
        exit.setTranslateX(maxX);

        mainMenuLayout.getChildren().add(background);
        mainMenuLayout.getChildren().add(logoView);
        mainMenuLayout.getChildren().add(play);
        mainMenuLayout.getChildren().add(leaderboards);
        mainMenuLayout.getChildren().add(options);
        mainMenuLayout.getChildren().add(exit);

        return mainMenuLayout;
    }
    
    private ImageView setupLogo() {
    	SimpleImage logo = new SimpleImage("MainMenuLogo.png");
    	logo.setFitHeight(Game.getHeight());
    	logo.setFitWidth(Game.getWidth());

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(logo.rotateProperty(), -2)),
            new KeyFrame(new Duration(800), new KeyValue(logo.rotateProperty(), 2)),
            new KeyFrame(new Duration(1600), new KeyValue(logo.rotateProperty(), -2))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        return logo;
    }

    private StackPane createCharacterSelect() {
        StackPane characterSelectLayout = new StackPane();
        Image background = new Image("CharacterSelect.png");
        ImageView imgView = new ImageView(background);
        imgView.setFitHeight(Game.getHeight());
        imgView.setFitWidth(Game.getWidth());

        // Eventually null will be replaced with a reference to the previous scene
        // For now we assume mainMenu
        BackButton backBtn = new BackButton(null);

        characterSelectLayout.getChildren().add(imgView);
        characterSelectLayout.getChildren().add(backBtn);
        return characterSelectLayout;
    }
    
    private StackPane createOptionsMenu() {
        StackPane optionsMenu = new StackPane();
        SimpleImage imgView = new SimpleImage("OptionsMenu.png", true);
        
        ImageView logoView = setupLogo();
        
        OptionsButton minRes = new OptionsButton("640 x 480");
        OptionsButton medRes = new OptionsButton("1280 x 720");
        OptionsButton maxRes = new OptionsButton("1920 x 1080");
        
        minRes.setTranslateX(-270 * hRatio);
        minRes.setTranslateY(-170 * hRatio);
        
        medRes.setTranslateX(-259 * hRatio);
        medRes.setTranslateY(-70 * hRatio);
        
        maxRes.setTranslateX(-241 * hRatio);
        maxRes.setTranslateY(30 * hRatio);
        
        SimpleImage pointer = new SimpleImage("OptionPointer.png", false);
        
        
        SimpleImage container = new SimpleImage("OptionsContainer.png", true);
        SimpleImage heading = new SimpleImage("OptionsHeading.png", true);

        // Eventually null will be replaced with a reference to the previous scene
        // For now we assume mainMenu
        BackButton backBtn = new BackButton(null);

        optionsMenu.getChildren().add(imgView);
        optionsMenu.getChildren().add(backBtn);
        optionsMenu.getChildren().add(logoView);
        optionsMenu.getChildren().add(container);
        optionsMenu.getChildren().add(heading);
        optionsMenu.getChildren().add(minRes);
        optionsMenu.getChildren().add(medRes);
        optionsMenu.getChildren().add(maxRes);
        optionsMenu.getChildren().add(pointer);
        
        
        pointer.setTranslateX(-450 * hRatio);
        
        if(Game.getHeight() == 1080) {
            pointer.setTranslateY(30 * hRatio);
        } else if(Game.getHeight() == 720) {
        	pointer.setTranslateY(-70 * hRatio);
        } else {
        	pointer.setTranslateY(-170 * hRatio);
        }
        
        minRes.setupHandlers(optionsMenu, pointer);
        medRes.setupHandlers(optionsMenu, pointer);
        maxRes.setupHandlers(optionsMenu, pointer);
        
        return optionsMenu;
    }
    
    // Removes a lot of boilerplate for adding images
    public class SimpleImage extends ImageView {
    	
    	public SimpleImage(String path) {
    		super(new Image(path));
    	}
    	
    	public SimpleImage(String path, boolean fitScreen) {
    		super(new Image(path));
    		if(fitScreen) {
                this.setFitHeight(Game.getHeight());
                this.setFitWidth(Game.getWidth());
    		} else {
    			double width = this.boundsInLocalProperty().getValue().getWidth() * hRatio;
    			double height = this.boundsInLocalProperty().getValue().getHeight() * hRatio;
    			this.setFitWidth(width);
    			this.setFitHeight(height);
    		}
    	}
    }
    
    public class OptionsButton extends Label {
    	
    	private int heightVal;
    	
        public OptionsButton(String label) {
            super(label);
            this.heightVal = Integer.parseInt(label.split(" ")[2]);
            this.setupAppearence();
        }
        
        private void setupAppearence() {
            this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, hRatio * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);
        }
        
        public void setupHandlers(StackPane optionsMenu, SimpleImage pointer){
        	OptionsButton that = this;
        	SimpleImage highlight = new SimpleImage("OptionHighlighter.png");
            highlight.setFitHeight(highlight.boundsInLocalProperty().getValue().getHeight() * hRatio);
            highlight.setFitWidth(highlight.boundsInLocalProperty().getValue().getWidth() * hRatio);
            
            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    optionsMenu.getChildren().add(highlight);
                    highlight.setTranslateX(that.getTranslateX() + 170 * hRatio);
                    highlight.setTranslateY(that.getTranslateY());
                    
                    that.toFront();
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    optionsMenu.getChildren().remove(highlight);
                }
            });
            
            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                	Game.setWindowSize(stage, that.heightVal);
                    getScene().setRoot(createOptionsMenu());
                }
            });
        }
    }

    public class MainMenuButton extends Label {

        public MainMenuButton(String label, Parent newScene) {
            super(label);

            this.setupAppearence();
            this.setupHandlers();

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    getScene().setRoot(newScene);
                }
            });
        }

        // Currently only for exit button
        public MainMenuButton(String label) {
            super(label);

            this.setupAppearence();
            this.setupHandlers();

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.exit(0);
                }
            });
        }

        private void setupAppearence() {
            // TODO better font
            this.setFont(Font.font("Arial", FontWeight.NORMAL, hRatio * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);

            // Relative height to window size, 1/4 of the way up from the bottom
            this.setTranslateY(Game.getHeight() / 2 - Game.getHeight() / 4);
        }

        private void setupHandlers() {
            // B/c we lose reference to ourself inside event handler
            MainMenuButton that = this;

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(new Glow(0.8));
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(null);
                }
            });
        }
    }

    public class BackButton extends ImageView {

        public BackButton(Parent prevScene) {
            super(new Image("BackIcon.png"));  
            
            this.setCursor(Cursor.HAND);
            double side = hRatio * new Image("BackIcon.png").getHeight();
            this.setFitHeight(side);
            this.setFitWidth(side);

            // Save ourself for event handler
            BackButton that = this;
            this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setTranslateY(that.getTranslateY() + 2);
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    getScene().setRoot(createMainMenu());
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(new Glow(0.8));
                }
            });

            this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    that.setEffect(null);
                }
            });

            // Place back button in left corner relative to window size
            this.setTranslateY(Game.getHeight() / 2 - hRatio * 110);
            this.setTranslateX(-Game.getWidth() / 2 + hRatio * 110);
        }
    }
}