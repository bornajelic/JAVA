package pmf.phy.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ArgumentExtractor;
import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;

public class HelpShellCommand implements ShellCommand {

	private static final String commandName = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {

			if (args.size() >= 0 && args.size() <= 1) {

				if (args.size() == 0) {
					for (ShellCommand command : env.commands()) {
						env.writeln(command.getCommandName());
					}
				} else {
					boolean exists = false;
					for (ShellCommand command : env.commands()) {
						if (command.getCommandName().equals(args.get(0))) {
							env.writeln("NAME: " + command.getCommandName());
							env.writeln("DESCRIPTION:");
							env.writeln(command.getCommandDescription().get(0));
							exists = true;
						}
					}
					if (!exists) {
						env.writeln("Command does not exists: " + args.get(1));
					}
				}

			} else {
				env.writeln("Help command expects one or two arguments.");
			}

		} catch (IOException e) {
			throw new RuntimeException("Can not write to the environment.");
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
		description.add("If started with no arguments,"
				+ " it list names of all supported"
				+ " commands. If started with single"
				+ " argument, it prints name and description"
				+ " of selected command.");
		return Collections.unmodifiableList(description);
	}
}
