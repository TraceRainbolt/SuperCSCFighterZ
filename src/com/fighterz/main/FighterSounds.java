package com.fighterz.main;

import javafx.scene.media.*;
import javafx.util.Duration;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class FighterSounds {
	private static final Logger logger = Logger.getLogger(FighterSounds.class.getName());

	private static final String FALESSI_STR = "Falessi";
	private static final String MAMMEN_STR = "Mammen";

	// Holds all lines for this fighter
	private Lines lines;
	// Ensures only one sound can play at a time
	private MediaPlayer playingSound = null;
	private String myPlaySound = null;
	// Used to pick lines at random
	private Random random = new Random();
	// Plays idle lines at random times when nothing else is playing
	private IdleSoundManager idleSoundManager = new IdleSoundManager();
	private static boolean idleSoundIsPlaying = false;

	// Pass in either FALESSI_STR or MAMMEN_STR
	public FighterSounds(String fighterName) throws NoSuchFighterException {
		if (fighterName.equalsIgnoreCase(FALESSI_STR)) {
			lines = new Lines(FALESSI_STR);
		} else if (fighterName.equalsIgnoreCase(MAMMEN_STR)) {
			lines = new Lines(MAMMEN_STR);
		} else {
			throw new NoSuchFighterException("Unable to create fighter with name \"" + fighterName + "\"");
		}
		idleSoundManager.start();
		playBeginGameSound();
	}

	public class NoSuchFighterException extends Exception {

		private static final long serialVersionUID = 5311920362809035055L;

		public NoSuchFighterException(String message) {
			super(message);
		}
	}
	
	public String getPlayingSound() // for integration testing
	{
		return myPlaySound; //this gets set to the name of the last sound played
	}

	// MARK: Methods to play sounds
	public boolean playBeginGameSound() {
		playExclusiveRandomSoundFromArrayList(lines.beginGame);
		
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playIdleSound() {
		playExclusiveRandomSoundFromArrayList(lines.idle);
		
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playTakeDamageSound() {
		MediaPlayer takeDamageSound = getRandomSoundFromArrayList(lines.hit);
		if (takeDamageSound != null) {
			takeDamageSound.seek(Duration.ZERO);
			takeDamageSound.play();
		}
		playExclusiveRandomSoundFromArrayList(lines.takeDamage);
		
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playMeleSound() {
		playExclusiveRandomSoundFromArrayList(lines.mele);
		
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playRangedSound() {
		playExclusiveRandomSoundFromArrayList(lines.ranged);
		myPlaySound = "RangedSound";
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playJumpSound() {
		playExclusiveRandomSoundFromArrayList(lines.jump);
		
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playVictorySound() {
		playExclusiveRandomSoundFromArrayList(lines.victory);
		
		return playingSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean playTeleportSound() {
		MediaPlayer teleportSound = lines.teleport.get(0);
		teleportSound.seek(Duration.ZERO);
		teleportSound.play();
		
		return teleportSound.getStatus() == MediaPlayer.Status.PLAYING;
	}

	// Call to stop from playing idle sounds and properly shut down thread
	public boolean kill() {
		boolean res;
		logger.warning("Killing idle sound manager thread");
		res = idleSoundManager.kill();
		// Ensure no sounds continue to play
		playingSound.stop();
		
		return playingSound.getStatus() == MediaPlayer.Status.STOPPED && res;
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
		if (playingSound != null) {
			playingSound.seek(Duration.ZERO);
			playingSound.play();
		}
	}

	private MediaPlayer getRandomSoundFromArrayList(ArrayList<MediaPlayer> arr) {
		int index;

		// If there's nothing in the given ArrayList, do nothing
		if (arr.isEmpty()) {
			return null;
		}

		index = random.nextInt(arr.size());
		// Return random sound
		return arr.get(index);
	}

	private static class Lines {
		private ArrayList<MediaPlayer> beginGame;
		private ArrayList<MediaPlayer> idle;
		private ArrayList<MediaPlayer> takeDamage;
		private ArrayList<MediaPlayer> mele;
		private ArrayList<MediaPlayer> ranged;
		private ArrayList<MediaPlayer> jump;
		private ArrayList<MediaPlayer> victory;
		private ArrayList<MediaPlayer> hit;
		private ArrayList<MediaPlayer> teleport;

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
			teleport = new ArrayList<>();

			addLinesFromFolderToArrayList("Audio/Fighter Sounds/All/Teleport", teleport);
			addLinesFromFolderToArrayList("Audio/Fighter Sounds/All/Hit", hit);
			if (fighterName.equalsIgnoreCase(FALESSI_STR)) {
				// Initialize Falessi's lines
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Begin Game", beginGame);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Idle", idle);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Jump", jump);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Mele", mele);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Ranged", ranged);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Take Damage", takeDamage);
				addLinesFromFolderToArrayList("Audio/Fighter Sounds/Falessi/Victory", victory);
			} else if (fighterName.equalsIgnoreCase(MAMMEN_STR)) {
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

		private static void addLinesFromFolderToArrayList(String folder, ArrayList<MediaPlayer> arr) {
			try {
				File[] files = new File(FighterSounds.class.getClassLoader().getResource(folder).toURI()).listFiles();
				for (File f : files) {
					arr.add(new MediaPlayer(new Media(f.toURI().toString())));
				}
			} catch (URISyntaxException e) {
				logger.severe("Can't add lines from folder to array list -- " + e.getMessage());
				System.exit(1);
			}
		}
	}

	// Waits random amounts of time, then plays an idle sound if nothing else is
	// playing
	private class IdleSoundManager extends Thread {
		private boolean shouldRun = true;

		public boolean kill() {
			shouldRun = false;
			
			return true;
		}

		@Override
		public void run() {
			while (shouldRun) {
				// Wait for random amount of time
				try {
					Thread.sleep((long) random.nextInt(9000) + 5000);
				} catch (InterruptedException e) {
					logger.warning("IdleSoundManager's thread was interrupted: " + e.getMessage());
					// No longer smelly
					interrupt();
				}
				// Play idle sound if nothing else is playing
				if (!idleSoundIsPlaying && shouldRun) {
					if (playingSound == null) {
						playIdleSound();
					} else if (playingSound.getCurrentTime().equals(playingSound.getStopTime())
							|| playingSound.getCurrentTime().toMillis() == 0.0) {
						playIdleSound();
					} else {
						// Sound shouldn't play idle sound because another idle sound is playing
					}
				}
			}
		}
	}
}
