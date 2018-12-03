package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class Shell implements Environment{

	private char promptSymbol = '>';
	
	private char multiLineSymbol = '|';
	
	private char moreLinesSymbol = '\\';
	
	List<String> list = new ArrayList<String>();
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private SortedMap<String, ShellCommand> commands;
			
	
	public Shell() {
		
		this.commands = new TreeMap<String, ShellCommand>();
		commands.put("cat", new CatCommand());
		commands.put("charset", new CharsetCommand());
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexDumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("symbol", new SymbolsCommand());
		commands.put("tree", new TreeCommand());
		
		commands = Collections.unmodifiableSortedMap(commands());
		
	}
	
	@Override
	public String readLine() throws ShellIOException {
		String str = null;
		try {
			str = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public void write(String text) throws ShellIOException {
		// TODO Auto-generated method stub
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		// TODO Auto-generated method stub
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		// TODO Auto-generated method stub
		return this.multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		// TODO Auto-generated method stub
		this.multiLineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		// TODO Auto-generated method stub
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		// TODO Auto-generated method stub
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		// TODO Auto-generated method stub
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		// TODO Auto-generated method stub
		this.moreLinesSymbol = symbol;
		
	}
	
	//utility method, returns a list of input parts
	public List<String> parameterisedInput(String str) {
		
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(str);

		while (m.find()) {
			list.add(m.group(1));
		}
		
		return list;
	}

}
