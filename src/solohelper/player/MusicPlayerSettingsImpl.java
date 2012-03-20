package solohelper.player;

import javax.inject.Inject;

import solohelper.domain.MusicPlayerSettings;

/**
 * Concrete implementation of {@link MusicPlayerSettings}.
 * 
 * @author pankajm
 */
public class MusicPlayerSettingsImpl implements MusicPlayerSettings {

	private LoopingMode loopingMode;
	private int loopingSliceFrameCount;
	private int startFramePosition;
	
	@Inject
	public MusicPlayerSettingsImpl() {
		loopingMode = LoopingMode.OFF;
	}
	
	@Override
	public void toggleLooping() {
		if (this.loopingMode == LoopingMode.OFF) {
			this.loopingMode = LoopingMode.ON;
		} else {
			this.loopingMode = LoopingMode.OFF;			
		}
	}
	
	@Override
	public LoopingMode getLoopingMode() {
		return this.loopingMode;
	}
	
	@Override
	public void setLoopingSliceFramesCount(
			int loopingSliceFrameCount) {
		this.loopingSliceFrameCount = loopingSliceFrameCount;
	}
	
	@Override
	public int getLoopingSliceFramesCount() {
		if (this.loopingSliceFrameCount < 100) {
			return 100;
		}
		return this.loopingSliceFrameCount;
	}
	
	@Override
	public void setStartFramePosition(int startFramePosition) {
		this.startFramePosition = startFramePosition;
	}
	
	@Override
	public int getStartFramePosition() {
		return this.startFramePosition;
	}
}
