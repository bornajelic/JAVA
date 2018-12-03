package shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

/*
 * arguments are passed as string, so we use method from environment interface to
 * separate the input into 1,2 or 3 parts
 */

public class SymbolsCommand implements ShellCommand{
	
	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		list_arguments = env.parameterisedInput(arguments);
		
		if(list_arguments.size() <= 1 || list_arguments.size() >= 4) {
			
			env.write("Wrong number arguments for symbol-function\n");
			return ShellStatus.CONTINUE;
			
		} else {
			
			if(list_arguments.size() == 2) {
				
				switch(list_arguments.get(1)) {
				
					case "multiline":
						env.writeln("Symbol for MULTILINE is " + "'" + env.getMultilineSymbol() +"'");
						break;
					case "prompt":
						env.writeln("Symbol for PROMPT is " + "'" + env.getPromptSymbol() +"'");
						break;
					case "morelines":
						env.writeln("Symbol for MORELINES is " + "'" + env.getMorelinesSymbol() +"'");
						break;
				}
			}
			if(list_arguments.size() == 3){
				char newSymbol = list_arguments.get(2).charAt(0); 
				
				
				switch(list_arguments.get(1)) {
				
				case "multiline":
					
					char previousMultiline = env.getMultilineSymbol();
					env.setMultilineSymbol(newSymbol);
					env.writeln("Symbol for MULTILINE canged from " + "'" + previousMultiline +"'" + "to " + "'"+newSymbol+"'");
					break;
					
				case "prompt":
					
					char previousPrompt = env.getPromptSymbol();
					env.setPromptSymbol(newSymbol);
					env.writeln("Symbol for PROMPT canged from " + "'" + previousPrompt +"'" + "to " + "'"+newSymbol+"'");
					break;
				case "morelines":
					
					char previousMorelines = env.getMorelinesSymbol();
					env.setMorelinesSymbol(newSymbol);
					env.writeln("Symbol for MORELINES canged from " + "'" + previousMorelines +"'" + "to " + "'"+newSymbol+"'");
					break;
					
					default:
						env.writeln("Wrong input, expected MULTILINE,MORELINES,PROMPT");
			}
			}
			
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
		descriptionOfCommands.add("This command changes symbol to a desired one");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
