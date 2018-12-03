package shell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import shell.commands.CatCommand;
import shell.commands.CharsetCommand;
import shell.commands.CopyCommand;
import shell.commands.ExitCommand;
import shell.commands.HelpCommand;
import shell.commands.HexDumpCommand;
import shell.commands.LsCommand;
import shell.commands.MkdirCommand;
import shell.commands.SymbolsCommand;
import shell.commands.TreeCommand;

public class Main {

	public static void main(String[] args) throws IOException {
		
		List<String> list_arguments = new ArrayList<String>();
 		Environment env = new Shell();
 		Environment env2 = new Shell();
 		
 		//used just for extracting input in main, if i used env, insted of env2, 
 		//in etc HelpCommand, list_arguments would have had double the size it needed
 		
		env.writeln("Welcome to myShell v.1.0 by Borna Jelic");
		
		ShellStatus status = ShellStatus.CONTINUE;
		ShellCommand command;
		
		
		while(status == ShellStatus.CONTINUE) {
			
			String input = utilityFunction(env);
			list_arguments = env2.parameterisedInput(input);
			String commandName = list_arguments.get(0);
			command = env.commands().get(commandName);
			status = command.executeCommand(env, input);
			if(status == ShellStatus.TERMINATE) {
				break;
			}
			list_arguments.clear();
		}
		
		
	}
		
	/*
	 * method regulates input, handles multilines,morelines symbols
	 * returns a string witch is then sent to commandClass.
	 * In commandClass, method parameterisedInput() is called.
	 * It generates a list of string, it basically breakes down a string input
	 * that is sent to multiple parts to it is easier to operate 
	 * 
	 */
	public static String utilityFunction(Environment env) { //ovu funckciju saljem commandi
		
		StringBuilder sb = new StringBuilder();
		env.write(env.getPromptSymbol() + " ");
		while (true) {
		
			String str = env.readLine();
			sb.append(str);
			if (str.charAt(str.length() - 1) == '\\') {
				env.write(env.getMultilineSymbol() + " ");
				sb.setLength(sb.length() - 1);
				continue;
			} else {
				break;
			}

		}
		
		char[] c = sb.toString().toCharArray();
		sb.setLength(0);
		int currentIndex = 0;
	
		while(currentIndex < c.length) {
			if(c[currentIndex] == '\\'){
				sb.append(c[currentIndex]);
				sb.append(c[currentIndex++]);
			}
			 
			else {
				sb.append(c[currentIndex++]);
			}
		}
		return sb.toString().toLowerCase();
	}

}
