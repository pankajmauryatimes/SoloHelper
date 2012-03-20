package solohelper.player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import javazoom.jl.player.Player;
import solohelper.domain.MusicFile;
import solohelper.player.AdvancedMp3Player.PlayMp3Task;

/**
 * Wrapper over a library player.
 * 
 * @author pankajm
 */
public class Mp3Player {

	private Player player;
	private final ExecutorService executorService;
		
	@Inject
	public Mp3Player(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	public void load(MusicFile musicFile) {
		String filePath = musicFile.getFilePath();
		try {
			FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
		} catch (Exception e) {
            System.out.println("Problem playing file " + filePath);
            System.out.println(e);
        }
	}
	
	public void play() {
		this.executorService.execute(new PlayMp3Task());
	}
	
    public void close() { if (player != null) player.close(); }
    
    public class PlayMp3Task implements Runnable {

		@Override
		public void run() {
			try { player.play(); }
            catch (Exception e) { System.out.println(e); }			
		}
	}
    
    public int getPositionMillis() {
    	int position = player.getPosition();
    	return position;
    }
}
