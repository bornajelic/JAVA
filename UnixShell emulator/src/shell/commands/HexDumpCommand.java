package shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shell.Environment;
import shell.ShellCommand;
import shell.ShellStatus;

public class HexDumpCommand implements ShellCommand{
	
	List<String> list_arguments = new ArrayList<String>();
	
	private String commandName = "hexdump";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		list_arguments = env.parameterisedInput(arguments);
		
		if(list_arguments.size() == 2 && list_arguments.get(0).equals(getCommandName())) {
			
			File file = new File(list_arguments.get(1));
			
			if(file.isFile()) {
				byte[] bArray = null;
				try {
					bArray = Files.readAllBytes(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				env.writeln(hexDump(bytes2Hex(bArray)));
			}
			else {
				env.writeln("Invalid file path");
			}
			
		}else {
			env.writeln("Invalid input, expeced hexdump filepath");
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
		descriptionOfCommands.add("Finally, the hexdump command expects a single argument: file name, and produces hex-output as illustrated below. "
				+ "On the right side of the image only a standard subset of characters is shown");
		descriptionOfCommands = Collections.unmodifiableList(descriptionOfCommands);
		return null;
	}
	
	public static char[] bytes2Hex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];

		for (int i = 0; i < bytes.length; i++) {
			int value = bytes[i] & 0xFF;

			if (value >>> 4 == 0000) {
				hexChars[i * 2] = '0';

			} else {
				hexChars[i * 2] = hexArray[value >>> 4];
			}

			if ((value & 0x0F) == 0000) {
				hexChars[i * 2 + 1] = '0';
			} else {
				hexChars[i * 2 + 1] = hexArray[value & 0x0F];
			}

		}
		return hexChars;
	}

	private static String hexDump(char[] hexChars) {

		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		int hexCounter = 16;
		int counter = 0;
		String s = null;

		for (int i = 0; i < hexChars.length; i++) {

			if (i == 0) {
				sb.append(String.format("%08X", hexCounter) + ": ");
				hexCounter += 16;
			}

			if (counter < 32) {
				sb2.append(hexChars[i]);
				counter++;

			}
			if (counter == 32) {
				s = funkcija(sb2.toString());
				counter = 0;
				sb2 = new StringBuilder();

			}

			if (i % 2 == 0 && i > 0) {
				sb.append(" ");
				if (i % 16 == 0) {
					sb.append("| ");
					if (i % 32 == 0) {
						sb.append(" " + s + "\n");
						sb.append(String.format("%08X", hexCounter) + ": ");
						hexCounter += 16;
					}

				}

			}
			//processing last line with empty spaces
			if (i == hexChars.length - 1) {
				sb.append(hexChars[i]);
				
				String last = sb2.toString();
				sb.append(spaceHexPadding(sb2.toString()));
				sb.append("  " + funkcija(last));
				
				break;

			}
			sb.append(hexChars[i]);

		}
		return sb.toString();
	}

	private static String funkcija(String hexString) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < hexString.length(); i += 2) {
			String str = hexString.substring(i, i + 2);
			sb.append((char) Integer.parseInt(str, 16));
		}
		String rez = sb.toString().replaceAll("\\r|\\n", ".");

		return rez;
	}

	private static String spaceHexPadding(String str) {
		
		StringBuilder sb = new StringBuilder();
		int padding = 32 - str.length();

		while (padding > 0) {
			if (padding % 2 == 0) {
				sb.append(" ");
				if (padding % 16 == 0 && padding > 0) {
					sb.append("| ");
				}
			}
			sb.append(" ");
			padding--;
		}
		sb.append(" |");
		return sb.toString();
	}

}
