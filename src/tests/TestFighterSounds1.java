// Written by Benjamin Troller

package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.FighterSounds;
import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFighterSounds1 {

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

	// Test constructors
	@Test
	public void testConstructFalessi() {
		FighterSounds falessiSounds;
		try {
			falessiSounds = new FighterSounds("Falessi");
			assertNotEquals(null, falessiSounds);
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}

	@Test
	public void testConstructMammen() {
		FighterSounds mammenSounds;
		try {
			mammenSounds = new FighterSounds("Mammen");
			assertNotEquals(null, mammenSounds);
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}

	@Test
	public void testConstructNonExistentFighter() {
		try {
			new FighterSounds("Ben");
			fail("Constructor should have failed");
		} catch (NoSuchFighterException e) {
			assertEquals("Unable to create fighter with name \"Ben\"", e.getMessage());
		}
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
