//Author: Christopher Gix

package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.Fighter;
import com.fighterz.main.FighterMammen;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChrisGixIntegrationTest2 {

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
	public void testEnergyAndHealth() {
		try {
			Window.main(null);
		} catch (Exception e) {

		}

		Window.getGame().addFighter(new FighterMammen("right"));

		Fighter f = Window.getGame().getFighterRight();

		f.setBlockOn();

		double health = f.getHealth();

		f.onCollide(null);

		double endHealth = f.getHealth();

		assertTrue(health == endHealth);
	}

}
