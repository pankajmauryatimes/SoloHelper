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
	private int pauseMillis;
	
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
	
	@Override
	public String toString() {
		return String.format("Looping Mode %s, Window start %s, Window size %s, pause %s", 
			this.loopingMode, startFramePosition, getLoopingSliceFramesCount(), pauseMillis);
	}

	@Override
	public int getPauseMillis() {
		return pauseMillis;
	}

	@Override
	public void setPauseMillis(int pauseMillis) {
		this.pauseMillis = pauseMillis;
	}
}
