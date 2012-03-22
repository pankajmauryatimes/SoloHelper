package solohelper.launcher;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import solohelper.command.CommandArgumentsImpl;
import solohelper.command.CommandExecutor;
import solohelper.command.CommandInterpreter;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;
import solohelper.io.MusicClipsReader;
import solohelper.io.MusicClipsWriter;

public class SoloHelperImpl implements SoloHelper {

	private static final String CLIP_FILE_SUFFIX = ".solohelper.txt";
	private final MusicPlayer musicPlayer;
	private final CommandInterpreter commandInterpreter;
	private final CommandExecutor commandExecutor;
	private final Map<String, MusicClip> clipMap = new TreeMap<String, MusicClip>();
	private String filePath;
	private String configFileName;
	private final MusicClipsReader musicClipsReader;
	private final MusicClipsWriter musicClipsWriter;

	@Inject
	public SoloHelperImpl(MusicPlayer musicPlayer,
		CommandInterpreter commandInterpreter,
		CommandExecutor commandExecutor,
		MusicClipsReader musicClipsReader,
		MusicClipsWriter musicClipsWriter) {
		this.musicPlayer = musicPlayer;
		this.commandInterpreter = commandInterpreter;
		this.commandExecutor = commandExecutor;
		this.musicClipsReader = musicClipsReader;
		this.musicClipsWriter = musicClipsWriter;
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
		readClips(configFileName);
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
			clipMap.put(clipLabel, 
				new MusicClipImpl(clipLabel, this.musicPlayer.getMusicPlayerSettings()));
			return;
		} else if (command == CommandCode.LOAD_CLIP) {
			String clipLabel = commandArguments.getArgumentsList().get(0);
			MusicClip musicClip = clipMap.get(clipLabel);
			this.musicPlayer.setMusicPlayerSettings(musicClip.getMusicPlayerSettings());
			return;
		} else if (command == CommandCode.SAVE_INFO) {
			this.musicClipsWriter.writeClips(configFileName, clipMap);
		} else if (command == CommandCode.LOAD_INFO) {
			readClips(configFileName);
		} else if (command == CommandCode.SHOW_CLIPS) { 
			for (MusicClip clip : clipMap.values()) {
	        	System.out.println(clip.toString());
	        }
		} else {
			this.musicPlayer.issueCommand(command, commandArguments);	
		}
	}
	
	public void readClips(String configFileName) {
		clipMap.clear();
		clipMap.putAll(this.musicClipsReader.readClips(configFileName));
	}
}
