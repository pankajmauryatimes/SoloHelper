package solohelper.command;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.inject.Inject;

import solohelper.command.CommandLibrary.CommandCode;

public class CommandInterpreterImpl implements CommandInterpreter {
	
	private BufferedReader bufferedReader;
	private final CommandLibrary commandLibrary;
	private String commandLine;

	@Inject
	public CommandInterpreterImpl(CommandLibrary commandLibrary) {
		this.commandLibrary = commandLibrary;
		BufferedInputStream bis = new BufferedInputStream(System.in);
		InputStreamReader reader = new java.io.InputStreamReader(bis);
        bufferedReader = new BufferedReader(reader);
	}
	
	@Override
	public void readCommandLine() throws IOException {
		commandLine = bufferedReader.readLine();
	}
	
	@Override
	public void loadCommandFromSession(String commandLine) {
		this.commandLine = commandLine;
	}
	
	@Override
	public boolean isValidCommand() {
		CommandCode commandCode = getCommandCode();
		
		if (commandCode == CommandCode.PRINT) {
			return true;
		} else {
			int expectedNumTokens = commandCode.getNumArguments() + 1;
			StringTokenizer tokenizer = new StringTokenizer(this.commandLine);
			if (tokenizer.countTokens() != expectedNumTokens) {
				System.out.println("Found tokens " + tokenizer.countTokens() + ", expected " + expectedNumTokens);
				return false;
			} else {
				return true;	
			}
		}
	}
	
	@Override
	public CommandCode getCommandCode() {
		return this.commandLibrary.getAdvancedCommand(this.commandLine);
	}
	
	@Override
	public CommandArgumentsImpl getCommandArguments() {
		return new CommandArgumentsImpl(getCommandCode(), this.commandLine);
	}
}
