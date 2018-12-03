package shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;



public class TreeCommand implements ShellCommand {
	
	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		list_arguments = env.parameterisedInput(arguments);
		
	
		
		//System.out.println("0  element is "+ list_arguments.get(0));
		//System.out.println("1  element is "+  list_arguments.get(1));
		
		
		if(list_arguments.get(0).equals(this.commandName)) {
			
			if(list_arguments.size() == 2) {
				File f = new File(list_arguments.get(1));
				env.writeln(printDirectoryTree(f));
			} else {
				env.writeln("Wrong number of arguments");
			}
		}else {
			env.writeln("Wrong name of function, expected 'tree'");
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
		descriptionOfCommands.add("This command is uset to EXIT the shell");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}
	
	private static String printDirectoryTree(File folder) {
		if(!folder.isDirectory()) {
			throw new IllegalArgumentException();
		}
		int indent = 0;
		StringBuilder sb = new StringBuilder();
		printDirectoryTree(folder,indent,sb);
		return sb.toString();
	}
	
	private static void printDirectoryTree(File folder,int indent,StringBuilder sb) {
		if(!folder.isDirectory()) {
			throw new IllegalArgumentException();
		}
		sb.append(getIndentString(indent));
		sb.append("+--");
		sb.append(folder.getName());
		 sb.append("/");
		    sb.append("\n");
		    for (File file : folder.listFiles()) {
		        if (file.isDirectory()) {
		            printDirectoryTree(file, indent + 1, sb);
		        } else {
		            printFile(file, indent + 1, sb);
		        }
		    }
	}
	
	private static void printFile(File file, int indent, StringBuilder sb) {
		sb.append(getIndentString(indent));
		sb.append("+--");
		sb.append(file.getName());
		sb.append("\n");
	}

	private static String getIndentString(int indent) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < indent ; i++) {
			sb.append("|  ");
		}
		return sb.toString();
	}

}
