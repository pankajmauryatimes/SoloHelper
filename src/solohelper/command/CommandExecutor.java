package solohelper.command;

import solohelper.command.CommandLibrary.CommandCode;

public interface CommandExecutor {

	void executeCommand(CommandCode command,
			CommandArgumentsImpl commandArguments);

}
