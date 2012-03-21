package solohelper.launcher;

import java.io.IOException;

import javax.inject.Inject;

import solohelper.command.CommandArgumentsImpl;
import solohelper.command.CommandExecutor;
import solohelper.command.CommandInterpreter;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;

public class SoloHelperImpl implements SoloHelper {

	private final MusicPlayer musicPlayer;
	private final CommandInterpreter commandInterpreter;
	private final CommandExecutor commandExecutor;

	@Inject
	public SoloHelperImpl(MusicPlayer musicPlayer,
			CommandInterpreter commandInterpreter,
			CommandExecutor commandExecutor) {
		this.musicPlayer = musicPlayer;
		this.commandInterpreter = commandInterpreter;
		this.commandExecutor = commandExecutor;
	}
	
	@Override
	public CommandInterpreter getCommandInterpreter() {
		return commandInterpreter;
	}
	
	@Override
	public void openMusicFile(String filePath) {
		this.musicPlayer.loadMusicFile(filePath);
	}
	
	@Override
	public void playMusicFile() {
		musicPlayer.play();
	}
	
	@Override
	public void process() throws IOException {
		do {
			System.out.println("Enter command :");
			commandInterpreter.readCommandLine();
			CommandCode advancedCommand = commandInterpreter.getCommandCode();
			CommandArgumentsImpl commandArguments = commandInterpreter.getCommandArguments();
			executeCommand(advancedCommand, commandArguments);
		} while (true);
	}
	
	public void executeCommand(CommandCode command, CommandArgumentsImpl commandArguments) {
		if (command == CommandCode.QUIT) {
			System.exit(0);
		} else {
			this.musicPlayer.issueCommand(command, commandArguments);	
		}
	}
}
