package solohelper.launcher;

import java.io.IOException;

import javax.inject.Inject;

import solohelper.clip.MusicClipsManager;
import solohelper.command.CommandArgumentsImpl;
import solohelper.command.CommandExecutor;
import solohelper.command.CommandInterpreter;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;

public class SoloHelperImpl implements SoloHelper {

	private static final String CLIP_FILE_SUFFIX = ".solohelper.txt";
	private final MusicPlayer musicPlayer;
	private final CommandInterpreter commandInterpreter;
	private final CommandExecutor commandExecutor;
	private String filePath;
	private String configFileName;
	private final MusicClipsManager musicClipsManager;

	@Inject
	public SoloHelperImpl(MusicPlayer musicPlayer,
		CommandInterpreter commandInterpreter,
		CommandExecutor commandExecutor,
		MusicClipsManager musicClipsManager) {
		this.musicPlayer = musicPlayer;
		this.commandInterpreter = commandInterpreter;
		this.commandExecutor = commandExecutor;
		this.musicClipsManager = musicClipsManager;
	}
	
	@Override
	public CommandInterpreter getCommandInterpreter() {
		return commandInterpreter;
	}
	
	@Override
	public void openMusicFile(String filePath) {
		this.filePath = filePath;
		this.musicPlayer.loadMusicFile(filePath);
		configFileName = this.filePath + CLIP_FILE_SUFFIX;
		this.musicClipsManager.readClips(configFileName);
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
		} else if (command == CommandCode.SAVE_CLIP) {
			String clipLabel = commandArguments.getArgumentsList().get(0);
			this.musicClipsManager.saveClip(clipLabel);
			return;
		} else if (command == CommandCode.DELETE_CLIP) {
			String clipLabel = commandArguments.getArgumentsList().get(0);
			this.musicClipsManager.deleteClip(clipLabel);
		} else if (command == CommandCode.LOAD_CLIP) {
			String clipLabel = commandArguments.getArgumentsList().get(0);
			this.musicClipsManager.loadClip(clipLabel);
			return;
		} else if (command == CommandCode.SAVE_INFO) {
			this.musicClipsManager.writeClips(configFileName);
		} else if (command == CommandCode.LOAD_INFO) {
			this.musicClipsManager.readClips(configFileName);
		} else if (command == CommandCode.SHOW_CLIPS) {
			this.musicClipsManager.showClips();
		} else {
			this.musicPlayer.issueCommand(command, commandArguments);	
		}
	}
}
