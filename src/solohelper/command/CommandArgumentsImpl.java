package solohelper.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import solohelper.command.CommandLibrary.CommandCode;

/**
 * Encapsulates the argument information for a command.
 * 
 * @author pankajm
 */
public class CommandArgumentsImpl implements CommandArguments {

	private final String commandLine;
	private final CommandCode commandCode;

	public CommandArgumentsImpl(CommandCode commandCode, String commandLine) {
		this.commandCode = commandCode;
		this.commandLine = commandLine;
	}
	
	@Override
	public List<String> getArgumentsList() {
		int numArguments = this.commandCode.getNumArguments();
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		
		// the command code
		tokenizer.nextToken();
		
		List<String> argumentStrings = new ArrayList<String>();
		for (int i = 1; i <= numArguments; i++) {
			argumentStrings.add(tokenizer.nextToken());
		}
		return argumentStrings;
	}
	
	@Override
	public String getArgumentsString() {
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		tokenizer.nextToken();
		return commandLine.substring(tokenizer.nextToken().length() - 1);
	}

}
