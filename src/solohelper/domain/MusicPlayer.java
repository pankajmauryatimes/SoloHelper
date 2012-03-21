package solohelper.domain;

import solohelper.command.CommandArguments;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayerSettings.LoopingMode;


/**
 * Represents a music player which can play a {@link MusicFile}.
 * 
 * @author pankajm
 */
public interface MusicPlayer {

	MusicPlayerSettings getMusicPlayerSettings();

	void loadMusicFile(String filePath);

	void play();
	
	StateOfPlay getStateOfPlay();

	void issueCommand(CommandCode command, CommandArguments commandArguments);
	
	void setLoopingMode(LoopingMode loopingMode);
}
