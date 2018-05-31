package com.fighterz.main;

import javafx.scene.media.*;

public class AudioManager {

	
	
	public static class Music {
		private static final MediaPlayer menuSong = new MediaPlayer(
				new Media(AudioManager.class.getClassLoader().getResource("Audio/Music/Main Theme.m4a").toString()));
		private static final MediaPlayer fightSong = new MediaPlayer(
				new Media(AudioManager.class.getClassLoader().getResource("Audio/Music/Fight Theme.m4a").toString()));

		private static void initMusic() {
			// Media menuSongMedia = new Media(
			// AudioManager.class.getClassLoader().getResource("Audio/Music/Main
			// Theme.m4a").toString());
			// menuSong = new MediaPlayer(menuSongMedia);

			// Loop forever
			menuSong.setCycleCount(MediaPlayer.INDEFINITE);

			// Media fightSongMedia = new Media(
			// AudioManager.class.getClassLoader().getResource("Audio/Music/Fight
			// Theme.m4a").toString());
			// fightSong = new MediaPlayer(fightSongMedia);
			fightSong.setCycleCount(MediaPlayer.INDEFINITE);
		}

		// Play menu music and stop fighting music
		public static void playMenuMusic() {
			if (menuSong == null) {
				initMusic();
			}
			fightSong.stop();
			menuSong.play();
		}

		// Play fighting music and stop menu music
		public static void playFightMusic() {
			if (menuSong == null) {
				initMusic();
			}
			menuSong.stop();
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

//		private MenuSounds() {
//			Media buttonMousedOverMedia = new Media(AudioManager.class.getClassLoader()
//					.getResource("Audio/Menu Sounds/Button Moused Over.wav").toString());
//			buttonMousedOverSound = new MediaPlayer(buttonMousedOverMedia);
//			Media buttonClickedMedia = new Media(
//					AudioManager.class.getClassLoader().getResource("Audio/Menu Sounds/Button Clicked.wav").toString());
//			buttonClickedSound = new MediaPlayer(buttonClickedMedia);
//		}

		public static void playButtonMousedOverSound() {
			buttonMousedOverSound.stop();
			buttonMousedOverSound.play();
		}

		public static void playButtonClickedSound() {
			buttonClickedSound.stop();
			buttonClickedSound.play();
		}
	}

}
