package pmf.phy.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pmf.phy.shell.ArgumentExtractor;
import pmf.phy.shell.ShellStatus;
import pmf.phy.shell.environment.Environment;


public class TreeShellCommand implements ShellCommand {

	
	private static final String commandName = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ArgumentExtractor.extract(arguments);

		try {

			if (args.size() == 1) {

				Path root = Paths.get(args.get(0));
				if (!Files.exists(root)) {
					env.writeln("File does not exists.");
					return ShellStatus.CONTINUE;
				}

				if (!Files.isDirectory(root)) {
					env.writeln("File is not directory.");
					return ShellStatus.CONTINUE;
				}

				Files.walkFileTree(root, new IndentPrint(env));

			} else {
				env.writeln("Tree command expects one argument.");
				return ShellStatus.CONTINUE;
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
		description.add("Prints a tree of given directory recursively.");
		return Collections.unmodifiableList(description);
	}

	
	private static class IndentPrint implements FileVisitor<Path> {

		
		private int indentLevel;

		
		private Environment env;

		
		public IndentPrint(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (indentLevel == 0) {
				env.writeln(dir.toString());
			} else {
				env.write(String.format("%" + indentLevel + "s%s%n", "",
						dir.getFileName()));
			}
			indentLevel += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			env.write(String.format("%" + indentLevel + "s%s%n", "",
					file.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indentLevel -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

}
