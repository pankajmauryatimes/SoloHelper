package solohelper.launcher;

import solohelper.domain.LoopingMode;
import solohelper.domain.MusicPlayerSettings;


public class MusicClipImpl implements MusicClip {
	private static final int minWindowSize = 10;
	private LoopingMode loopingMode;
	private int loopingSliceFrameCount;
	private int startFramePosition;
	private int pauseMillis;
	private String label;
	
	public MusicClipImpl(String clipLabel, MusicPlayerSettings musicPlayerSettings) {
		setLabel(clipLabel);
		setLoopingMode(musicPlayerSettings.getLoopingMode());
		setLoopingSliceFramesCount(musicPlayerSettings.getLoopingSliceFramesCount());
		setPauseMillis(musicPlayerSettings.getPauseMillis());
		setStartFramePosition(musicPlayerSettings.getStartFramePosition());
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
		return String.format("Clip label %s, Looping Mode %s, Window start %s, Window size %s, pause %s\n", 
			this.label, this.loopingMode, startFramePosition, getLoopingSliceFramesCount(), pauseMillis);
	}

	@Override
	public int getPauseMillis() {
		return pauseMillis;
	}

	@Override
	public void setPauseMillis(int pauseMillis) {
		this.pauseMillis = pauseMillis;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}
}
