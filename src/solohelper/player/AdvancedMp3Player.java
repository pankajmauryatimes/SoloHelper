package solohelper.player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import solohelper.domain.MusicFile;
import solohelper.domain.MusicPlayerSettings;
import solohelper.domain.StateOfPlay;

/**
 * Wrapper over a library player.
 * 
 * @author pankajm
 */
public class AdvancedMp3Player {

	private AdvancedPlayer player;
	private final ExecutorService executorService;
	private MusicFile musicFile;
	private StateOfPlay stateOfPlay;
	private PlaybackListener playbackListener;
	private MusicPlayerSettings musicPlayerSettings;

	@Inject
	public AdvancedMp3Player(ExecutorService executorService) {
		this.executorService = executorService;
		stateOfPlay = StateOfPlay.INACTIVE;
	}
		
	public void load(MusicFile musicFile) {
		this.musicFile = musicFile;
		String filePath = musicFile.getFilePath();
		try {
			FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new AdvancedPlayer(bis);
		} catch (Exception e) {
            System.out.println("Problem playing file " + filePath);
            System.out.println(e);
        }
	}
	
	public StateOfPlay getStateOfPlay() {
		return stateOfPlay;
	}
	
	public void applyMusicPlayerSettings(MusicPlayerSettings musicPlayerSettings) {
		this.musicPlayerSettings = musicPlayerSettings;
		int loopCount = musicPlayerSettings.getLoopCount();
		setPlayBackListener(new LoopingForCountTimesListener(loopCount));
	}
	
	private void setPlayBackListener(PlaybackListener playbackListener) {
		this.playbackListener = playbackListener;
		
		// Also update the player ?? do we check state ??
		this.player.setPlayBackListener(playbackListener);
	}
	
	public void play() {
		this.executorService.execute(
			new PlayMp3Task(musicPlayerSettings.getStartFramePosition(),
			musicPlayerSettings.getStartFramePosition() +
			musicPlayerSettings.getLoopingSliceFramesCount()));
	}


	public void loop() {
		close();
		load(this.musicFile);
		this.setPlayBackListener(playbackListener);
		pause();
		play();
	}
	
	public void pause() {
		this.executorService.execute(new PauseTask(
				musicPlayerSettings.getPauseMillis()));
	}
	
    public void close() { if (player != null) player.close(); }
	
	private class LoopingForCountTimesListener extends PlaybackListener {
		private final int loopCount;
		private int loopedCount = 0;

		public LoopingForCountTimesListener(int loopCount) {
			this.loopCount = loopCount;
		}
		
		@Override
		public void playbackFinished(PlaybackEvent playbackEvent) {
			super.playbackFinished(playbackEvent);
			System.out.println("playback finished");
			
			loopedCount++;
			System.out.println("looped count = " + loopedCount);
			
			System.out.println("loop count set = " + loopCount);
			
			if (loopedCount < loopCount) {
				loop();	
			}
		}
	}
    
    public class PauseTask implements Runnable {

    	private final long pauseMillis;

		public PauseTask(long pauseMillis) {
			this.pauseMillis = pauseMillis;
    	}
    	
		@Override
		public void run() {
			try {
				stateOfPlay = StateOfPlay.PAUSED;
				Thread.sleep(pauseMillis);
				stateOfPlay = StateOfPlay.INACTIVE;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    
    public class PlayMp3Task implements Runnable {
    	private final int startFrame;
		private final int endFrame;

		public PlayMp3Task(final int startFrame, final int endFrame) {
			this.startFrame = startFrame;
			this.endFrame = endFrame;
    	}
    	
		@Override
		public void run() {
			try { 
				System.out.println(
					String.format("Will play the window [%s, %s]", startFrame, endFrame));
				stateOfPlay = StateOfPlay.ACTIVE;
				player.play(startFrame, endFrame);
				stateOfPlay = StateOfPlay.INACTIVE;
			} catch (Exception e) { 
				System.out.println(e); 
			}			
		}
	}
}
