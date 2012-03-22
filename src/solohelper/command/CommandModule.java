package solohelper.command;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

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
		
		install(new FactoryModuleBuilder()
			.implement(CommandExecutor.class, CommandExecutorImpl.class)
			.build(CommandExecutorImpl.Factory.class));
	}
}
