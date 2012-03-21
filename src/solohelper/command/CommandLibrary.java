package solohelper.command;

/**
 * Represents the library of commands.
 * We intend to support the following list of commands:
 * 
 * loop on/off
 * expand_window forward/backward <frame_count>
 * contract_window forward/backward <frame_count>
 * play
 * slide_window forward/backward <frame_count>
 * save_window label
 * goto_window label
 * info
 * save_info
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
		 * Change the loop mode to on or off depending on the argument.
		 */
		TOGGLE_LOOP_MODE("loop", 1);
		
		
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
