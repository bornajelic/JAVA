package pmf.phy.shell;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pmf.phy.shell.commands.CatShellCommand;
import pmf.phy.shell.commands.CharsetShellCommand;
import pmf.phy.shell.commands.CopyShellCommand;
import pmf.phy.shell.commands.ExitShellCommand;
import pmf.phy.shell.commands.HelpShellCommand;
import pmf.phy.shell.commands.HexdumpShellCommand;
import pmf.phy.shell.commands.LsShellCommand;
import pmf.phy.shell.commands.MkdirShellCommand;
import pmf.phy.shell.commands.ShellCommand;
import pmf.phy.shell.commands.SymbolShellClass;
import pmf.phy.shell.commands.TreeShellCommand;
import pmf.phy.shell.environment.Environment;
import pmf.phy.shell.environment.ShellEnvironment;


public class MyShell {

	/**
	 * Map of supported commands.
	 */
	private static Map<String, ShellCommand> commands;

	/**
	 * Builds map of supported commands during shell startup.
	 */
	static {
		commands = new HashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("symbol", new SymbolShellClass());
		commands.put("charset", new CharsetShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
	}

	
	public static Collection<ShellCommand> getCommands() {
		return commands.values();
	}

	
	private static Environment environment = new ShellEnvironment();

	
	public static void main(String[] args) {

		try {

			environment.writeln("Welcome to MyShell v 1.0");

			while (true) {

				environment.write(environment.getPromptSymbol().toString()
						+ " ");

				String line = environment.readLine();
				if (hasMoreLines(line)) {
					line = removeMorelineSymbol(line);
					while (true) {
						environment.write(environment.getMultilineSymbol()
								.toString() + " ");
						String restOfTheLine = environment.readLine();
						if (hasMoreLines(restOfTheLine)) {
							restOfTheLine = removeMorelineSymbol(restOfTheLine);
							line += restOfTheLine;
						} else {
							line += restOfTheLine;
							break;
						}
					}
				}

				String[] lineParts = line.trim().split(" ", 2);
				String commandName = lineParts[0];
				String arguments = null;
				if (lineParts.length > 1) {
					arguments = lineParts[1].trim();
				}

				ShellCommand command = commands.get(commandName);
				if (command == null) {
					environment.writeln("Command " + commandName
							+ " is not supported in this shell.");
					continue;
				}
				ShellStatus status = command.executeCommand(environment,
						arguments);
				if (status.equals(ShellStatus.TERMINATE)) {
					environment
							.writeln("Thank you for using MyShell! Goodbye!");
					break;
				}
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	
	private static boolean hasMoreLines(String line) {
		String morelinesSymbol = environment.getMorelinesSymbol().toString();
		return line.endsWith(" " + morelinesSymbol);
	}

	private static String removeMorelineSymbol(String line) {
		return line.substring(0, line.length() - 1);
	}

}
