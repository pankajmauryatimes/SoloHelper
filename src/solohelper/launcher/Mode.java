package solohelper.launcher;

/**
 * An enumeration of the various modes in which SoloHelper
 * program can run.
 * 
 * @author pankajm
 */
public enum Mode {

	/**
	 * An interactive mode in which the solo helper takes
	 * command line inputs from the user.
	 */
	INTERACTIVE,
	
	/**
	 * Session mode in which the solo helper reads the commands
	 * from a session file.
	 */
	SESSION;
}
