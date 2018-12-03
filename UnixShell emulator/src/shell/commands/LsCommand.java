package shell.commands;

import shell.ShellIOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

// Path path = Paths.get("C:\Users\barto\Desktop\Java-course-2017.-master\Java-course-2017.-master\Java-course-2017.-master\hw06-0036491099");

public class LsCommand implements ShellCommand {

	
	
	List<String> list_arguments = new ArrayList<String>();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String commandName = "ls";
	
	
	

	@SuppressWarnings("resource")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		list_arguments = env.parameterisedInput(arguments);

		if (list_arguments.size() == 2
				&& list_arguments.get(0).equals(getCommandName())) {
			Path path = Paths.get(list_arguments.get(1));
			
			if(!(path.toFile().exists())) {
				env.writeln("Wrong path!");
				return ShellStatus.CONTINUE;
			}

			try { // sve pokazane foldere, neide u podfoldere
				Stream<Path> s = Files.walk(path, 1);
				s.forEach(filePath -> {

					String formattedDateTime = null;

					BasicFileAttributeView faView = Files.getFileAttributeView(
							filePath, BasicFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes;
					try {
						attributes = faView.readAttributes();
						FileTime fileTime = attributes.creationTime();
						formattedDateTime = sdf.format(new Date(fileTime
								.toMillis()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					StringBuilder sb = new StringBuilder();
					StringBuilder sb2 = new StringBuilder();
					if (Files.isDirectory(filePath)) {
						sb.append("d");
					} else {
						sb.append("-");
					}
					if (Files.isReadable(filePath)) {
						sb.append("r");
					} else {
						sb.append("-");
					}
					if (Files.isWritable(filePath)) {
						sb.append("w");
					} else {
						sb.append("-");
					}
					if (Files.isExecutable(filePath)) {
						sb.append("x");
					} else {
						sb.append("-");
					}

					int prazninaSIZE = 10 - Long
							.valueOf(filePath.toFile().length()).toString()
							.length();

					if (prazninaSIZE >= 0) {
						int i = 0;
						while (i < prazninaSIZE) {
							sb2.append(" ");
							i++;
						}

					} else {
						throw new ShellIOException(
								"Input too large or something went wrond in LS function"); // ovo
																							// sredi
					}
					sb2.append(filePath.toFile().length());
					System.out.println(sb.toString() + " " + sb2.toString()
							+ " " + formattedDateTime + " "
							+ filePath.getFileName());
				});
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// path prima kao direktorij
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
				.add("Command ls takes a single argument – directory – and writes a directory listing (not recursive)");
		descriptionOfCommands = Collections
				.unmodifiableList(descriptionOfCommands);
		return null;
	}

}
