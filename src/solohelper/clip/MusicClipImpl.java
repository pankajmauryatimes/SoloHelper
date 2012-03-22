package solohelper.clip;

import java.util.StringTokenizer;

import solohelper.domain.MusicPlayerSettings;
import solohelper.player.MusicPlayerSettingsImpl;

/**
 * Implementation of {@link MusicClip}.
 * 
 * @author pankajm
 */
public class MusicClipImpl implements MusicClip {
	private static final int minWindowSize = 10;
	private final int loopingSliceFrameCount;
	private final int startFramePosition;
	private final int pauseMillis;
	private final String label;
	private final int loopCount;
	
	public MusicClipImpl(String clipLabel, MusicPlayerSettings musicPlayerSettings) {
		this.label = clipLabel;
		this.loopCount = musicPlayerSettings.getLoopCount();
		this.loopingSliceFrameCount = musicPlayerSettings.getLoopingSliceFramesCount();
		this.pauseMillis = musicPlayerSettings.getPauseMillis();
		this.startFramePosition = musicPlayerSettings.getStartFramePosition();
	}
	
	@Override
	public String getCsvString() {
		return String.format("%s,%s,%s,%s,%s\n", 
			this.label, this.loopCount, startFramePosition, loopingSliceFrameCount, pauseMillis);
	}
	
	public MusicClipImpl(String csvString) {
		StringTokenizer tokenizer = new StringTokenizer(csvString, ",");
		this.label = tokenizer.nextToken();
		this.loopCount = Integer.parseInt(tokenizer.nextToken());
		this.startFramePosition = Integer.parseInt(tokenizer.nextToken());
		this.loopingSliceFrameCount = Integer.parseInt(tokenizer.nextToken());
		this.pauseMillis = Integer.parseInt(tokenizer.nextToken());
	}
	
	@Override
	public MusicPlayerSettings getMusicPlayerSettings() {
		MusicPlayerSettingsImpl settings = new MusicPlayerSettingsImpl();
		settings.setLoopCount(loopCount);
		settings.setLoopingSliceFramesCount(loopingSliceFrameCount);
		settings.setPauseMillis(pauseMillis);
		settings.setStartFramePosition(startFramePosition);
		return settings;
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
		return String.format("Clip label %s, Loop Count %s, Window start %s, Window size %s, pause %s\n", 
			this.label, this.loopCount, startFramePosition, getLoopingSliceFramesCount(), pauseMillis);
	}

	@Override
	public int getPauseMillis() {
		return pauseMillis;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public int getLoopCount() {
		return loopCount;
	}
}
