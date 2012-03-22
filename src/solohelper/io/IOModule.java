package solohelper.io;

import com.google.inject.AbstractModule;

public class IOModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FileReader.class).to(FileReaderImpl.class);
		bind(FileWriter.class).to(FileWriterImpl.class);
	}

}
