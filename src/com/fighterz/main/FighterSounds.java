package com.fighterz.main;

import javafx.scene.media.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

public class FighterSounds {

	// Lines for individual characters. Initialized as static so they are only
	// loaded once.
	static final private Lines falessiLines = new Lines("Falessi");
	static final private Lines mammenLines = new Lines("Mammen");
	// Holds all lines for this fighter
	private Lines lines;
	// Ensures only one sound can play at a time
	private MediaPlayer playingSound = null;
	// Used to pick lines at random
	private Random random = new Random();
	// Plays idle lines at random times when nothing else is playing
	private IdleSoundManager idleSoundManager = new IdleSoundManager();
	private static boolean idleSoundIsPlaying = false;

	// Pass in either "Falessi" or "Mammen"
	public FighterSounds(String fighterName) throws NoSuchFighterException {
		if (fighterName.toLowerCase().equals("Falessi".toLowerCase())) {
			lines = falessiLines;
		} else if (fighterName.toLowerCase().equals("Mammen".toLowerCase())) {
			lines = mammenLines;
		} else {
			throw new NoSuchFighterException("Unable to create fighter with name \"" + fighterName + "\"");
		}
		idleSoundManager.start();
	}

	public class NoSuchFighterException extends Exception {

		private static final long serialVersionUID = 5311920362809035055L;

		public NoSuchFighterException(String message) {
			super(message);
		}
	}

	private static void addLinesFromFolderToArrayList(String folder, ArrayList<MediaPlayer> arr) {
		try {
			File[] files = new File(FighterSounds.class.getClassLoader().getResource(folder).toURI()).listFiles();
			for (File f : files) {
				arr.add(new MediaPlayer(new Media(f.toURI().toString())));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// MARK: Methods to play sounds
	public void playBeginGameSound() {
		playExclusiveRandomSoundFromArrayList(lines.beginGame);
	}

	public void playIdleSound() {
		playExclusiveRandomSoundFromArrayList(lines.idle);
	}

	public void playTakeDamageSound() {
		MediaPlayer takeDamageSound = getRandomSoundFromArrayList(lines.hit);
		takeDamageSound.stop();
		takeDamageSound.play();
		playExclusiveRandomSoundFromArrayList(lines.takeDamage);
	}

	public void playMeleSound() {
		playExclusiveRandomSoundFromArrayList(lines.mele);
	}

	public void playRangedSound() {
		playExclusiveRandomSoundFromArrayList(lines.ranged);
	}

	public void playJumpSound() {
		playExclusiveRandomSoundFromArrayList(lines.jump);
	}

	public void playVictorySound() {
		playExclusiveRandomSoundFromArrayList(lines.victory);
	}

	// Unexpected Behavior: Occasionally doesn't play sounds when called even if
	// index is generated
	private synchronized void playExclusiveRandomSoundFromArrayList(ArrayList<MediaPlayer> arr) {
		// Stop any sounds being played by this fighter before playing a new one
		if (playingSound != null) {
			playingSound.stop();
		}
		// Get and play new random sound
		playingSound = getRandomSoundFromArrayList(arr);
		playingSound.play();
	}

	private MediaPlayer getRandomSoundFromArrayList(ArrayList<MediaPlayer> arr) {
		int index;

		// If there's nothing in the given ArrayList, do nothing
		if (arr.size() == 0) {
			return null;
		}

		index = random.nextInt(arr.size());
		// Return random sound
		return arr.get(index);
	}

	private static class Lines {
		private ArrayList<MediaPlayer> beginGame, idle, takeDamage, mele, ranged, jump, victory, hit;

		// Almost a factory, but you don't need a factory this way
		public Lines(String fighterName) {
			beginGame = new ArrayList<>();
			idle = new ArrayList<>();
			takeDamage = new ArrayList<>();
			mele = new ArrayList<>();
			ranged = new ArrayList<>();
			jump = new ArrayList<>();
			victory = new ArrayList<>();
			hit = new ArrayList<>();

			addLinesFromFolderToArrayList("Audio/Fighter Sounds/Hit", hit);
			if (fighterName.toLowerCase().equals("Falessi".toLowerCase())) {
				// Initialize Falessi's lines
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Begin Game", beginGame);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Idle", idle);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Jump", jump);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Mele", mele);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Ranged", ranged);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Take Damage", takeDamage);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Victory", victory);
			} else if (fighterName.toLowerCase().equals("Mammen".toLowerCase())) {
				// Initialize Mammen's lines
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Begin Game", beginGame);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Idle", idle);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Jump", jump);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Mele", mele);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Ranged", ranged);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Take Damage", takeDamage);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Mammen/Victory", victory);
			} else {
				// I can't throw an exception because there's no way to deal with it when
				// assigning in a static context. This is a terrible solution, though.
				System.exit(1);
			}
		}
	}

	// Waits random amounts of time, then plays an idle sound if nothing else is
	// playing
	private class IdleSoundManager extends Thread {
		public void run() {
			while (true) {
				try {
					Thread.sleep((long) random.nextInt(9000) + 5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Play idle sound if nothing else is playing
				if (!idleSoundIsPlaying) {
					if (playingSound == null) {
						playIdleSound();
					} else if (playingSound.getCurrentTime().equals(playingSound.getStopTime())
							|| playingSound.getCurrentTime().toMillis() == 0.0) {
						playIdleSound();
					} else {
						// Sound shouldn't play because another idle sound is playing
					}
				}
			}
		}
	}
}
