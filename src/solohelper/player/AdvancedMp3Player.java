package solohelper.player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicFile;

/**
 * Wrapper over a library player.
 * 
 * @author pankajm
 */
public class AdvancedMp3Player {

	private AdvancedPlayer player;
	private final ExecutorService executorService;
	private MusicFile musicFile;
	private static final long pauseMillis = 1000;
	private int startFramePosition;
	private int endFramePosition;
	
	@Inject
	public AdvancedMp3Player(ExecutorService executorService) {
		this.executorService = executorService;
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
	
	public void setPlayBackListener(PlaybackListener playbackListener) {
		this.player.setPlayBackListener(playbackListener);
	}

	public void play(int startFramePosition, int endFramePosition) {
		this.startFramePosition = startFramePosition;
		this.endFramePosition = endFramePosition;
		this.executorService.execute(
			new PlayMp3Task(startFramePosition, endFramePosition));
	}
	
	public void loop() {
		PlaybackListener playBackListener = this.player.getPlayBackListener();
		close();
		load(this.musicFile);
		this.setPlayBackListener(playBackListener);
		pause();
		play(startFramePosition, endFramePosition);
	}
	
	public void issueCommand(CommandCode command) {
		
	}
	
	public void pause() {
		this.executorService.execute(new PauseTask(pauseMillis));
	}
	
    public void close() { if (player != null) player.close(); }
    
    public class PauseTask implements Runnable {

    	private final long pauseMillis;

		public PauseTask(long pauseMillis) {
			this.pauseMillis = pauseMillis;
    	}
    	
		@Override
		public void run() {
			try {
				Thread.sleep(pauseMillis);
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
			try { player.play(startFrame, endFrame); }
            catch (Exception e) { System.out.println(e); }			
		}
	}
}
