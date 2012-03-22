package solohelper.launcher;

import java.io.IOException;

/**
 * Represents the solo helper.
 * 
 * @author pankajm
 */
public interface SoloHelper {

	void openMusicFile(String filePath);

	void process() throws IOException;

	void executeSession(String filePath);
}
