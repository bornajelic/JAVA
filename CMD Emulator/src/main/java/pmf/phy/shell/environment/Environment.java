package pmf.phy.shell.environment;

import java.io.IOException;

import pmf.phy.shell.commands.ShellCommand;

public interface Environment {

	String readLine() throws IOException;

	void write(String text) throws IOException;

	void writeln(String text) throws IOException;

	Iterable<ShellCommand> commands();

	Character getMultilineSymbol();

	void setMultilineSymbol(Character symbol);

	Character getPromptSymbol();

	void setPromptSymbol(Character symbol);

	Character getMorelinesSymbol();

	void setMorelinesSymbol(Character symbol);

}
