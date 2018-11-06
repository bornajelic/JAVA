package pmf.phy.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;

public class ExitShellCommand implements ShellCommand {

	
	private static final String commandName = "exit";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {

			if (arguments != null) {
				env.writeln("Exit command does not expects any arguments.");
				return ShellStatus.CONTINUE;
			}

		} catch (IOException e) {
			throw new RuntimeException("Can not write to the environment.");
		}

		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Termination of a shell.");
		return Collections.unmodifiableList(description);
	}
}
