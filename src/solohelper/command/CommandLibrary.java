package solohelper.command;

/**
 * Represents the library of commands.
 * 
 * @author pankajm
 */
public interface CommandLibrary {
	
	public enum CommandCode {
		/**
		 * Quits the SoloHelper program.
		 */
		QUIT("quit", 0, "quits the solo helper program."),
		
		/**
		 * Will start playing over the last known window if
		 * the current state of the play is inactive. No effect
		 * if playback is already happening.
		 */
		PLAY("play", 0, "starts the play over the current window with current settings. Run info to see what will be played"),
		
		/**
		 * Get current information about the player, window etc.
		 */
		INFO("info", 0, "get information about current window and settings"),
		
		/**
		 * Slides the current window in the given direction by the
		 * given frame count. On hitting a boundary on start,
		 * the slide stops.
		 */
		SLIDE_WINDOW("slide_window", 2, "Format: slide_window forward|backward slide_amount. Slides the window"),
		
		/**
		 * Loop the current window n times.
		 */
		LOOP("loop", 1, "Format: loop n. Sets the loop counter to n for the current window."),
		
		/**
		 * Sets the looping pause when the window end is reached before
		 * resuming playback.
		 */
		SET_PAUSE("set_pause", 1, "Format: set_pause pause_millis. Sets the looping pause when window end is reached."),
		
		/**
		 * Alters the current window in the given direction by the
		 * given frame count. Cannot alter in back before 0 count.
		 */
		ALTER_WINDOW("alter_window", 2, "Format: alter_window forward|backward frame_count. Alters the window in given direction by given amount."),
		
		/**
		 * Sets the current window to the given parameters.
		 * First param = start position.
		 * Second param = size of window. 
		 */
		SET_WINDOW("set_window", 2, "Format: set_window start_frame_position size_of_window. Sets the window to given arguments"),
		
		/**
		 * Saves the current music player settings with the given label.
		 */
		SAVE_CLIP("save_clip", 1, "Format: save_clip clip_label. Saves the current settings as a clip."),
		
		/**
		 * Deletes the clip with the given label from the in memory loaded map.
		 * If followed by a save_info, will result in deleting the clip from the
		 * clips file.
		 */
		DELETE_CLIP("delete_clip", 1, "Format: delete_clip clip_label. Deletes the clip with given label"),
		
		/**
		 * Loads the music player settings previously saved with the given label.
		 */
		LOAD_CLIP("load_clip", 1, "Format: load_clip clip_label. Loads the named clip."),
		
		/**
		 * Saves all the currently saved in-memory music player settings
		 * to a file.
		 */
		SAVE_INFO("save_info", 0, "Saves all the clips"),
		
		/**
		 * Loads the info saved previously in a file.
		 */
		LOAD_INFO("load_info", 0, "loads all the clips"),
		
		/**
		 * Shows all clips currently saved in current project.
		 */
		SHOW_CLIPS("show_clips", 0, "print all the clips"),
		
		/**
		 * A useful command which blocks the command executor until the state of play
		 * is the state in the first argument.
		 */
		WAIT_TILL_STATE_OF_PLAY("till_sop", 1, "Format: till_sop ACTIVE|INACTIVE|PAUSED. Causes command executor to block till given state."),
		
		/**
		 * Will cause the command executor to sleep for argument millis of secs.
		 * Useful in session mode.
		 */
		SLEEP("sleep", 1, "Format: sleep sleep_millis. Causes command executor to sleep for given millis"),
		
		/**
		 * A command to print a single word on the output console for debugging.
		 * Useful in session mode.
		 * The argument count is not used for print command as we may have any number
		 * of words to print. getArgumentsList is not used for print execution.
		 */
		PRINT("print", 0, "Format: print <any long string with any num of words>. Prints it"),
		
		/**
		 * Simply prints out the list of supported commands.
		 */
		HELP("help", 0, "Prints out this list");
		
		private final String commandWord;
		private final int numArguments;
		private final String description;
		
		private CommandCode(String commandWord, int numArguments, String description) {
			this.commandWord = commandWord;
			this.numArguments = numArguments;
			this.description = description;
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

		public String getDescription() {
			return description;
		}
	}

	CommandCode getAdvancedCommand(String commandString);
}
