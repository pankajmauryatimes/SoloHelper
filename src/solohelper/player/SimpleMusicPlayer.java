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
	private final MusicPlayerSettings musicPlayerSettings;
	private String filePath;

	@Inject
	public SimpleMusicPlayer(Mp3MusicFile.Factory musicFileFactory,
			Mp3Player mp3Player,
			AdvancedMp3Player advancedMp3Player,
			MusicPlayerSettings musicPlayerSettings) {
		this.musicFileFactory = musicFileFactory;
		this.advancedMp3Player = advancedMp3Player;
		this.musicPlayerSettings = musicPlayerSettings;
	}
	
	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		return this.musicPlayerSettings;
	}
	
	@Override
	public void loadMusicFile(String filePath) {
		this.filePath = filePath;
		mp3MusicFile = musicFileFactory.create(filePath);
		System.out.println("loaded a mp3 music file " + mp3MusicFile.getFilePath());
		this.advancedMp3Player.load(mp3MusicFile);
		this.advancedMp3Player.applyMusicPlayerSettings(musicPlayerSettings);
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
		this.musicPlayerSettings.setLoopingMode(loopingMode);
		this.advancedMp3Player.applyMusicPlayerSettings(musicPlayerSettings);
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
			// parse direction
			Direction direction = null;
			if (argumentsList.get(0).equalsIgnoreCase("forward")) {
				direction = CommandConfigurations.Direction.FORWARD;
			} else if (argumentsList.get(0).equalsIgnoreCase("backward")) {
				direction = CommandConfigurations.Direction.BACKWARD;
			}
			
			// frame count
			int frameCount = Integer.parseInt(argumentsList.get(1));
			updateMusicPlayerSettings(direction, frameCount);
			return;
		}
	}
	
	public void updateMusicPlayerSettings(Direction direction, int frameCount) {
		int startFramePosition = this.musicPlayerSettings.getStartFramePosition();
		
		if (direction == Direction.FORWARD) {
			this.musicPlayerSettings.setStartFramePosition(
					startFramePosition + frameCount);
		} else {
			int safeStartFramePosition 
			= startFramePosition > frameCount ?  startFramePosition - frameCount : 0; 
			this.musicPlayerSettings.setStartFramePosition(safeStartFramePosition);
		}
		this.advancedMp3Player.applyMusicPlayerSettings(musicPlayerSettings);
	}
	
	private void printInfo() {
		System.out.println("******************************");
		System.out.println(String.format("State of play [%s] \n Music player settings [%s]", 
			getStateOfPlay(), this.musicPlayerSettings));
		System.out.println("******************************");
	}
}
