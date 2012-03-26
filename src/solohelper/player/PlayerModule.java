package solohelper.player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import solohelper.domain.MusicFile;
import solohelper.domain.MusicMetaInfo;
import solohelper.domain.MusicPlayer;
import solohelper.domain.MusicPlayerSettings;
import solohelper.domain.MusicPlayerSettingsManager;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Guice bindings for player package of solo helper.
 * 
 * @author pankajm
 */
public class PlayerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MusicPlayer.class).to(SimpleMusicPlayer.class)
			.in(Scopes.SINGLETON);
		
		bind(MusicPlayerSettings.class).to(MusicPlayerSettingsImpl.class);
		
		install(new FactoryModuleBuilder()
			.implement(MusicFile.class, Mp3MusicFile.class)
			.build(Mp3MusicFile.Factory.class));
		
		install(new FactoryModuleBuilder()
			.implement(MusicMetaInfo.class, Mp3MusicMetaInfo.class)
			.build(Mp3MusicMetaInfo.Factory.class));
		
		install(new FactoryModuleBuilder()
			.implement(MusicPlayerSettingsManager.class, MusicPlayerSettingsManagerImpl.class)
			.build(MusicPlayerSettingsManagerImpl.Factory.class));
		
		bind(ExecutorService.class)
			.toInstance(Executors.newSingleThreadExecutor());
	}
}
