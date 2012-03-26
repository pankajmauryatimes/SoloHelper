package solohelper.domain;

/**
 * Represents the inherent meta information related to the music associated
 * with the {@link MusicFile}.
 * 
 * @author pankajm
 */
public interface MusicMetaInfo {

	String getFilePath();

	int getDurationInSeconds();

	String getArtist();

	String getAlbum();

}
