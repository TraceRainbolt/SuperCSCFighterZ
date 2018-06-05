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

public class MannyJimenezIntegrationTests {

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
	public void testBlockOff() {
		try {
			Window.main(null);
		} catch (Exception e) {

		}

		Window.getGame().addFighter(new FighterMammen("right"));

		Fighter f = Window.getGame().getFighterRight();

		f.onCollide(null);

		int energy = f.getEnergy();

		boolean isBlocking = f.getBlock();
		
		assertTrue(isBlocking == false && energy == 10);
	}
	
	@Test
	public void testBlockOn() {
		try {
			Window.main(null);
		} catch (Exception e) {

		}

		Window.getGame().addFighter(new FighterMammen("right"));

		Fighter f = Window.getGame().getFighterRight();

		f.onCollide(null);

		int energy = f.getEnergy();
		
		f.setBlockOn();

		boolean isBlocking = f.getBlock();
		
		assertTrue(isBlocking == true && energy == 10);
	}

}
