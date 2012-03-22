package solohelper.io;

import java.util.Map;

import solohelper.launcher.MusicClip;

public interface MusicClipsReader {

	Map<String, MusicClip> readClips(String configFileName);

}
