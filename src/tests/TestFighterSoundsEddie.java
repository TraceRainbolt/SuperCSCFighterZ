
package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.AnimationState;
import com.fighterz.main.Fighter;
import com.fighterz.main.FighterFalessi;
import com.fighterz.main.FighterMammen;
import com.fighterz.main.FighterSounds;
import com.fighterz.main.Window;
import com.fighterz.main.FighterSounds.NoSuchFighterException;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFighterSoundsEddie {

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

	// Edward Kesicki Unit Tests
	@Test
	public void testConstructDummyFighter() {
		boolean isExcepted = false;
		try {
			FighterSounds dummyFighterSounds;
			dummyFighterSounds = new FighterSounds("Dummy Fighter");
		} catch (NoSuchFighterException e) {
			isExcepted = true;
			assertTrue(isExcepted);
			
		}
	}

	@Test
	public void testKillSound() {
		FighterSounds mammenSounds;
		try {
			mammenSounds = new FighterSounds("Mammen");
			mammenSounds.playIdleSound();
			mammenSounds.kill();
			String sound = mammenSounds.getPlayingSound();
			assertTrue(sound.equals(""));
			
		} catch (NoSuchFighterException e) {
			fail("Constructor failed");
		}
		
	}

	//Edward Kesicki Integration Tests
	@Test
	public void testFalessiBeginGameSound() throws InterruptedException {
		try {
			Window.main(null);
		} catch (Exception e) {

		}

		Window.getGame().addFighter(new FighterFalessi("right"));

		Fighter falessi = Window.getGame().getFighterRight();

		String sound = falessi.getFighterSounds().getPlayingSound();
		System.out.println(sound);
		assertTrue(sound.equals("BeginGameSound")); 
	}
	
	@Test
	public void testMammenBeginGameSound() throws InterruptedException {
		try {
			Window.main(null);
		} catch (Exception e) {

		}

		Window.getGame().addFighter(new FighterMammen("left"));

		Fighter mammen = Window.getGame().getFighterLeft();

		String sound = mammen.getFighterSounds().getPlayingSound();
		System.out.println("Mammen's sound" + sound);
		assertTrue(sound.equals("BeginGameSound")); 
	}

	
}
