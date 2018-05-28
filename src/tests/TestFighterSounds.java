package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.FighterSounds;
import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFighterSounds {

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

	// Test methods specific to each player
	@Test
	public void testMammenPlayBeginGameSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playBeginGameSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}

	@Test
	public void testFalessiPlayBeginGameSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playBeginGameSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testMammenPlayIdleSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playIdleSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testFalessiPlayIdleSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playIdleSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testMammenPlayTakeDamageSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playTakeDamageSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testFalessiPlayTakeDamageSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playTakeDamageSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testMammenPlayMeleSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playMeleSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testFalessiPlayMeleSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playMeleSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testMammenPlayRangedSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playRangedSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testFalessiPlayRangedSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playRangedSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testMammenPlayJumpSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playJumpSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testFalessiPlayJumpSound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playJumpSound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testMammenPlayVictorySound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Mammen");
			fSounds.playVictorySound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
	@Test
	public void testFalessiPlayVictorySound() {
		FighterSounds fSounds;
		try {
			fSounds = new FighterSounds("Falessi");
			fSounds.playVictorySound();
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
	}
	
}
