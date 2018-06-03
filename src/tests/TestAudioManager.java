package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.AudioManager;

import javafx.application.Application;
import javafx.stage.Stage;

// NOTE: These are basic tests, but I can't get too in-depth with them. 
// 	     There's not a whole lot I can check besides that they don't crash when I call the methods.
public class TestAudioManager {

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

	// MenuSounds Tests
	@Test
	public void testButtonMousedOverSound() {
		AudioManager.MenuSounds.playButtonMousedOverSound();
	}

	@Test
	public void testButtonClickedSound() {
		AudioManager.MenuSounds.playButtonClickedSound();
	}

	// Music Tests
	@Test
	public void testSetMusicVolume() {
		AudioManager.Music.setVolume(0.5);
	}

	@Test
	public void testPlayFightMusic() {
		AudioManager.Music.playFightMusic();
	}

	@Test
	public void testPlayMenuMusic() {
		AudioManager.Music.playMenuMusic();
	}

	@Test
	public void testStopAllMusic() {
		AudioManager.Music.stopAllMusic();
	}
}
