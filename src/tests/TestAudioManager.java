package tests;

import static org.junit.Assert.*;

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
				Application.launch(AsNonApp.class, new String[0]);
			}
		};
		t.setDaemon(true);
		t.start();
		Thread.sleep(500);
	}

	// Constructor test
	@Test
	public void testConstructor() {
		AudioManager audioManager = AudioManager.getInstance();
		assertNotEquals(null, audioManager);
	}

	// MenuSounds Tests
	@Test
	public void testButtonMousedOverSound() {
		AudioManager audioManager = AudioManager.getInstance();
		audioManager.menuSounds.playButtonMousedOverSound();
	}

	@Test
	public void testButtonClickedSound() {
		AudioManager audioManager = AudioManager.getInstance();
		audioManager.menuSounds.playButtonClickedSound();
	}

	// Music Tests
	@Test
	public void testSetMusicVolume() {
		AudioManager audioManager = AudioManager.getInstance();
		audioManager.music.setMusicVolume(0.5);
	}

	@Test
	public void testPlayFightMusic() {
		AudioManager audioManager = AudioManager.getInstance();
		audioManager.music.playFightMusic();
	}

	@Test
	public void testPlayMenuMusic() {
		AudioManager audioManager = AudioManager.getInstance();
		audioManager.music.playMenuMusic();
	}

	@Test
	public void testStopAllMusic() {
		AudioManager audioManager = AudioManager.getInstance();
		audioManager.music.stopAllMusic();
	}
}
