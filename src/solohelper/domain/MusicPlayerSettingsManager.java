package solohelper.domain;

import solohelper.command.CommandConfigurations.Direction;
import solohelper.domain.MusicPlayerSettings.LoopingMode;

public interface MusicPlayerSettingsManager {

	void updateMusicPlayerSettingsForSlideWindow(Direction direction,
			int frameCount);

	void updateMusicPlayerSettingsForAlterWindow(Direction direction,
			int frameCount);

	void updateMusicPlayerSettingsForPause(int pauseMillis);

	MusicPlayerSettings getMusicPlayerSettings();

	void updateMusicPlayerSettingsLoopingMode(LoopingMode loopingMode);

}
