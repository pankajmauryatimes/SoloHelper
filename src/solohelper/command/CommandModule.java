package solohelper.command;

import com.google.inject.AbstractModule;

/**
 * Guice bindings for command classes.
 * 
 * @author pankajm
 */
public class CommandModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CommandInterpreter.class).to(CommandInterpreterImpl.class);
		bind(CommandLibrary.class).to(CommandLibraryImpl.class);
		bind(CommandExecutor.class).to(CommandExecutorImpl.class);
	}
}
