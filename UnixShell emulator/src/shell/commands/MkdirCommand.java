package shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class MkdirCommand implements ShellCommand{
	
	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		list_arguments = env.parameterisedInput(arguments);
		
	
		
		if(list_arguments.size() == 2 && list_arguments.get(0).equals(getCommandName())) {
			File file = new File(list_arguments.get(1));
			if(!file.exists()) {
				env.writeln("Creating directory " + file.getName());
				boolean result = false;
				
				try {
					file.mkdir();
					result = true;
				} catch (SecurityException se) {
					
				}
				if(result) {
					env.writeln("Direcotry created!");
				}
			}
			
		} else {
			env.writeln("Wrong input, expected command name and path");
		}
		list_arguments.clear();
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return this.commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> descriptionOfCommands = new ArrayList<String>();
		descriptionOfCommands.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
