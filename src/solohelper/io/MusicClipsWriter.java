package solohelper.io;

import java.util.Map;

import solohelper.launcher.MusicClip;

public interface MusicClipsWriter {

	void writeClips(String configFileName, Map<String, MusicClip> clipMap);

}
