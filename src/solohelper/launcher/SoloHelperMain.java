package solohelper.launcher;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main entry point for the SoloHelper program.
 * 
 * @author pankajm
 */
public class SoloHelperMain {

	public static void main(String[] args) throws IOException {
		Injector injector = Guice.createInjector(new SoloHelperModule());
		SoloHelper soloHelper = injector.getInstance(SoloHelper.class);
		
		if (args.length != 2) {
			System.out.println("Usage : arg1 = Mode [INTERACTIVE|SESSION], arg2 = [mp3 file path or session file path]");
			System.exit(0);
		}
		
		Mode mode = Mode.valueOf(args[0]);
		
		if (mode == Mode.INTERACTIVE) {
			soloHelper.openMusicFile(args[1]);
			soloHelper.process();
		} else {
			// load the session.
		}
		

	}
}
