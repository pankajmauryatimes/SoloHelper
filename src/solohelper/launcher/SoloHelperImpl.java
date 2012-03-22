package solohelper.launcher;

import java.io.IOException;

import javax.inject.Inject;

import solohelper.command.CommandArgumentsImpl;
import solohelper.command.CommandExecutor;
import solohelper.command.CommandExecutorImpl;
import solohelper.command.CommandExecutorImpl.Factory;
import solohelper.command.CommandInterpreter;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;

public class SoloHelperImpl implements SoloHelper {

	private static final String CLIP_FILE_SUFFIX = ".solohelper.txt";
	private final MusicPlayer musicPlayer;
	private final CommandInterpreter commandInterpreter;
	private final Factory commandExecutorFactory;
	private CommandExecutor commandExecutor;

	@Inject
	public SoloHelperImpl(
		MusicPlayer musicPlayer,
		CommandInterpreter commandInterpreter,
		CommandExecutorImpl.Factory commandExecutorFactory) {
		this.musicPlayer = musicPlayer;
		this.commandInterpreter = commandInterpreter;
		this.commandExecutorFactory = commandExecutorFactory;
	}
	
	public void openSessionFile(String filePath) {
		
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
