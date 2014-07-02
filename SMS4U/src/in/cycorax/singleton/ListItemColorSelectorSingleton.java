package in.cycorax.singleton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * This is single ton class which returns color code from list of color codes
 * available.
 * 
 * @author Virendra
 * 
 */
public class ListItemColorSelectorSingleton {
	private static int lastCodeIndex = -1;
	private static ListItemColorSelectorSingleton instance;
	private static ArrayList<String> colorCodes = new ArrayList<String>(
			Arrays.asList("#7ec0ee", "#ee7ec0", "#c0ee7e", "#eeab7e", "#7eeee3"));

	private ListItemColorSelectorSingleton() {

	}

	public static ListItemColorSelectorSingleton getInstance() {

		if (instance == null) {
			instance = new ListItemColorSelectorSingleton();
		}
		return instance;
	}

	public String getColorCode() {

		if (lastCodeIndex == 4) {
			lastCodeIndex = -1;
		}
		lastCodeIndex++;
		return colorCodes.get(lastCodeIndex);

	}

}
