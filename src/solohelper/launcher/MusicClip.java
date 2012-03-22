package solohelper.launcher;

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

	int getLoopingSliceFramesCount();

	int getStartFramePosition();
	
	int getPauseMillis();
	
	String getLabel();

	MusicPlayerSettings getMusicPlayerSettings();

	String getCsvString();
	
	int getLoopCount();
}
