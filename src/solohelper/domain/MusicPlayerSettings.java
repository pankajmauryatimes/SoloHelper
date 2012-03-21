
package solohelper.domain;


/**
 * Represents the global settings of the {@link MusicPlayer}.
 * 
 * @author pankajm
 */
public interface MusicPlayerSettings {

	void setLoopingMode(LoopingMode loopingMode);

	LoopingMode getLoopingMode();

	void setLoopingSliceFramesCount(int loopingSliceFrameCount);

	int getLoopingSliceFramesCount();

	void setStartFramePosition(int startFramePosition);

	int getStartFramePosition();
	
	int getPauseMillis();
	
	void setPauseMillis(int pauseMillis);
}
