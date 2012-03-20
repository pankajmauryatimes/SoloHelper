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
		soloHelper.openMusicFile(args[0]);
		soloHelper.process();
	}
}
