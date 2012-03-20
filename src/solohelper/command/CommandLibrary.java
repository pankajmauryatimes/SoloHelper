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
		QUIT("quit"),
		TOGGLE_LOOP_MODE("loop");
		
		private final String commandWord;
		
		private CommandCode(String commandWord) {
			this.commandWord = commandWord;
		}
		
		public static CommandCode getCommandForWord(String word) {
			for (CommandCode advancedCommand : CommandCode.values()) {
				if (advancedCommand.commandWord.equalsIgnoreCase(word)) {
					return advancedCommand;
				}
			}
			return null;
		}
	}

	CommandCode getAdvancedCommand(String commandString);
}
