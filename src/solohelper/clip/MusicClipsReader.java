package solohelper.clip;

import java.util.Map;


public interface MusicClipsReader {

	Map<String, MusicClip> readClips(String configFileName);

}
