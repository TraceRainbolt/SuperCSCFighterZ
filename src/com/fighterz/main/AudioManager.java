package com.fighterz.main;

import javafx.scene.media.*;

public class AudioManager {
	public Music music;
	public MenuSounds menuSounds;

	// Singleton instance of AudioManager
	private static AudioManager instance;

	// Actual AudioManager constructor
	private AudioManager() {
		// Initialize AudioManager
		music = new Music();
		menuSounds = new MenuSounds();
	}

	// Gets AudioManager singleton, initializing if necessary
	public static synchronized AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManager();
		}
		return instance;
	}

	public class Music {
		private MediaPlayer menuSong, fightSong;

		private Music() {
			Media menuSongMedia = new Media(
					AudioManager.class.getClassLoader().getResource("Audio/Music/Main Theme.m4a").toString());
			menuSong = new MediaPlayer(menuSongMedia);
			// Loop forever
			menuSong.setCycleCount(MediaPlayer.INDEFINITE);

			Media fightSongMedia = new Media(
					AudioManager.class.getClassLoader().getResource("Audio/Music/Fight Theme.m4a").toString());
			fightSong = new MediaPlayer(fightSongMedia);
			fightSong.setCycleCount(MediaPlayer.INDEFINITE);
		}

		// Play menu music and stop fighting music
		public void playMenuMusic() {
			fightSong.stop();
			menuSong.play();
		}

		// Play fighting music and stop menu music
		public void playFightMusic() {
			menuSong.stop();
			fightSong.play();
		}

		// Stop all music (I don't know why you'd need this, but it seems useful
		// somehow)
		public void stopAllMusic() {
			menuSong.stop();
			fightSong.stop();
		}

		// TODO: Use in options menu to change music volume
		public void setMusicVolume(double volume) {
			menuSong.setVolume(volume);
			fightSong.setVolume(volume);
		}
	}

	public class MenuSounds {
		private MediaPlayer buttonMousedOverSound, buttonClickedSound;

		private MenuSounds() {
			Media buttonMousedOverMedia = new Media(AudioManager.class.getClassLoader()
					.getResource("Audio/Menu Sounds/Button Moused Over.wav").toString());
			buttonMousedOverSound = new MediaPlayer(buttonMousedOverMedia);
			Media buttonClickedMedia = new Media(
					AudioManager.class.getClassLoader().getResource("Audio/Menu Sounds/Button Clicked.wav").toString());
			buttonClickedSound = new MediaPlayer(buttonClickedMedia);
		}

		public void playButtonMousedOverSound() {
			buttonMousedOverSound.stop();
			buttonMousedOverSound.play();
		}

		public void playButtonClickedSound() {
			buttonClickedSound.stop();
			buttonClickedSound.play();
		}
	}

}
