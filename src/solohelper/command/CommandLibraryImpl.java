package solohelper.command;

import java.util.StringTokenizer;

import javax.inject.Inject;

public class CommandLibraryImpl implements CommandLibrary {

	@Inject
	public CommandLibraryImpl() {
		// Do we need this?
	}
	
	@Override
	public CommandCode getAdvancedCommand(String commandString) {
		StringTokenizer tokenizer = new StringTokenizer(commandString);
		return CommandCode.getCommandForWord(tokenizer.nextToken());
	}
}
