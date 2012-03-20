package solohelper.command;

import java.io.IOException;

import solohelper.command.CommandLibrary.CommandCode;
import solohelper.launcher.SoloHelper;

/**
 * Represents the command interpreter to the {@link SoloHelper}.
 * 
 * @author pankajm
 */
public interface CommandInterpreter {

	void readCommandLine() throws IOException;

	boolean validateCommandLine();

	CommandCode getCommandCode();
}
