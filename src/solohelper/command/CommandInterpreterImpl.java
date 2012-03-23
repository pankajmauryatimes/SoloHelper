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
		setCommandLine(bufferedReader.readLine());
	}
	
	@Override
	public void loadCommandFromSession(String commandLine) {
		this.setCommandLine(commandLine);
	}
	
	@Override
	public boolean isValidCommand() {
		if (isComment()) {
			return false;
		}
		
		if (getCommandLine().startsWith("#") || getCommandLine().startsWith("//")) {
			return false;
		}
		
		CommandCode commandCode = getCommandCode();
		
		if (commandCode == null) {
			return false;
		}
		
		if (commandCode == CommandCode.PRINT) {
			return true;
		} else {
			int expectedNumTokens = commandCode.getNumArguments() + 1;
			StringTokenizer tokenizer = new StringTokenizer(this.getCommandLine());
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
		return this.commandLibrary.getAdvancedCommand(this.getCommandLine());
	}
	
	@Override
	public CommandArgumentsImpl getCommandArguments() {
		return new CommandArgumentsImpl(getCommandCode(), this.getCommandLine());
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	@Override
	public String getCommandLine() {
		return commandLine;
	}
	
	private boolean isComment() {
		if (getCommandLine().startsWith("#") || getCommandLine().startsWith("//")) {
			return true;
		}
		return false;
	}

}
