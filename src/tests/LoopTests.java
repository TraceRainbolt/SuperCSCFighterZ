package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.FighterSounds;
import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.application.Application;
import javafx.stage.Stage;

public class LoopTests {

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
	public void testIdleSoundManagerLoop0Times() throws InterruptedException {
		try {
			FighterSounds fSounds = new FighterSounds("Mammen");
			
			Thread.sleep(16000 * 0);
			
			assertNotEquals("IdleSound", fSounds.getPlayingSound());
			
		} catch (NoSuchFighterException e) {
			fail("FighterSounds initialization failed.");
		}
	}
	
	@Test
	public void testIdleSoundManagerLoop1Times() throws InterruptedException {
		try {
			FighterSounds fSounds = new FighterSounds("Mammen");
			
			Thread.sleep(16000 * 1);
			
			assertEquals("IdleSound", fSounds.getPlayingSound());
			
		} catch (NoSuchFighterException e) {
			fail("FighterSounds initialization failed.");
		}
	}
	
	@Test
	public void testIdleSoundManagerLoop2Times() throws InterruptedException {
		try {
			FighterSounds fSounds = new FighterSounds("Mammen");
			
			Thread.sleep(16000 * 2);
			
			assertEquals("IdleSound", fSounds.getPlayingSound());
			
		} catch (NoSuchFighterException e) {
			fail("FighterSounds initialization failed.");
		}
	}

}
