
package solohelper.domain;

/**
 * Represents the global settings of the {@link MusicPlayer}.
 * 
 * @author pankajm
 */
public interface MusicPlayerSettings {
	public enum LoopingMode {
		ON,
		OFF;
	}

	void setLoopingMode(LoopingMode loopingMode);

	LoopingMode getLoopingMode();

	void setLoopingSliceFramesCount(int loopingSliceFrameCount);

	int getLoopingSliceFramesCount();

	void setStartFramePosition(int startFramePosition);

	int getStartFramePosition();
}
