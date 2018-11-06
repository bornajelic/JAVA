package pmf.phy.shell.environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import pmf.phy.shell.MyShell;
import pmf.phy.shell.commands.ShellCommand;


public class ShellEnvironment implements Environment {

	
	private BufferedReader reader;

	
	private BufferedWriter writer;

	private Character multilineSymbol;

	
	private Character promptSymbol;

	
	private Character morelinesSymbol;

	
	public ShellEnvironment() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
		this.multilineSymbol = '|';
		this.promptSymbol = '>';
		this.morelinesSymbol = '\\';
	}

	@Override
	public String readLine() throws IOException {
		return reader.readLine().trim();
	}

	@Override
	public void write(String text) throws IOException {
		writer.write(text);
		writer.flush();
	}

	@Override
	public void writeln(String text) throws IOException {
		writer.write(text);
		writer.newLine();
		writer.flush();
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return (() -> MyShell.getCommands().iterator());
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
