package com.fighterz.main;

import javafx.scene.media.*;
import javafx.util.Duration;

public class AudioManager {
	
	// Required to get rid of code smell
	private AudioManager() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static class Music {
		private static final MediaPlayer menuSong = new MediaPlayer(
				new Media(AudioManager.class.getClassLoader().getResource("Audio/Music/Main Theme.m4a").toString()));
		private static final MediaPlayer fightSong = new MediaPlayer(
				new Media(AudioManager.class.getClassLoader().getResource("Audio/Music/Fight Theme.m4a").toString()));

		// Required to get rid of code smell
		private Music() {
			throw new IllegalStateException("Static Class");
		}

		// Play menu music and stop fighting music
		public static void playMenuMusic() {
			fightSong.stop();
			menuSong.setCycleCount(MediaPlayer.INDEFINITE);
			menuSong.seek(Duration.ZERO);
			menuSong.play();
		}

		// Play fighting music and stop menu music
		public static void playFightMusic() {
			menuSong.stop();
			fightSong.setCycleCount(MediaPlayer.INDEFINITE);
			fightSong.seek(Duration.ZERO);
			fightSong.play();
		}

		// Stop all music (I don't know why you'd need this, but it seems useful
		// somehow)
		public static void stopAllMusic() {
			menuSong.stop();
			fightSong.stop();
		}

		// TODO: Use in options menu to change music volume
		public static void setMusicVolume(double volume) {
			menuSong.setVolume(volume);
			fightSong.setVolume(volume);
		}
	}

	public static class MenuSounds {
		private static final MediaPlayer buttonMousedOverSound = new MediaPlayer(new Media(AudioManager.class.getClassLoader()
				.getResource("Audio/Menu Sounds/Button Moused Over.wav").toString()));
		private static final MediaPlayer buttonClickedSound = new MediaPlayer(new Media(
					AudioManager.class.getClassLoader().getResource("Audio/Menu Sounds/Button Clicked.wav").toString()));

		// Required to get rid of code smell
		private MenuSounds() {
			throw new IllegalStateException("Static Class");
		}

		public static void playButtonMousedOverSound() {
			buttonMousedOverSound.stop();
			buttonMousedOverSound.seek(Duration.ZERO);
			buttonMousedOverSound.play();
		}

		public static void playButtonClickedSound() {
			buttonClickedSound.stop();
			buttonMousedOverSound.seek(Duration.ZERO);
			buttonClickedSound.play();
		}
	}

}
