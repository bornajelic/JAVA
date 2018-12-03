package shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class ExitCommand implements ShellCommand {

	private String commandName = "exit";
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments != null) {
			env.writeln(arguments);
			env.write("GOODBYE!");
			return ShellStatus.TERMINATE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return this.commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptionOfCommands = new ArrayList<String>();
		descriptionOfCommands.add("This command is used to EXIT the shell");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
