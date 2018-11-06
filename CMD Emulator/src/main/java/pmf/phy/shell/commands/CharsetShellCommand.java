package pmf.phy.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;


public class CharsetShellCommand implements ShellCommand {

	
	private static final String commandName = "charset";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		try {

			if (arguments != null) {
				env.writeln("Charset command does not expect any arguments.");
			} else {
				Set<String> charsets = Charset.availableCharsets().keySet();
				for (String charset : charsets) {
					env.writeln(charset);
				}
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
		description.add("Lists names od supported charsets"
				+ " for currently  used Java platform.");
		return Collections.unmodifiableList(description);
	}

}
