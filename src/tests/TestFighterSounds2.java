//Connor Steele
package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.FighterSounds;
import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFighterSounds2 {

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

	// Edward Kesicki Tests
	@Test
	public void testConstructDummyFighter() {
		FighterSounds dummyFighterSounds;
		try {
			dummyFighterSounds = new FighterSounds("Dummy Fighter");
			assertEquals(null, dummyFighterSounds);
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}

	@Test
	public void testKillSound() {
		FighterSounds mammenSounds;
		try {
			mammenSounds = new FighterSounds("Mammen");
			mammenSounds.playIdleSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
		
	}


	
}
