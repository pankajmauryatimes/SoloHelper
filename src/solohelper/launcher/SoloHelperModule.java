package solohelper.launcher;

import solohelper.command.CommandModule;
import solohelper.player.PlayerModule;

import com.google.inject.AbstractModule;

/**
 * Guice bindings for {@link SoloHelper}.
 * 
 * @author pankajm
 */
public class SoloHelperModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SoloHelper.class).to(SoloHelperImpl.class);
		install(new PlayerModule());
		install(new CommandModule());
	}

}
