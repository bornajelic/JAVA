package shell;

import java.util.List;

/*
 * Each Shell operation, such as cat,tree,copy... implements
 * this interface
 */

public interface ShellCommand {

	ShellStatus executeCommand(Environment env, String arguments);
	
	String getCommandName();
	
	List<String> getCommandDescription();
	
}
