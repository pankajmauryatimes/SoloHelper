package solohelper.player;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import solohelper.command.CommandConfigurations.Direction;
import solohelper.domain.MusicPlayerSettings;
import solohelper.domain.MusicPlayerSettingsManager;
import solohelper.domain.MusicPlayerSettings.LoopingMode;

public class MusicPlayerSettingsManagerImpl implements MusicPlayerSettingsManager {
	
	private final MusicPlayerSettings musicPlayerSettings;

	@Inject
	public MusicPlayerSettingsManagerImpl(@Assisted("settings") MusicPlayerSettings musicPlayerSettings) {
		this.musicPlayerSettings = musicPlayerSettings;
	}
	
	public interface Factory {
		MusicPlayerSettingsManagerImpl create(@Assisted("settings") MusicPlayerSettings musicPlayerSettings);
	}
	
	@Override
	public void updateMusicPlayerSettingsForSlideWindow(Direction direction, int frameCount) {
		int startFramePosition = this.getMusicPlayerSettings().getStartFramePosition();
		
		if (direction == Direction.FORWARD) {
			this.getMusicPlayerSettings().setStartFramePosition(
					startFramePosition + frameCount);
		} else {
			int safeStartFramePosition 
			= startFramePosition > frameCount ?  startFramePosition - frameCount : 0; 
			this.getMusicPlayerSettings().setStartFramePosition(safeStartFramePosition);
		}
	}
	
	@Override
	public void updateMusicPlayerSettingsForAlterWindow(Direction direction, int frameCount) {
		int startFramePosition = this.getMusicPlayerSettings().getStartFramePosition();
		int loopingSliceFramesCount = this.getMusicPlayerSettings().getLoopingSliceFramesCount();
		
		this.getMusicPlayerSettings().setLoopingSliceFramesCount(
				loopingSliceFramesCount + frameCount);
		
		if (direction == Direction.BACKWARD) {
			int safeStartFramePosition 
				= startFramePosition > frameCount ?  startFramePosition - frameCount : 0; 
			this.getMusicPlayerSettings().setStartFramePosition(safeStartFramePosition);

		} 
	}

	@Override
	public void updateMusicPlayerSettingsForPause(int pauseMillis) {
		this.getMusicPlayerSettings().setPauseMillis(pauseMillis);
	}
	
	@Override
	public void updateMusicPlayerSettingsLoopingMode(LoopingMode loopingMode) {
		this.getMusicPlayerSettings().setLoopingMode(loopingMode);
	}

	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		return musicPlayerSettings;
	}
}
