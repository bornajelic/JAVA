package pmf.phy.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author	Borna Jelic
 *
 */
public class ArgumentExtractor {

	
	public static List<String> extract(String arguments) {
		List<String> extractedArgs = new ArrayList<>();
		if (arguments != null) {
			String regex = "\"?( )(?=(([^\"]*\"){2})*[^\"]*$)\"?";
			Collections.addAll(extractedArgs, arguments.split(regex));
			for (int i = 0, lenght = extractedArgs.size(); i < lenght; i++) {
				extractedArgs.set(i, extractedArgs.get(i).replaceAll("\"", ""));
			}
		}
		return extractedArgs;
	}
}
