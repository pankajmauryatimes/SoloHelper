package solohelper.launcher;

import java.util.StringTokenizer;

import solohelper.domain.LoopingMode;
import solohelper.domain.MusicPlayerSettings;
import solohelper.player.MusicPlayerSettingsImpl;

/**
 * Implementation of {@link MusicClip}.
 * 
 * @author pankajm
 */
public class MusicClipImpl implements MusicClip {
	private static final int minWindowSize = 10;
	private final LoopingMode loopingMode;
	private final int loopingSliceFrameCount;
	private final int startFramePosition;
	private final int pauseMillis;
	private final String label;
	
	public MusicClipImpl(String clipLabel, MusicPlayerSettings musicPlayerSettings) {
		this.label = clipLabel;
		this.loopingMode = musicPlayerSettings.getLoopingMode();
		this.loopingSliceFrameCount = musicPlayerSettings.getLoopingSliceFramesCount();
		this.pauseMillis = musicPlayerSettings.getPauseMillis();
		this.startFramePosition = musicPlayerSettings.getStartFramePosition();
	}
	
	@Override
	public String getCsvString() {
		return String.format("%s,%s,%s,%s,%s\n", 
			this.label, this.loopingMode, startFramePosition, loopingSliceFrameCount, pauseMillis);
	}
	
	public MusicClipImpl(String csvString) {
		StringTokenizer tokenizer = new StringTokenizer(csvString, ",");
		this.label = tokenizer.nextToken();
		this.loopingMode = LoopingMode.valueOf(tokenizer.nextToken());
		this.startFramePosition = Integer.parseInt(tokenizer.nextToken());
		this.loopingSliceFrameCount = Integer.parseInt(tokenizer.nextToken());
		this.pauseMillis = Integer.parseInt(tokenizer.nextToken());
	}
	
	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		MusicPlayerSettingsImpl settings = new MusicPlayerSettingsImpl();
		settings.setLoopingMode(loopingMode);
		settings.setLoopingSliceFramesCount(loopingSliceFrameCount);
		settings.setPauseMillis(pauseMillis);
		settings.setStartFramePosition(startFramePosition);
		return settings;
	}
	
	@Override
	public LoopingMode getLoopingMode() {
		return this.loopingMode;
	}

	@Override
	public int getLoopingSliceFramesCount() {
		if (this.loopingSliceFrameCount < minWindowSize) {
			return minWindowSize;
		}
		return this.loopingSliceFrameCount;
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
	public String getLabel() {
		return label;
	}
}
