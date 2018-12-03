package shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class CatCommand implements ShellCommand{
	
	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		list_arguments = env.parameterisedInput(arguments);
		
		
		if(list_arguments.size() == 3 && list_arguments.get(0).equals(this.commandName)) {
			String charset = list_arguments.get(2);
			Path filePath = Paths.get(list_arguments.get(1));
			Charset c;
			switch(charset.toUpperCase()) {
				case "ISO_8859_1": c = StandardCharsets.ISO_8859_1;break;
				case "US_ASCII"  : c = StandardCharsets.US_ASCII; break;
				case "UTF_16" 	 : c = StandardCharsets.UTF_16; break;
				case "UTF_16BE"  : c = StandardCharsets.UTF_16BE; break;
				case "UTF_16LE"  : c = StandardCharsets.UTF_16LE; break;
				default :
					c = StandardCharsets.UTF_8;
					break;
			}
			
			if(filePath.toFile().isFile()) {
				try {
					Stream<String> stream = Files.lines(filePath, c); 
					stream.forEach(System.out::println);
					stream.close();
					System.out.println();
				}catch(IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("ERROR, send path to file, not directory");
			}
			
		} else if(list_arguments.size() == 2 && list_arguments.get(0).equals(this.commandName)) {
			
			Path filePath = Paths.get(list_arguments.get(1));
			if(filePath.toFile().isFile()) {
				try {
					Stream<String> stream = Files.lines(filePath, Charset.defaultCharset()); 
					stream.forEach(System.out::println);
					stream.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			} else {
				env.writeln("ERROR, send path to file, not directory");
			}
			
		} else {
			env.writeln("Wrong input!");
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
		descriptionOfCommands.add("Command cat takes one or two arguments. The first argument is path to some file and is mandatory."
				+ " The second argument is charset name that should be used to interpret chars from bytes. "
				+ "If not provided, a defaultplatform charset should be used (see java.nio.charset.Charset class for details). "
				+ "This command opensgiven file and writes its content to console." + 
				"Charset inputs : ISO_8859_1, US_ASCII , UTF_16 , UTF_16BE , UTF_16LE ,UTF_8");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
