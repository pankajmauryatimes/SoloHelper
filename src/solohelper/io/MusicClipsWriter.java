package solohelper.io;

import java.util.Map;

import solohelper.clip.MusicClip;

public interface MusicClipsWriter {

	void writeClips(String configFileName, Map<String, MusicClip> clipMap);

}
