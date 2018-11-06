package pmf.phy.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ArgumentExtractor;
import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;


public class SymbolShellClass implements ShellCommand {

	private static final String commandName = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);
		try {

			if (args.size() > 0 && args.size() <= 2) {

				if (args.size() == 1) {
					printSymbol(env, args.get(0));
				} else {
					if (args.get(1).length() != 1) {
						env.writeln("New symbol should be one character: "
								+ args.get(1));
						return ShellStatus.CONTINUE;
					}
					changeSymbol(env, args.get(0), args.get(1).charAt(0));
				}

			} else {
				env.writeln("Symbol command expects one or two arguments.");
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
		description.add("Used to change symbols. If"
				+ " started with one argument"
				+ " it prints the currently used"
				+ " symbol. If started with two arguments,"
				+ " it changes the symbol to second argument.");
		return Collections.unmodifiableList(description);
	}

	
	private void printSymbol(Environment env, String envSymbol)
			throws IOException {
		switch (envSymbol) {
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol()
					+ "'.");
			break;
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'.");
			break;
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol()
					+ "'.");

			break;
		default:
			env.writeln("Symbol of this type not supported: " + envSymbol);
			break;
		}
	}

	
	private void changeSymbol(Environment env, String envSymbol,
			Character symbol) throws IOException {
		switch (envSymbol) {
		case "MULTILINE":
			env.setMultilineSymbol(symbol);
			break;
		case "PROMPT":
			env.setPromptSymbol(symbol);
			break;
		case "MORELINES":
			env.setMorelinesSymbol(symbol);
			break;
		default:
			env.writeln("Symbol of this type not supported: " + envSymbol);
			break;
		}

	}

}
