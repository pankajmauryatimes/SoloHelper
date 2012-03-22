package solohelper.clip;

import java.util.Map;


public interface MusicClipsWriter {

	void writeClips(String configFileName, Map<String, MusicClip> clipMap);

}
