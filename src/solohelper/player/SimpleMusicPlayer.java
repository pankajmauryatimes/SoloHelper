package solohelper.player;

import java.util.List;

import javax.inject.Inject;

import solohelper.command.CommandArguments;
import solohelper.command.CommandConfigurations;
import solohelper.command.CommandConfigurations.Direction;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;
import solohelper.domain.MusicPlayerSettings;
import solohelper.domain.MusicPlayerSettingsManager;
import solohelper.domain.StateOfPlay;
import solohelper.player.Mp3MusicFile.Factory;

/**
 * A simple music player.
 * 
 * @author pankajm
 */
public class SimpleMusicPlayer implements MusicPlayer {

	private final Factory musicFileFactory;
	private Mp3MusicFile mp3MusicFile;
	private final Mp3Player mp3Player;
	private String filePath;
	private MusicPlayerSettingsManager musicPlayerSettingsManager;
	private final solohelper.player.MusicPlayerSettingsManagerImpl.Factory musicPlayerSettingsManagerFactory;

	@Inject
	public SimpleMusicPlayer(Mp3MusicFile.Factory musicFileFactory,
			Mp3Player mp3Player,
			MusicPlayerSettings musicPlayerSettings,
			MusicPlayerSettingsManagerImpl.Factory musicPlayerSettingsManagerFactory) {
		this.musicFileFactory = musicFileFactory;
		this.mp3Player = mp3Player;
		this.musicPlayerSettingsManagerFactory = musicPlayerSettingsManagerFactory;
		this.musicPlayerSettingsManager = musicPlayerSettingsManagerFactory.create(musicPlayerSettings);
	}
	
	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		return this.musicPlayerSettingsManager.getMusicPlayerSettings();
	}
	
	@Override
	public void setMusicPlayerSettings(MusicPlayerSettings musicPlayerSettings) {
		this.musicPlayerSettingsManager = musicPlayerSettingsManagerFactory.create(musicPlayerSettings);
	}
	
	@Override
	public void loadMusicFile(String filePath) {
		this.filePath = filePath;
		mp3MusicFile = musicFileFactory.create(filePath);
		System.out.println("loaded a mp3 music file " + mp3MusicFile.getFilePath());
		this.mp3Player.load(mp3MusicFile);
		this.mp3Player.applyMusicPlayerSettings(
				musicPlayerSettingsManager.getMusicPlayerSettings());
	}
	
	@Override
	public void play() {
		System.out.println("Starting play.");
		if (this.musicPlayerSettingsManager.getMusicPlayerSettings().getLoopCount() > 1) {
			this.mp3Player.play(true /*is looping?*/);	
		} else {
			this.mp3Player.play(false /*is not looping?*/);
		}
		
	}
	
	@Override
	public StateOfPlay getStateOfPlay() {
		return this.mp3Player.getStateOfPlay();
	}
	
	@Override
	public void setLoopCount(int loopCount) {
		this.musicPlayerSettingsManager.updateSettingsLoopingCount(loopCount);
		this.mp3Player.applyMusicPlayerSettings(
				musicPlayerSettingsManager.getMusicPlayerSettings());
	}

	@Override
	public void issueCommand(CommandCode command, CommandArguments commandArguments) {
		List<String> argumentsList = commandArguments.getArgumentsList();
		
		if (command == CommandCode.LOOP) {
			Integer loopCount = Integer.parseInt(argumentsList.get(0));
			setLoopCount(loopCount);
			return;
		}
		
		if (command == CommandCode.PLAY) {
			// check the current state of play now.
			if (getStateOfPlay() == StateOfPlay.ACTIVE || 
					getStateOfPlay() == StateOfPlay.PAUSED) {
				return;
			} else {
				loadMusicFile(filePath);
				play();
			}
			return;
		}
		
		if (command == CommandCode.INFO) {
			printInfo();
			return;
		}
		
		if (command == CommandCode.SLIDE_WINDOW) {
			Direction direction = getDirection(argumentsList.get(0));
			int frameCount = Integer.parseInt(argumentsList.get(1));
			this.musicPlayerSettingsManager.updateSettingsForSlideWindow(direction, frameCount);
			this.mp3Player.applyMusicPlayerSettings(
					musicPlayerSettingsManager.getMusicPlayerSettings());
			return;
		}
		
		if (command == CommandCode.SET_PAUSE) {
			int pauseMillis = Integer.parseInt(argumentsList.get(0));
			this.musicPlayerSettingsManager.updateSettingsForPause(pauseMillis);
			this.mp3Player.applyMusicPlayerSettings(
					musicPlayerSettingsManager.getMusicPlayerSettings());
			return;
		}
		
		if (command == CommandCode.ALTER_WINDOW) {
			Direction direction = getDirection(argumentsList.get(0));
			int frameCount = Integer.parseInt(argumentsList.get(1));
			this.musicPlayerSettingsManager.updateSettingsForAlterWindow(direction, frameCount);
			this.mp3Player.applyMusicPlayerSettings(
					musicPlayerSettingsManager.getMusicPlayerSettings());
			return;
		}
		
		if (command == CommandCode.SET_WINDOW) {
			int start = Integer.parseInt(argumentsList.get(0));
			int frameCount = Integer.parseInt(argumentsList.get(1));
			this.musicPlayerSettingsManager.updateSettingsForSetWindow(
					start, frameCount);
			this.mp3Player.applyMusicPlayerSettings(
					musicPlayerSettingsManager.getMusicPlayerSettings());
			return;
		}
	}
	
	/*
	 * Parse direction
	 */
	private Direction getDirection(String directionString) {
		Direction direction = null;
		if (directionString.equalsIgnoreCase("forward")) {
			direction = CommandConfigurations.Direction.FORWARD;
		} else if (directionString.equalsIgnoreCase("backward")) {
			direction = CommandConfigurations.Direction.BACKWARD;
		}
		return direction;
	}
	
	private void printInfo() {
		System.out.println("******************************");
		System.out.println(String.format("State of play [%s] \n Music player settings [%s]", 
			getStateOfPlay(), this.musicPlayerSettingsManager.getMusicPlayerSettings()));
		System.out.println("******************************");
	}
}
