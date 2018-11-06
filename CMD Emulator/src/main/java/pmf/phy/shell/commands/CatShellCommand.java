package pmf.phy.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ArgumentExtractor;
import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;

public class CatShellCommand implements ShellCommand {

	
	private static final String commandName = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {

			if (args.size() > 0 && args.size() <= 2) {

				Charset charset = Charset.defaultCharset();
				if (args.size() == 2) {
					try {
						charset = Charset.forName(args.get(1));
					} catch (IllegalCharsetNameException e) {
						env.writeln("Invalid charset name " + args.get(1));
						return ShellStatus.CONTINUE;
					} catch (UnsupportedCharsetException e) {
						env.writeln("No support for the named charset is available in this instance of the Java virtual machine: "
								+ args.get(1));
						return ShellStatus.CONTINUE;

					}
				}

				Path file = Paths.get(args.get(0));

				if (!Files.isReadable(file)) {
					env.writeln("File is not readable.");
					return ShellStatus.CONTINUE;
				}

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(
								new FileInputStream(file.toFile())), charset))) {

					String line = null;
					while ((line = br.readLine()) != null) {
						env.writeln(line);
					}

				}

			} else {
				env.writeln("Cat command expects one argument.");
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
		description.add("Opens given file and writes its"
				+ " content to console using charset"
				+ " from an argument if provided, default"
				+ " platform charset otherwsise.");
		return Collections.unmodifiableList(description);
	}
}
