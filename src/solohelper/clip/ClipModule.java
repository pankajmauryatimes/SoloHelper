package solohelper.clip;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ClipModule extends AbstractModule {

	@Override
	protected void configure() {
		// For now dont need to bind music clip to music clip impl as creation of music clip impl
		// is not by guice.
		bind(MusicClipsManager.class).to(MusicClipsManagerImpl.class)
			.in(Scopes.SINGLETON);
		bind(MusicClipsReader.class).to(MusicClipsReaderImpl.class);
		bind(MusicClipsWriter.class).to(MusicClipsWriterImpl.class);
	}

}
