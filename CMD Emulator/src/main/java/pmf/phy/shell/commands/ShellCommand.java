package pmf.phy.shell.commands;

import java.util.List;

import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;


public interface ShellCommand {

	ShellStatus executeCommand(Environment env, String arguments);

	
	String getCommandName();

	
	List<String> getCommandDescription();

}
