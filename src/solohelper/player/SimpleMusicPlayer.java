package solohelper.player;

import java.util.List;

import javax.inject.Inject;

import solohelper.command.CommandArguments;
import solohelper.command.CommandConfigurations;
import solohelper.command.CommandConfigurations.Direction;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;
import solohelper.domain.MusicPlayerSettings;
import solohelper.domain.MusicPlayerSettings.LoopingMode;
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
	private final AdvancedMp3Player advancedMp3Player;
//	private final MusicPlayerSettings musicPlayerSettings;
	private String filePath;
	private final MusicPlayerSettingsManager musicPlayerSettingsManager;

	@Inject
	public SimpleMusicPlayer(Mp3MusicFile.Factory musicFileFactory,
			Mp3Player mp3Player,
			AdvancedMp3Player advancedMp3Player,
			MusicPlayerSettings musicPlayerSettings,
			MusicPlayerSettingsManagerImpl.Factory musicPlayerSettingsManagerFactory) {
		this.musicFileFactory = musicFileFactory;
		this.advancedMp3Player = advancedMp3Player;
//		this.musicPlayerSettings = musicPlayerSettings;
		this.musicPlayerSettingsManager = musicPlayerSettingsManagerFactory.create(musicPlayerSettings);
	}
	
	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		return this.musicPlayerSettingsManager.getMusicPlayerSettings();
	}
	
	@Override
	public void loadMusicFile(String filePath) {
		this.filePath = filePath;
		mp3MusicFile = musicFileFactory.create(filePath);
		System.out.println("loaded a mp3 music file " + mp3MusicFile.getFilePath());
		this.advancedMp3Player.load(mp3MusicFile);
		this.advancedMp3Player.applyMusicPlayerSettings(
				musicPlayerSettingsManager.getMusicPlayerSettings());
	}
	
	@Override
	public void play() {
		System.out.println("Starting play.");
		this.advancedMp3Player.play();
	}
	
	@Override
	public StateOfPlay getStateOfPlay() {
		return this.advancedMp3Player.getStateOfPlay();
	}
	
	@Override
	public void setLoopingMode(LoopingMode loopingMode) {
		this.musicPlayerSettingsManager.updateMusicPlayerSettingsLoopingMode(loopingMode);
		this.advancedMp3Player.applyMusicPlayerSettings(
				musicPlayerSettingsManager.getMusicPlayerSettings());
	}

	@Override
	public void issueCommand(CommandCode command, CommandArguments commandArguments) {
		List<String> argumentsList = commandArguments.getArgumentsList();
		
		if (command == CommandCode.TOGGLE_LOOP_MODE) {
			// we get the desired loop mode from argument 1
			if (argumentsList.get(0).equalsIgnoreCase("on")) {
				setLoopingMode(LoopingMode.ON);
			} else if (argumentsList.get(0).equalsIgnoreCase("off")) {
				setLoopingMode(LoopingMode.OFF);
			}
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
			this.musicPlayerSettingsManager.updateMusicPlayerSettingsForSlideWindow(direction, frameCount);
			this.advancedMp3Player.applyMusicPlayerSettings(
					musicPlayerSettingsManager.getMusicPlayerSettings());
			return;
		}
		
		if (command == CommandCode.SET_PAUSE) {
			int pauseMillis = Integer.parseInt(argumentsList.get(0));
			this.musicPlayerSettingsManager.updateMusicPlayerSettingsForPause(pauseMillis);
			this.advancedMp3Player.applyMusicPlayerSettings(
					musicPlayerSettingsManager.getMusicPlayerSettings());
			return;
		}
		
		if (command == CommandCode.ALTER_WINDOW) {
			Direction direction = getDirection(argumentsList.get(0));
			int frameCount = Integer.parseInt(argumentsList.get(1));
			this.musicPlayerSettingsManager.updateMusicPlayerSettingsForAlterWindow(direction, frameCount);
			this.advancedMp3Player.applyMusicPlayerSettings(
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
