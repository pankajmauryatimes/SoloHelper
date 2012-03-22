
package solohelper.domain;

/**
 * Represents the global settings of the {@link MusicPlayer}.
 * 
 * @author pankajm
 */
public interface MusicPlayerSettings {

	void setLoopingSliceFramesCount(int loopingSliceFrameCount);

	int getLoopingSliceFramesCount();

	void setStartFramePosition(int startFramePosition);

	int getStartFramePosition();
	
	int getPauseMillis();
	
	void setPauseMillis(int pauseMillis);

	int getLoopCount();

	void setLoopCount(int loopCount);
}
