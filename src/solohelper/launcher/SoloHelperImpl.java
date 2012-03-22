package solohelper.launcher;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import solohelper.command.CommandArgumentsImpl;
import solohelper.command.CommandExecutor;
import solohelper.command.CommandExecutorImpl;
import solohelper.command.CommandExecutorImpl.Factory;
import solohelper.command.CommandInterpreter;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;
import solohelper.io.FileReader;

public class SoloHelperImpl implements SoloHelper {

	private static final String CLIP_FILE_SUFFIX = ".solohelper.txt";
	private final MusicPlayer musicPlayer;
	private final CommandInterpreter commandInterpreter;
	private final Factory commandExecutorFactory;
	private CommandExecutor commandExecutor;
	private final FileReader fileReader;

	@Inject
	public SoloHelperImpl(
		MusicPlayer musicPlayer,
		CommandInterpreter commandInterpreter,
		CommandExecutorImpl.Factory commandExecutorFactory,
		FileReader fileReader) {
		this.musicPlayer = musicPlayer;
		this.commandInterpreter = commandInterpreter;
		this.commandExecutorFactory = commandExecutorFactory;
		this.fileReader = fileReader;
	}
	
	@Override
	public void executeSession(String filePath) {
		List<String> sessionContents = fileReader.readLines(filePath);
		for (String command : sessionContents) {
			commandInterpreter.loadCommandFromSession(command);
			CommandCode advancedCommand = commandInterpreter.getCommandCode();
			CommandArgumentsImpl commandArguments = commandInterpreter.getCommandArguments();
			this.commandExecutor.executeCommand(advancedCommand, commandArguments);
		}
	}
	
	@Override
	public void openMusicFile(String filePath) {
		this.musicPlayer.loadMusicFile(filePath);
		String configFileName = filePath + CLIP_FILE_SUFFIX;
		commandExecutor = this.commandExecutorFactory.create(configFileName);
	}
	
	@Override
	public void process() throws IOException {
		do {
			System.out.println("Enter command :");
			commandInterpreter.readCommandLine();
			CommandCode advancedCommand = commandInterpreter.getCommandCode();
			CommandArgumentsImpl commandArguments = commandInterpreter.getCommandArguments();
			this.commandExecutor.executeCommand(advancedCommand, commandArguments);
		} while (true);
	}
}
