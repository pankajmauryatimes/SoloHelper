package solohelper.clip;

public interface MusicClipsManager {

	void saveClip(String clipLabel);

	void deleteClip(String clipLabel);

	void loadClip(String clipLabel);

	void showClips();

	void readClips(String configFileName);

	void writeClips(String configFileName);

}
