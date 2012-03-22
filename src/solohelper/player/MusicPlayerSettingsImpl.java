package solohelper.player;

import javax.inject.Inject;

import solohelper.domain.MusicPlayerSettings;

/**
 * Concrete implementation of {@link MusicPlayerSettings}.
 * 
 * @author pankajm
 */
public class MusicPlayerSettingsImpl implements MusicPlayerSettings {

	private static final int minWindowSize = 10;
	private int loopingSliceFrameCount;
	private int startFramePosition;
	private int pauseMillis;
	private int loopCount;
	
	@Inject
	public MusicPlayerSettingsImpl() {
		this.loopCount = 1;
		startFramePosition = 0;
		loopingSliceFrameCount = minWindowSize;
	}
	
	@Override
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	@Override
	public int getLoopCount() {
		return this.loopCount;
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
		return String.format("Looping count %s, Window start %s, Window size %s, pause %s", 
			this.loopCount, startFramePosition, getLoopingSliceFramesCount(), pauseMillis);
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
