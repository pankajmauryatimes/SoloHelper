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
		
		if (args.length < 2 || args.length > 3) {
			System.out.println("Usage : arg1 = Mode [INTERACTIVE|SESSION], arg2 = [mp3 file path] , arg3 (only for session mode) = [session file path]");
			System.exit(0);
		}
		
		Mode mode = Mode.valueOf(args[0]);
		soloHelper.openMusicFile(args[1]);

		if (mode == Mode.INTERACTIVE) {
			soloHelper.process();
		} else {
			if (args.length != 3) {
				System.out.println("We need a session file argument");
				System.exit(0);
			}
			// load the session.
			soloHelper.executeSession(args[2]);
		}
		

	}
}
