package sg.edu.nus.comp.cs4218.impl.app.sed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SedHelper {
	public boolean isInValidReplacement(String arg) {
		String strRegex = "^s\\/.+\\/\\/";
		String strRegex2 = "^s\\/.+\\/[^\\/]+";
		String strRegex3 = "^s\\/.+\\/\\/g";

		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|.+\\|\\|";
			strRegex2 = "^s\\|.+\\|[^\\|]+";
			strRegex3 = "^s\\|.+\\|\\|g";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\.+\\\\\\\\";
			strRegex2 = "^s\\\\.+\\\\[^\\\\]+";
			strRegex3 = "^s\\\\.+\\\\\\\\g";
		}

		if (isLocalSed(arg)) {
			if (Pattern.matches(strRegex, arg) || Pattern.matches(strRegex2, arg)) {
				return true;
			}

		} else if (isGlobalSed(arg) && Pattern.matches(strRegex3, arg)) {
			return true;
		}

		return false;
	}

	public boolean isInvalidPattern(String strRegex) {
		String pattern = getPattern(strRegex);
		try {
			Pattern.compile(pattern);
		} catch (PatternSyntaxException p) {
			return true;
		}
		return false;
	}

	public boolean isGlobalSed(String arg) {
		String strRegex = "^s\\/.+\\/.+\\/g$";
		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|.+\\|.+\\|g$";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\.+\\/.+\\\\g$";
		}
		return Pattern.matches(strRegex, arg);
	}

	public boolean isLocalSed(String arg) {
		String strRegex = "^s\\/.+\\/.+\\/$";

		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|.+\\|.+\\|$";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\.+\\/.+\\\\$";
		}

		return Pattern.matches(strRegex, arg);
	}

	public String getPatternLocal(String arg) {
		String strRegex = "^s\\/(.+)\\/(.*)\\/$";

		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|(.+)\\|(.*)\\|$";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\(.+)\\\\(.*)\\\\$";
		}

		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if (matcher.find()) {
			return matcher.group(1);
		}

		else {
			return "";
		}

	}

	public String getPatternGlobal(String arg) {
		String strRegex = "^s\\/(.+)\\/(.*)\\/g$";
		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|(.+)\\|(.*)\\|g$";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\(.+)\\\\(.*)\\\\g$";
		}

		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if (matcher.find()) {
			return matcher.group(1);
		}

		else {
			return "";
		}

	}

	public String getReplacementLocal(String arg) {
		String strRegex = "^s\\/(.+)\\/(.*)\\/$";
		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|(.+)\\|(.*)\\|$";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\(.+)\\\\(.*)\\\\$";
		}
		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if (matcher.find()) {
			return matcher.group(2);
		} else {
			return "";
		}

	}

	public String getReplacementGlobal(String arg) {
		String strRegex = "^s\\/(.+)\\/(.*)\\/g$";
		if (arg.charAt(1) == '|') {
			strRegex = "^s\\|(.+)\\|(.*)\\|g$";
		} else if (arg.charAt(1) == '\\') {
			strRegex = "^s\\\\(.+)\\\\(.*)\\\\g$";
		}
		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if (matcher.find()) {
			return matcher.group(2);
		}

		else {
			return "";
		}

	}

	public String getPattern(String arg) {
		if (isLocalSed(arg)) {
			return getPatternLocal(arg);
		} else if (isGlobalSed(arg)) {
			return getPatternGlobal(arg);
		} else {
			return null;
		}

	}

	public String concatenateStringsToString(String... inputStrings) {
		String strOutput = "";

		for (String string : inputStrings) {
			strOutput += string + "\n";
		}

		return strOutput;

	}

	public String[] splitStringToArray(String inputString) {
		return inputString.split("\n");
	}
}
