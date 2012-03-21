package solohelper.launcher;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import solohelper.command.CommandArgumentsImpl;
import solohelper.command.CommandExecutor;
import solohelper.command.CommandInterpreter;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;
import solohelper.domain.MusicPlayerSettings;

public class SoloHelperImpl implements SoloHelper {

	private final MusicPlayer musicPlayer;
	private final CommandInterpreter commandInterpreter;
	private final CommandExecutor commandExecutor;
	private final Map<String, MusicClip> clipMap 
		= new TreeMap<String, MusicClip>();

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
			String configFileName = commandArguments.getArgumentsList().get(0);
			writeClips(configFileName);
		} else if (command == CommandCode.LOAD_INFO) {
			String configFileName = commandArguments.getArgumentsList().get(0);
			System.out.println("loading not yet implemented");
		} else {
			this.musicPlayer.issueCommand(command, commandArguments);	
		}
	}
	
	
	public void writeClips(String configFileName) {
		try {
			FileOutputStream fos = new FileOutputStream(configFileName);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        OutputStreamWriter osw = new OutputStreamWriter(bos);
	        BufferedWriter writer = new BufferedWriter(osw);
	        for (MusicClip clip : clipMap.values()) {
	        	writer.write(clip.toString());
	        }
	        writer.flush();
	        writer.close();	
		} catch (IOException e) {
			System.out.println("Failed to write file " + configFileName);
		}
		
	}
}
