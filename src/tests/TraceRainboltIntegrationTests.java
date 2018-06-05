//Author: Trace Rainbolt
package tests;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.EnergyBar;
import com.fighterz.main.Fighter;
import com.fighterz.main.FighterFalessi;
import com.fighterz.main.FighterMammen;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;
import tests.TraceRainboltUnitTests.AsNonApp;

public class TraceRainboltIntegrationTests {
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

	@Test
	public void testAddFighterRight() throws InterruptedException {
		try {
			Window.main(null);
		}
		catch(Exception e) {
			
		}
		Fighter mammen = new FighterMammen("right");
		Window.getGame().addFighter(mammen);
		
		assertTrue(Window.getGame().getFighterRight() instanceof FighterMammen);

	}
	
	@Test
	public void testAddFighterLeft() throws InterruptedException {
		try {
			Window.main(null);
		}
		catch(Exception e) {
			
		}
		Fighter mammen = new FighterFalessi("left");
		Window.getGame().addFighter(mammen);
		
		assertTrue(Window.getGame().getFighterLeft() instanceof FighterFalessi);

	}
}
