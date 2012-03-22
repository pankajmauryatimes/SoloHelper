package solohelper.clip;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import solohelper.domain.MusicPlayer;

public class MusicClipsManagerImpl implements MusicClipsManager {
	private final Map<String, MusicClip> clipMap;
	private final MusicPlayer musicPlayer;
	private final MusicClipsReader musicClipsReader;
	private final MusicClipsWriter musicClipsWriter;

	@Inject
	public MusicClipsManagerImpl(
			MusicPlayer musicPlayer,
			MusicClipsReader musicClipsReader,
			MusicClipsWriter musicClipsWriter) {
		this.musicPlayer = musicPlayer;
		this.musicClipsReader = musicClipsReader;
		this.musicClipsWriter = musicClipsWriter;
		clipMap = new TreeMap<String, MusicClip>();
	}
	
	@Override
	public void saveClip(String clipLabel) {
		clipMap.put(clipLabel, 
				new MusicClipImpl(clipLabel, this.musicPlayer.getMusicPlayerSettings()));
	}

	@Override
	public void deleteClip(String clipLabel) {
		clipMap.remove(clipLabel);
	}
	
	@Override
	public void loadClip(String clipLabel) {
		MusicClip musicClip = clipMap.get(clipLabel);
		this.musicPlayer.setMusicPlayerSettings(musicClip.getMusicPlayerSettings());
	}
	
	@Override
	public void showClips() {
		for (MusicClip clip : clipMap.values()) {
        	System.out.println(clip.toString());
		}
	}
	
	@Override
	public void readClips(String configFileName) {
		clipMap.clear();
		clipMap.putAll(this.musicClipsReader.readClips(configFileName));
	}
	
	@Override
	public void writeClips(String configFileName) {
		this.musicClipsWriter.writeClips(configFileName, clipMap);
	}
}
