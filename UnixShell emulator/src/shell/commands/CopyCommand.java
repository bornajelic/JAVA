package shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class CopyCommand implements ShellCommand {
	
	

	List<String> list_arguments = new ArrayList<String>();

	private String commandName = "copy";
	
	
	

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		list_arguments = env.parameterisedInput(arguments);

		if (list_arguments.size() == 3
				&& list_arguments.get(0).equals(getCommandName())) {

			File source = new File(list_arguments.get(1));

			File destination = new File(list_arguments.get(2));

			if (!(source.canRead()) && !(source.canWrite())
					&& !(source.isFile())) {
				env.writeln("Path name for file to be copied is not valid!");
				return ShellStatus.CONTINUE;
			}

			if (destination.exists() && destination.isFile()
					&& destination.canRead() && destination.canWrite()) { // if
																			// destination
																			// file
																			// is
																			// FILE

				System.out.println("OVERWRITE? Y\\N");

				char c = env.readLine().toUpperCase().charAt(0);

				if (c == 'Y') {
					try {
						copyFiles(source, destination);
					} catch (IOException e) {

						e.printStackTrace();
					}

				} else {
					env.writeln("Path name is not valid!");
				}

			} else if (destination.exists() && destination.isDirectory()) { // if
																			// destination
																			// is
																			// DIRECTORY

				destination = new File(destination, source.getName());
				try {
					copyFiles(source, destination);
				} catch (IOException e) {

					e.printStackTrace();
				}

			} else {
				System.out.println("Destination not valid");
			}

		} else {
			env.writeln("Passed arguments are not valit, expecting command name and two paths");
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
		descriptionOfCommands
				.add("The copy command expects two arguments: source file name and destination file name (i.e. paths andnames). "
						+ "Is destination file exists, you should ask user is it allowed to overwrite it. ");
		descriptionOfCommands = Collections
				.unmodifiableList(descriptionOfCommands);
		return null;
	}

	private void copyFiles(File source, File destination) throws IOException {

		InputStream is = null;
		OutputStream os = null;

		is = new FileInputStream(source);
		os = new FileOutputStream(destination);
		byte[] buffer = new byte[1024];
		int lenght;

		while ((lenght = is.read(buffer)) > 0) {
			os.write(buffer, 0, lenght);
		}

		is.close();
		os.close();
	}

}
