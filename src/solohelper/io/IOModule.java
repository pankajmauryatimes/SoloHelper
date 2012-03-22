package solohelper.io;

import com.google.inject.AbstractModule;

public class IOModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MusicClipsReader.class).to(MusicClipsReaderImpl.class);
		bind(MusicClipsWriter.class).to(MusicClipsWriterImpl.class);
	}

}
