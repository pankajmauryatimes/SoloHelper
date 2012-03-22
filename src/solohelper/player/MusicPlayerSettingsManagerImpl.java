package solohelper.player;

import javax.inject.Inject;

import solohelper.command.CommandConfigurations.Direction;
import solohelper.domain.MusicPlayerSettings;
import solohelper.domain.MusicPlayerSettingsManager;

import com.google.inject.assistedinject.Assisted;

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
	public void updateSettingsForSlideWindow(Direction direction, int frameCount) {
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
	public void updateSettingsForAlterWindow(Direction direction, int frameCount) {
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
	public void updateSettingsForSetWindow(int startFramePosition,
			int frameCount) {
		this.getMusicPlayerSettings().setStartFramePosition(startFramePosition);
		this.getMusicPlayerSettings().setLoopingSliceFramesCount(frameCount);
	}

	@Override
	public void updateSettingsForPause(int pauseMillis) {
		this.getMusicPlayerSettings().setPauseMillis(pauseMillis);
	}
	
	@Override
	public void updateSettingsLoopingCount(int loopCount) {
		this.getMusicPlayerSettings().setLoopCount(loopCount);
	}

	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		return musicPlayerSettings;
	}
}
