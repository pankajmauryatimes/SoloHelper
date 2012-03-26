package solohelper.player;

import javax.inject.Inject;

import solohelper.domain.MusicFile;
import solohelper.domain.MusicMetaInfo;
import solohelper.domain.UserMetaInfo;

import com.google.inject.assistedinject.Assisted;

public class Mp3MusicFile implements MusicFile {

	private final String filePath;
	private final Mp3MusicMetaInfo musicMetaInfo;

	@Inject
	public Mp3MusicFile(@Assisted("filePath") String filePath,
			Mp3MusicMetaInfo.Factory factory) {
		this.filePath = filePath;
		musicMetaInfo = factory.create(filePath);
	}
	
	public interface Factory {
		Mp3MusicFile create(@Assisted("filePath") String filePath);
	}
	
	@Override
	public UserMetaInfo getUserMetaInfo() {
		return null;
	}

	@Override
	public MusicMetaInfo getMusicMetaInfo() {
		return musicMetaInfo;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

}
