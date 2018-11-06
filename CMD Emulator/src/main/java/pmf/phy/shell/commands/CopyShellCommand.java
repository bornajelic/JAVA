package pmf.phy.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ArgumentExtractor;
import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;

public class CopyShellCommand implements ShellCommand {

	
	private static final String commandName = "copy";

	
	private static final int BUFFER_SIZE = 4096;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);
		try {

			if (args.size() == 2) {
				Path source = Paths.get(args.get(0));
				Path destination = Paths.get(args.get(1));

				if (!Files.exists(source)) {
					env.writeln("File does not exists :" + source.getFileName());
					return ShellStatus.CONTINUE;
				}

				if (Files.isDirectory(source)) {
					env.writeln("Given source is a directory. Copy command works with files.");
					return ShellStatus.CONTINUE;
				}

				if (Files.isDirectory(destination)) {
					destination = destination.resolve(source.getFileName());
				}

				if (Files.exists(destination)
						&& Files.isRegularFile(destination)) {
					env.write("File "
							+ destination.getFileName()
							+ " already exists. Would you like to overwrite it? [y/n]: ");
					String answer = env.readLine();
					if (answer.equalsIgnoreCase("n")) {
						env.writeln("File not copied.");
						return ShellStatus.CONTINUE;
					} else if (!answer.equalsIgnoreCase("y")) {
						env.writeln("Invalid input. File not copied.");
						return ShellStatus.CONTINUE;
					}

				}

				if (Files.isSameFile(source, destination)) {
					env.writeln("Source and destination are same files! File not copied.");
					return ShellStatus.CONTINUE;
				}

				copy(source, destination);
				env.writeln("File \"" + source.toString() + "\" copied to \""
						+ destination.toString() + "\".");

			} else {
				env.writeln("Copy command expects two arguments.");
			}

		} catch (IOException e) {
			throw new RuntimeException("Can not write to the environment.");
		}

		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Copys source file to destination file.");
		return Collections.unmodifiableList(description);
	}

	
	private void copy(Path source, Path destination) {
		try (InputStream input = new BufferedInputStream(new FileInputStream(
				source.toFile()));
				OutputStream output = new BufferedOutputStream(
						new FileOutputStream(destination.toFile()))) {

			byte[] data = new byte[BUFFER_SIZE];
			while (input.read(data) != -1) {
				output.write(data);
			}

		} catch (IOException e) {
			throw new RuntimeException("IOException occured.");
		}

	}

}
