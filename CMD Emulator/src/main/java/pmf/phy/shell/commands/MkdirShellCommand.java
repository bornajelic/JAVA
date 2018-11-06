package pmf.phy.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ArgumentExtractor;
import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;


public class MkdirShellCommand implements ShellCommand {

	
	private static final String commandName = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {
			if (args.size() == 1) {

				Path dir = Paths.get(args.get(0));

				dir.toFile().mkdirs();

				env.writeln("Directory structure has been created.");

			} else {
				env.writeln("Mkdir command expects one argument.");
			}
		} catch (IOException e) {

		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Creates the given directory structure.");
		return Collections.unmodifiableList(description);
	}

}
