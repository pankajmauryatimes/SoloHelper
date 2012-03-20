package solohelper.player;

import javax.inject.Inject;

import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
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
	private final Mp3Player mp3Player;
	private final AdvancedMp3Player advancedMp3Player;
	private StateOfPlay stateOfPlay;
	private final MusicPlayerSettings musicPlayerSettings;

	@Inject
	public SimpleMusicPlayer(Mp3MusicFile.Factory musicFileFactory,
			Mp3Player mp3Player,
			AdvancedMp3Player advancedMp3Player,
			MusicPlayerSettings musicPlayerSettings) {
		this.musicFileFactory = musicFileFactory;
		this.mp3Player = mp3Player;
		this.advancedMp3Player = advancedMp3Player;
		this.musicPlayerSettings = musicPlayerSettings;
		stateOfPlay = StateOfPlay.INACTIVE;
	}
	
	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		return this.musicPlayerSettings;
	}
	
	@Override
	public void loadMusicFile(String filePath) {
		mp3MusicFile = musicFileFactory.create(filePath);
		System.out.println("loaded a mp3 music file " + mp3MusicFile.getFilePath());
//		this.mp3Player.load(mp3MusicFile);
		this.advancedMp3Player.load(mp3MusicFile);
	}
	
	@Override
	public void play() {
		stateOfPlay = StateOfPlay.ACTIVE;
		this.advancedMp3Player.play(
				this.musicPlayerSettings.getStartFramePosition(),
				this.musicPlayerSettings.getStartFramePosition() +
				this.musicPlayerSettings.getLoopingSliceFramesCount()
				);
//		this.mp3Player.play();
	}
	
	public void loop() {
		stateOfPlay = StateOfPlay.ACTIVE;
		this.advancedMp3Player.loop();
	}
	
	@Override
	public void pause() {
		stateOfPlay = StateOfPlay.INACTIVE;
//		int positionMillis = this.mp3Player.getPositionMillis();
//		System.out.println("Current position = " + positionMillis);
	}

	@Override
	public StateOfPlay getStateOfPlay() {
		return stateOfPlay;
	}
	
	@Override
	public void toggleLooping() {
		this.musicPlayerSettings.toggleLooping();
		
		if (this.musicPlayerSettings.getLoopingMode() == LoopingMode.ON) {
			setPlayBackListener(new LoopingListener());
		} else {
			setPlayBackListener(new NonLoopingListener());
		}
	}

	@Override
	public void issueCommand(CommandCode command) {
		if (command == CommandCode.TOGGLE_LOOP_MODE) {
			toggleLooping();
		}

		this.advancedMp3Player.issueCommand(command);
	}
	
	private void setPlayBackListener(PlaybackListener playbackListener) {
		this.advancedMp3Player.setPlayBackListener(playbackListener);
	}
	
	private class LoopingListener extends PlaybackListener {
		@Override
		public void playbackFinished(PlaybackEvent playbackEvent) {
			super.playbackFinished(playbackEvent);
			loop();
		}
		
		@Override
		public void playbackStarted(PlaybackEvent playbackEvent) {
			super.playbackStarted(playbackEvent);
			// nothing to do for now.
		}
	}
	
	private class NonLoopingListener extends PlaybackListener {
	}
}
