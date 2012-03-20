package solohelper.player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import solohelper.domain.MusicFile;
import solohelper.domain.MusicPlayer;
import solohelper.domain.MusicPlayerSettings;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Guice bindings for player package of solo helper.
 * 
 * @author pankajm
 */
public class PlayerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MusicPlayer.class).to(SimpleMusicPlayer.class);
		bind(MusicPlayerSettings.class).to(MusicPlayerSettingsImpl.class);
		install(new FactoryModuleBuilder()
			.implement(MusicFile.class, Mp3MusicFile.class)
			.build(Mp3MusicFile.Factory.class));
		bind(ExecutorService.class)
			.toInstance(Executors.newSingleThreadExecutor());
	}
}