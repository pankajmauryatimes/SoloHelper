package solohelper.domain;

import solohelper.command.CommandLibrary.CommandCode;


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

	void pause();

	void toggleLooping();

	void issueCommand(CommandCode command);
}
