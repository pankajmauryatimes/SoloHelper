package solohelper.domain;

/**
 * Represents a music file which can be played as per properties of the file
 * and the player.
 * 
 * @author pankajm
 */
public interface MusicFile {

	UserMetaInfo getUserMetaInfo();
	
	MusicMetaInfo getMusicMetaInfo();

	String getFilePath();
}
