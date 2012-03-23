package solohelper.command;

import javax.inject.Inject;

import solohelper.clip.MusicClipsManager;
import solohelper.command.CommandLibrary.CommandCode;
import solohelper.domain.MusicPlayer;
import solohelper.domain.StateOfPlay;

import com.google.inject.assistedinject.Assisted;

/**
 * Implementation of {@link CommandExecutor}.
 * 
 * @author pankajm
 */
public class CommandExecutorImpl implements CommandExecutor {
	
	private final MusicClipsManager musicClipsManager;
	private final MusicPlayer musicPlayer;
	private final String configFileName;
	
	@Inject
	public CommandExecutorImpl(
			MusicPlayer musicPlayer,
			MusicClipsManager musicClipsManager,
			@Assisted("configFileName") String configFileName) {
		this.musicPlayer = musicPlayer;
		this.musicClipsManager = musicClipsManager;
		this.configFileName = configFileName;
	}
	
	public interface Factory {
		CommandExecutorImpl create(@Assisted("configFileName") String configFileName);
	}
	
	@Override
	public void executeCommand(CommandCode command, CommandArgumentsImpl commandArguments) {
		if (command == CommandCode.QUIT) {
			System.exit(0);
		} else if (command == CommandCode.HELP) {
			for (CommandCode availableCommand : CommandCode.values()) {
				System.out.println(availableCommand + " " + availableCommand.getNumArguments() + " arguments " + availableCommand.getDescription());
			}
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
		} else if (command == CommandCode.WAIT_TILL_STATE_OF_PLAY) {
			StateOfPlay desiredStateOfPlay = StateOfPlay.valueOf(
					commandArguments.getArgumentsList().get(0));	
			while(true) {
				try {
					Thread.sleep(10);
					if (this.musicPlayer.getStateOfPlay() == desiredStateOfPlay) {
						System.out.println("Got desired state of play");
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if (command == CommandCode.SLEEP) {
			try {
				Thread.sleep(Integer.parseInt(commandArguments.getArgumentsList().get(0)));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (command == CommandCode.PRINT) {
			System.out.println(commandArguments.getArgumentsString());
		} else {
			this.musicPlayer.issueCommand(command, commandArguments);	
		}
	}
}
