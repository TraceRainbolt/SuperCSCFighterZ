//Author: Connor Steele
// Integraton test 1/2

package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.AnimationState;
import com.fighterz.main.Fighter;
import com.fighterz.main.FighterFalessi;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestIntegration {

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
	public void testInteg() throws InterruptedException { // test Falessi Sound integration

		try {
			Window.main(null);
		} catch (Exception e) {

		}

		Window.getGame().addFighter(new FighterFalessi("right"));

		Fighter f = Window.getGame().getFighterRight();

		f.setAnimation(AnimationState.NORMAL_MOVE); // this function calls another function that plays sound and sets a
													// boolean in the FighterSoundsClass

		String s = f.getFighterSounds().getPlayingSound();

		assertTrue(s.equals("RangedSound")); // make sure the sound is set when the move is used

	}

}
