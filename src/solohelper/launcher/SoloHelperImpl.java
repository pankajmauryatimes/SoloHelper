package solohelper.launcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

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
	private final Map<String, MusicClip> clipMap 
		= new TreeMap<String, MusicClip>();
	private String filePath;
	private String configFileName;

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
		this.filePath = filePath;
		this.musicPlayer.loadMusicFile(filePath);
		configFileName = this.filePath + CLIP_FILE_SUFFIX;
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
			writeClips(configFileName);
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
		try {
			clipMap.clear();
			FileInputStream fis = new FileInputStream(configFileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis);
			BufferedReader br = new BufferedReader(isr);
			
			do {
				String line = br.readLine();
				if (line == null) {
					return;
				}
				MusicClipImpl clip = new MusicClipImpl(line);
				clipMap.put(clip.getLabel(), clip);
			} while (true);
		} catch (IOException e) {
			System.out.println("Failed to read file " + configFileName);
		}
	}
	
	public void writeClips(String configFileName) {
		try {
			FileOutputStream fos = new FileOutputStream(configFileName, true /*append*/);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        OutputStreamWriter osw = new OutputStreamWriter(bos);
	        BufferedWriter writer = new BufferedWriter(osw);
	        for (MusicClip clip : clipMap.values()) {
	        	writer.write(clip.getCsvString());
	        }
	        writer.flush();
	        writer.close();	
		} catch (IOException e) {
			System.out.println("Failed to write file " + configFileName);
		}
		
	}
}
