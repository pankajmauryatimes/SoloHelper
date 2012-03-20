package solohelper.launcher;

import java.io.IOException;

import solohelper.command.CommandInterpreter;

/**
 * Represents the solo helper.
 * 
 * @author pankajm
 */
public interface SoloHelper {

	CommandInterpreter getCommandInterpreter();

	void openMusicFile(String filePath);

	void playMusicFile();

	void process() throws IOException;
}
