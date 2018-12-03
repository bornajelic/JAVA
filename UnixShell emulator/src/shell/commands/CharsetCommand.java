package shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class CharsetCommand implements ShellCommand {

	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "charset";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		list_arguments = env.parameterisedInput(arguments);
		
		
		
		if(list_arguments.size() == 1 && list_arguments.get(0).equals(this.commandName)) {
			
			

			Map<String, Charset> map = Charset.availableCharsets();

			for (String key : map.keySet()) {
				env.writeln(key);
			}
		} else {
			env.writeln("Function CHARSETS takes no arguments || expected 'charset' ");
		}
		list_arguments.clear();
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
		descriptionOfCommands.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform ");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
