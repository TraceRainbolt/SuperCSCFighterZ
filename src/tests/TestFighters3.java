//Author: Connor Steele
// unit test 1/2

package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.Fighter;
import com.fighterz.main.FighterFalessi;
import com.fighterz.main.FighterMammen;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFighters3 {
	
	
	// Used to allow JavaFX to run
		public static class AsNonApp extends Application {
			@Override
			public void start(Stage primaryStage) throws Exception {
			}
		}

		
		@BeforeClass //make it so there are no graphic errors
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
		
		
		@Test //make sure characters spawn the right ways
		public void testFighterSpawn() throws InterruptedException {
			try {
				Window.main(null);
			}
			catch(Exception e) {
				
			}
			
			
			Window.getGame().addFighter(new FighterMammen("right"));
			Window.getGame().addFighter(new FighterFalessi("left"));
			
			Fighter fr = Window.getGame().getFighterRight();
			Fighter fl = Window.getGame().getFighterLeft();
			
			assertTrue((fr instanceof FighterMammen ) && (fl instanceof FighterFalessi )  );
			

		}
}