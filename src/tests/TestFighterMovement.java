//Author: Juan Jimenez

package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.Fighter;
import com.fighterz.main.FighterMammen;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFighterMovement {
	
	
	// Used to allow JavaFX to run
		public static class AsNonApp extends Application {
			@Override
			public void start(Stage primaryStage) throws Exception {
			}
		}

		@BeforeClass
		public static void initJFX() throws InterruptedException {
			Thread t = new Thread("JavaFX Init Thread") {
				public void run() {
					try {
						Application.launch(AsNonApp.class, new String[0]);
					} catch (Exception e) {
						// Don't launch another application if one is already running
					}	
				}
			};
			t.setDaemon(true);
			t.start();
			Thread.sleep(500);
		}
		
		// Manny Tests
		
		@Test
		public void testFighterMoveRight() throws InterruptedException {
			try {
				Window.main(null);
			}
			catch(Exception e) {
				
			}
			
			
			Window.getGame().addFighter(new FighterMammen("right"));
			
			Fighter f = Window.getGame().getFighterRight();
			
			double x = f.getX();
			
			f.moveRight();
			double y = f.getX();

			assertTrue("The player did not move", (266 == (int) x) && (272 == (int) y));
			
		}
		
		@Test
		public void testFighterMoveLeft() throws InterruptedException {
			try {
				Window.main(null);
			}
			catch(Exception e) {
				
			}
			
			
			Window.getGame().addFighter(new FighterMammen("right"));
			
			Fighter f = Window.getGame().getFighterRight();
			
			double x = f.getX();
			
			f.moveLeft();
			double y = f.getX();		
			
			assertTrue("The player did not move", (266 == (int) x) && (260 == (int) y));
			
		}
}
