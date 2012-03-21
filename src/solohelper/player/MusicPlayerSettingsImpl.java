package solohelper.player;

import javax.inject.Inject;

import solohelper.domain.MusicPlayerSettings;

/**
 * Concrete implementation of {@link MusicPlayerSettings}.
 * 
 * @author pankajm
 */
public class MusicPlayerSettingsImpl implements MusicPlayerSettings {

	private static final int minWindowSize = 400;
	private LoopingMode loopingMode;
	private int loopingSliceFrameCount;
	private int startFramePosition;
	
	@Inject
	public MusicPlayerSettingsImpl() {
		loopingMode = LoopingMode.OFF;
	}
	
	@Override
	public LoopingMode getLoopingMode() {
		return this.loopingMode;
	}
	

	@Override
	public void setLoopingMode(LoopingMode loopingMode) {
		this.loopingMode = loopingMode;
	}
	
	@Override
	public void setLoopingSliceFramesCount(
			int loopingSliceFrameCount) {
		this.loopingSliceFrameCount = loopingSliceFrameCount;
	}
	
	@Override
	public int getLoopingSliceFramesCount() {
		if (this.loopingSliceFrameCount < minWindowSize) {
			return minWindowSize;
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
