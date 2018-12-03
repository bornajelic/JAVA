package shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class HelpCommand implements ShellCommand{
	
	
	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		
		list_arguments = env.parameterisedInput(arguments);
		
		
		
	
		
		if(list_arguments.size() == 2 && list_arguments.get(0).equals(getCommandName())) {
			
			switch(list_arguments.get(1)) {
			case "charsets":
				env.writeln("Command charsets takes no arguments and lists names of supported charsets for your Java platform"
						+ " -see Charset.availableCharset() A single charset name is written per line.\n");
				break;
			case "cat":
				env.writeln("Command cat takes one or two arguments. The first argument is path to some file and is mandatory."
						+ " Thesecond argument is charset name that should be used to interpret chars from bytes. \n");
				break;
			case "ls":
				env.writeln("Command ls takes a single argument – directory – and writes a directory listing (not recursive)\n");
				break;
			case "tree":
				env.writeln("The tree command expects a single argument: directory name and prints a tree\n");
				break;
			case "copy":
				env.writeln("The copy command expects two arguments: source file name and destination file name (i.e. paths andnames).\n"
						+ "Is destination file exists, you should ask user is it allowed to overwrite it. \n");
				break;
			case "mkdir":
				env.writeln("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.\n");
				break;
			case "hexdump":
				env.writeln(" the hexdump command expects a single argument: file name, and produces hex-output as illustrated below. On the right side of the image only a standard subset of characters is shown;"
						+ " for all other characters a'.' is printed instead"
						+ " (i.e. replace all bytes whose value is less than 32 or greater than 127 with '.')\n");
				break;
			default:
				env.writeln("Wrong input");
				break;
			}
		}
		else if(list_arguments.size() == 1 && list_arguments.get(0).equals(getCommandName())) {
			
			
			env.writeln("Command charsets takes no arguments and lists names of supported charsets for your Java platform"
					+ " -see Charset.availableCharset() A single charset name is written per line.\n");
			env.writeln("Command cat takes one or two arguments. The first argument is path to some file and is mandatory."
					+ " Thesecond argument is charset name that should be used to interpret chars from bytes. \n");
			env.writeln("Command ls takes a single argument – directory – and writes a directory listing (not recursive)\n");
			env.writeln("The tree command expects a single argument: directory name and prints a tree\n");
			env.writeln("The copy command expects two arguments: source file name and destination file name (i.e. paths andnames).\n "
					+ "Is destination file exists, you should ask user is it allowed to overwrite it. ");
			env.writeln("The mkdir command takes a single argument: directory name,"
					+ " and creates the appropriate directory structure.\n");
			env.writeln("The hexdump command expects a single argument: file name, and produces hex-output as illustrated below."
					+ " On the right side of the image only a standard subset of characters is shown;"
					+ " for all other characters a'.' is printed instead\n"
					+ " (i.e. replace all bytes whose value is less than 32 or greater than 127 with '.')\n");
		}
		else {
			env.writeln("Wrong input!");
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
		descriptionOfCommands.add("You can use this command to get a basic information on every command, or a specific one"
				+ " if you want a specific one, type 'help ls', and if you want all, type 'help'");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
