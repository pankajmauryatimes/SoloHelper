package solohelper.io;

import java.util.Map;

import solohelper.clip.MusicClip;

public interface MusicClipsReader {

	Map<String, MusicClip> readClips(String configFileName);

}
