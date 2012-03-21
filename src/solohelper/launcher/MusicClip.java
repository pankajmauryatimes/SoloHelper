package solohelper.launcher;

import solohelper.domain.LoopingMode;
import solohelper.domain.MusicPlayerSettings;


/**
 * Represents a clip from a music file.
 * 
 * @author pankajm
 */
public interface MusicClip {

	void setLoopingMode(LoopingMode loopingMode);

	LoopingMode getLoopingMode();

	void setLoopingSliceFramesCount(int loopingSliceFrameCount);

	int getLoopingSliceFramesCount();

	void setStartFramePosition(int startFramePosition);

	int getStartFramePosition();
	
	int getPauseMillis();
	
	void setPauseMillis(int pauseMillis);
	
	String getLabel();

	void setLabel(String label);

	MusicPlayerSettings getMusicPlayerSettings();
}
