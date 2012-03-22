package solohelper.domain;

import solohelper.command.CommandConfigurations.Direction;

/**
 * Manages a music player settings.
 * 
 * @author pankajm
 */
public interface MusicPlayerSettingsManager {

	MusicPlayerSettings getMusicPlayerSettings();
	
	void updateSettingsForSlideWindow(Direction direction, int frameCount);

	void updateSettingsForAlterWindow(Direction direction, int frameCount);

	void updateSettingsForPause(int pauseMillis);

	void updateSettingsLoopingCount(int loopCount);

	void updateSettingsForSetWindow(int startFramePosition, int frameCount);
}
