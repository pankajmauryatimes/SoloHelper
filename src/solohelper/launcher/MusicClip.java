package solohelper.launcher;

import solohelper.domain.LoopingMode;
import solohelper.domain.MusicPlayerSettings;


/**
 * Represents a clip from a music file.
 * A clip is considered to have immutable properties.
 * 
 * Usually the clip is created from the {@link MusicPlayerSettings}.
 * 
 * @author pankajm
 */
public interface MusicClip {

	LoopingMode getLoopingMode();

	int getLoopingSliceFramesCount();

	int getStartFramePosition();
	
	int getPauseMillis();
	
	String getLabel();

	MusicPlayerSettings getMusicPlayerSettings();

	String getCsvString();
}
