package solohelper.player;

import java.io.IOException;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import slash.metamusic.mp3.MP3File;
import solohelper.domain.MusicMetaInfo;

public class Mp3MusicMetaInfo implements MusicMetaInfo {

	private final String filePath;
	private int durationInSeconds;
	private String artist;
	private String album;
	private String track;

	@Inject
	public Mp3MusicMetaInfo(@Assisted("filePath") String filePath) {
		this.filePath = filePath;
		readMetaInfoFromFile();
	}
	
	public interface Factory {
		Mp3MusicMetaInfo create(@Assisted("filePath") String filePath);
	}
	
	@Override
	public String getFilePath() {
		return filePath;
	}
	
	private void readMetaInfoFromFile() {
		MP3File mp3File = new MP3File();
		try {
			mp3File.read(filePath);
			setDurationInSeconds(mp3File.getSeconds());
			setArtist(mp3File.getArtist());
			setAlbum(mp3File.getAlbum());
			setTrack(mp3File.getTrack());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	@Override
	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String getArtist() {
		return artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	@Override
	public String getAlbum() {
		return album;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	@Override
	public String getTrack() {
		return track;
	}
}
