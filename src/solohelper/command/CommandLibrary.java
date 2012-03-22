package solohelper.command;

/**
 * Represents the library of commands.
 * We intend to support the following list of commands:
 * 
 * loop on/off
 * alter_window forward/backward <frame_count>
 * play
 * slide_window forward/backward <frame_count>
 * save_clip label
 * load_clip label
 * info
 * save_info filename
 * load_info filename
 * set_pause millis
 * 
 * @author pankajm
 */
public interface CommandLibrary {
	
	public enum CommandCode {
		/**
		 * Quits the SoloHelper program.
		 */
		QUIT("quit", 0),
		
		/**
		 * Will start playing over the last known window if
		 * the current state of the play is inactive. No effect
		 * if playback is already happening.
		 */
		PLAY("play", 0),
		
		/**
		 * Get current information about the player, window etc.
		 */
		INFO("info", 0),
		
		/**
		 * Slides the current window in the given direction by the
		 * given frame count. On hitting a boundary on start,
		 * the slide stops.
		 */
		SLIDE_WINDOW("slide_window", 2),
		
		/**
		 * Loop the current window n times.
		 */
		LOOP("loop", 1),
		
		/**
		 * Sets the looping pause when the window end is reached before
		 * resuming playback.
		 */
		SET_PAUSE("set_pause", 1),
		
		/**
		 * Alters the current window in the given direction by the
		 * given frame count. Cannot alter in back before 0 count.
		 */
		ALTER_WINDOW("alter_window", 2),
		
		/**
		 * Sets the current window to the given parameters.
		 * First param = start position.
		 * Second param = size of window. 
		 */
		SET_WINDOW("set_window", 2),
		
		/**
		 * Saves the current music player settings with the given label.
		 */
		SAVE_CLIP("save_clip", 1),
		
		/**
		 * Deletes the clip with the given label from the in memory loaded map.
		 * If followed by a save_info, will result in deleting the clip from the
		 * clips file.
		 */
		DELETE_CLIP("delete_clip", 1),
		
		/**
		 * Loads the music player settings previously saved with the given label.
		 */
		LOAD_CLIP("load_clip", 1),
		
		/**
		 * Saves all the currently saved in-memory music player settings
		 * to a file.
		 */
		SAVE_INFO("save_info", 0),
		
		/**
		 * Loads the info saved previously in a file.
		 */
		LOAD_INFO("load_info", 0),
		
		/**
		 * Shows all clips currently saved in current project.
		 */
		SHOW_CLIPS("show_clips", 0),
		
		/**
		 * A useful command which blocks the command executor until the state of play
		 * is the state in the first argument.
		 */
		WAIT_TILL_STATE_OF_PLAY("till_sop", 1),
		
		/**
		 * Will cause the command executor to sleep for argument millis of secs.
		 * Useful in session mode.
		 */
		SLEEP("sleep", 1),
		
		/**
		 * A command to print a single word on the output console for debugging.
		 * Useful in session mode.
		 * The argument count is not used for print command as we may have any number
		 * of words to print. getArgumentsList is not used for print execution.
		 */
		PRINT("print", 0);
		
		private final String commandWord;
		private final int numArguments;
		
		private CommandCode(String commandWord, int numArguments) {
			this.commandWord = commandWord;
			this.numArguments = numArguments;
		}
		
		public static CommandCode getCommandForWord(String word) {
			for (CommandCode advancedCommand : CommandCode.values()) {
				if (advancedCommand.commandWord.equalsIgnoreCase(word)) {
					return advancedCommand;
				}
			}
			return null;
		}

		public int getNumArguments() {
			return numArguments;
		}
	}

	CommandCode getAdvancedCommand(String commandString);
}
