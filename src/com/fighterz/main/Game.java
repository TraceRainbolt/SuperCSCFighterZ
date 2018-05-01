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
    private final static int INIT_HEIGHT = 720;
    private final static int INIT_WIDTH = INIT_HEIGHT * 16 / 9;
    
    // Used to determine values based off ratio of current res (HEIGHT) to standard res (1080p)
    private static double hRatio = INIT_HEIGHT / 1080.0;
    
    // Mutable height and with variables
    private static int height = INIT_HEIGHT, width = INIT_WIDTH;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        StackPane mainMenuLayout = createMainMenu();

        Scene root = new Scene(mainMenuLayout);

        stage.setResizable(false);
        stage.setTitle("Super CSC FighterZ");
        stage.setHeight(INIT_HEIGHT);
        stage.setWidth(INIT_WIDTH);
        stage.setScene(root);
        stage.show();
    }
    
    private static void setWindowSize(Stage stage, int newHeight) {
    	Game.height = newHeight;
    	Game.width = newHeight * 16 / 9;
    	Game.hRatio = newHeight / 1080.0;
    	
        stage.setHeight(height);
        stage.setWidth(width);
    }
    
    private static int getWidth() {
    	return width;
    }
    
    private static int getHeight() {
    	return height;
    }
    
    private static double getHRatio() {
        return hRatio;
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
     
        int maxX = (int) (Game.getHRatio() * 420);
        play.setTranslateX(-maxX);
        leaderboards.setTranslateX(-maxX / 2 + Game.getHRatio() * 90); // Leaderboard btn is offset b/c it's so long
        options.setTranslateX(maxX / 2);
        exit.setTranslateX(maxX);

        mainMenuLayout.getChildren().addAll(background, logoView, play, leaderboards, options, exit);

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

        characterSelectLayout.getChildren().addAll(imgView, backBtn);
        return characterSelectLayout;
    }
    
    private StackPane createOptionsMenu() {
        StackPane optionsMenu = new StackPane();
        SimpleImage imgView = new SimpleImage("OptionsMenu.png", true);
        
        ImageView logoView = setupLogo();
        
        OptionsButton minRes = new OptionsButton("640 x 480");
        OptionsButton medRes = new OptionsButton("1280 x 720");
        OptionsButton maxRes = new OptionsButton("1920 x 1080");
        
        // Starting point for resolution choices
        int baseY = -170;
        
        // Magic numbers for x values simply left align the res label
        minRes.setTranslateX(-270 * Game.getHRatio());
        minRes.setTranslateY(baseY * Game.getHRatio());
        
        medRes.setTranslateX(-259 * Game.getHRatio());
        medRes.setTranslateY((baseY + 100) * Game.getHRatio());
        
        maxRes.setTranslateX(-241 * Game.getHRatio());
        maxRes.setTranslateY((baseY + 200) * Game.getHRatio());
        
        SimpleImage pointer = new SimpleImage("OptionPointer.png", false);
        
        
        SimpleImage container = new SimpleImage("OptionsContainer.png", true);
        SimpleImage heading = new SimpleImage("OptionsHeading.png", true);

        // Eventually null will be replaced with a reference to the previous scene
        // For now we assume mainMenu
        BackButton backBtn = new BackButton(null);

        optionsMenu.getChildren().addAll(imgView, backBtn, logoView, container, 
                heading, minRes, medRes, maxRes, pointer);
        
        pointer.setTranslateX(-450 * Game.getHRatio());
        
        // Always initialize pointer pointing to correct resolution
        if(Game.getHeight() == 1080) {
            pointer.setTranslateY((baseY + 200) * Game.getHRatio());
        } else if(Game.getHeight() == 720) {
        	pointer.setTranslateY((baseY + 100) * Game.getHRatio());
        } else {
        	pointer.setTranslateY(baseY * Game.getHRatio());
        }
        
        minRes.setupHandlers(optionsMenu, pointer);
        medRes.setupHandlers(optionsMenu, pointer);
        maxRes.setupHandlers(optionsMenu, pointer);
        
        return optionsMenu;
    }
    
    
    // Class removes a lot of boilerplate for adding images
    public class SimpleImage extends ImageView {
    	
    	public SimpleImage(String path) {
    		super(new Image(path));
    	}
    	
    	// This constructor used if images need to be scaled based on resolution
    	public SimpleImage(String path, boolean fitScreen) {
    		super(new Image(path));
    		// Stretch to entire screen or just scale based on hRatio
    		if(fitScreen) {
                this.setFitHeight(Game.getHeight());
                this.setFitWidth(Game.getWidth());
    		} else {
    			double width = this.boundsInLocalProperty().getValue().getWidth() * Game.getHRatio();
    			double height = this.boundsInLocalProperty().getValue().getHeight() * Game.getHRatio();
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
            this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, Game.getHRatio() * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);
        }
        
        public void setupHandlers(StackPane optionsMenu, SimpleImage pointer){
            // B/c we lose reference to ourself inside event handler
        	OptionsButton that = this;
        	
        	SimpleImage highlight = new SimpleImage("OptionHighlighter.png");
            highlight.setFitHeight(highlight.boundsInLocalProperty().getValue().getHeight() * Game.getHRatio());
            highlight.setFitWidth(highlight.boundsInLocalProperty().getValue().getWidth() * Game.getHRatio());
            
            this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    optionsMenu.getChildren().add(highlight);
                    highlight.setTranslateX(that.getTranslateX() + 170 * Game.getHRatio());
                    highlight.setTranslateY(that.getTranslateY());
                    
                    // So that the highlight doesn't appear on top of us
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
            this.setFont(Font.font("Myriad Pro", FontWeight.NORMAL, Game.getHRatio() * 54));
            this.setTextFill(Color.WHITE);
            this.setCursor(Cursor.HAND);

            // Relative height to window size, 1/4 of the way up from the bottom
            this.setTranslateY(Game.getHeight() / 2 - Game.getHeight() / 4);
        }

        private void setupHandlers() {
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
            double side = Game.getHRatio() * new Image("BackIcon.png").getHeight();
            this.setFitHeight(side);
            this.setFitWidth(side);

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
            this.setTranslateY(Game.getHeight() / 2 - Game.getHRatio() * 110);
            this.setTranslateX(-Game.getWidth() / 2 + Game.getHRatio() * 110);
        }
    }
}