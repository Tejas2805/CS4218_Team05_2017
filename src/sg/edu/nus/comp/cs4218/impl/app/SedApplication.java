package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.app.Sed;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.PwdException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.file.FileHandler;

public class SedApplication implements Sed{

	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SedException {
		
		if(stdout == null){
			throw new SedException("OutputStream not provided");
		}
		else
		{
			switch(args.length)
			{
			case 0:
				throw new SedException("No arguments");
				
			case 1: //only replacement
				if(stdin == null)
				{
					throw new SedException("InputStream not provided");
				} else{
					int intCount;
					String input = "";
					char ch;
					String[] newArgs = {};
					try {
						while ((intCount = stdin.read()) != -1) {
							ch = (char)intCount;
							input += ch;
						}
						newArgs = new String[]{args[0], input};
					} catch (IOException e) {
						throw (SedException) new SedException(e.getMessage()).initCause(e);
					}
					String result = replaceInputStreamString(newArgs);
					try {
						stdout.write(result.getBytes());
					} catch (IOException e) {
						throw (SedException) new SedException(e.getMessage()).initCause(e);
					}
				}
				break;
			case 2:
				String result = replaceString(args);
				String[] results = splitStringToArray(result);
				
				for(String str : results){
					try {
						stdout.write(str.getBytes());
						stdout.write("\n".getBytes());
					} catch (IOException e) {
						throw (SedException) new SedException(e.getMessage()).initCause(e);
					}
				}
				break;
				
				default:
					throw new SedException("Too many args");
			}
		}
	}

	private String replaceInputStreamString(String[] args) throws SedException {

		if(isInValidReplacement(args[0])){
			return args[1];
		}	
		else if(isInvalidPattern(args[0])){
			return args[1];
		} else{
			if(isLocalSed(args[0])){
				return replaceFirstSubStringFromStdin(concatenateStringsToString(args));
			} else if(isGlobalSed(args[0])){
				return replaceAllSubstringsInStdin(concatenateStringsToString(args));
			}else {
				throw new SedException("WTF IS THIS");
			}		
	
		}
	}

	//args contain  /// file
	//returns null if fileNotReadable
	private String replaceString(String[] args) throws SedException{
		String filePath = args[1];
		FileHandler fh = new FileHandler();
		
		try {
			if(fh.checkIfFileIsReadable(Paths.get(filePath)))
			{
				if(isInValidReplacement(args[0])){
					return replaceSubstringWithInvalidReplacement(concatenateStringsToString(args));
				}	
				else if(isInvalidPattern(args[0])){
					return replaceSubstringWithInvalidRegex(concatenateStringsToString(args));
				} else{
					
					if(isLocalSed(args[0])){
						return replaceFirstSubStringInFile(concatenateStringsToString(args));
					} else if(isGlobalSed(args[0])){
						return replaceAllSubstringsInFile(concatenateStringsToString(args));
					}else {
						throw new SedException("WTF IS THIS");
					}
				}
					
			}
		} catch (Exception e) {
			throw new SedException("Could not read file");
		}
		
		return null;
	}
	private String replaceFirstWithRegex(String regex, String replacement, String line)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher.replaceFirst(replacement);
	}
	
	private String replaceAllWithRegex(String regex, String replacement, String line)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher.replaceAll(replacement);
	}
	
	@Override
	public String replaceFirstSubStringInFile(String args) {
		String[] argsArray = splitStringToArray(args);
		FileHandler fh = new FileHandler();
		List<String> lines = fh.readAllLines(argsArray[1]);
		
		for(int i = 0; i < lines.size(); i++)
		{
			String regex = getPatternLocal(argsArray[0]);
			String replacement = getReplacementLocal(argsArray[0]);
			lines.set(i, replaceFirstWithRegex(regex, replacement, lines.get(i)));
			
		}
		
		String[] output = new String[lines.size()];
		output = lines.toArray(output);
		return concatenateStringsToString(output);
	}

	@Override
	public String replaceAllSubstringsInFile(String args) {
		String[] argsArray = splitStringToArray(args);
		FileHandler fh = new FileHandler();
		List<String> lines = fh.readAllLines(argsArray[1]);
		
		for(int i = 0; i < lines.size(); i++)
		{
			String regex = getPatternGlobal(argsArray[0]);
			String replacement = getReplacementGlobal(argsArray[0]);
			lines.set(i, replaceAllWithRegex(regex, replacement, lines.get(i)));
			
		}
		
		String[] output = new String[lines.size()];
		output = lines.toArray(output);
		return concatenateStringsToString(output);
	}

	@Override
	public String replaceFirstSubStringFromStdin(String args) {
String[] argsArray = splitStringToArray(args);
		
		String[] lines = splitStringToArray(argsArray[1]);
		
		for(int i = 0; i < lines.length; i++)
		{
			String regex = getPatternGlobal(argsArray[0]);
			String replacement = getReplacementGlobal(argsArray[0]);
			lines[i] =  replaceFirstWithRegex(regex, replacement, lines[i]);
			
		}
		
		return concatenateStringsToString(lines);

	}

	@Override
	public String replaceAllSubstringsInStdin(String args) {
		String[] argsArray = splitStringToArray(args);
		
		String[] lines = splitStringToArray(argsArray[1]);
		
		for(int i = 0; i < lines.length; i++)
		{
			String regex = getPatternGlobal(argsArray[0]);
			String replacement = getReplacementGlobal(argsArray[0]);
			lines[i] = replaceAllWithRegex(regex, replacement, lines[i]);
			
		}
		
		return concatenateStringsToString(lines);

	}

	@Override
	public String replaceSubstringWithInvalidReplacement(String args) {
		String[] argsArray = splitStringToArray(args);
		
		FileHandler fh = new FileHandler();
		List<String> lines = fh.readAllLines(argsArray[1]);
		String output = "";
		
		for(String line : lines)
			output += line + System.getProperty("line.separator");
		return output;
	}

	@Override
	public String replaceSubstringWithInvalidRegex(String args) {
		String[] argsArray = splitStringToArray(args);
		
		FileHandler fh = new FileHandler();
		List<String> lines = fh.readAllLines(argsArray[1]);
		String output = "";
		
		for(String line : lines)
			output += line + System.getProperty("line.separator");
		return output;
	}

	// sed s/t/ijijj
	// sed s/t// 
	private boolean isInValidReplacement(String arg)
	{
		String strRegex = "^s\\/.+\\/\\/";
		String strRegex2 = "^s\\/.+\\/[^\\/]+";
		String strRegex3 = "^s\\/.+\\/\\/g";
		if(isLocalSed(arg)){
			if(Pattern.matches(strRegex, arg) || Pattern.matches(strRegex2, arg))
				return true;
		}
		else if (isGlobalSed(arg)){
			if(Pattern.matches(strRegex3, arg))
				return true;
		}
		
		return false;
	}
	
	private boolean isInvalidPattern(String strRegex)
	{
		String pattern = getPattern(strRegex);
		try
		{
			Pattern.compile(pattern);
		} catch(PatternSyntaxException p)
		{
			return true;
		}
		return false;
	}
	
	private boolean isGlobalSed(String arg)
	{
		String strRegex = "^s\\/.+\\/.+\\/g$";
		return Pattern.matches(strRegex, arg);
	}
	
	private boolean isLocalSed(String arg)
	{
		String strRegex = "^s\\/.+\\/.+\\/$";
		return Pattern.matches(strRegex, arg);
	}
	
	private String getPatternLocal(String arg)
	{
		String strRegex = "^s\\/(.+)\\/(.*)\\/$";
		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if(matcher.find())
			return matcher.group(1);
		else
			return "";
	}
	
	private String getPatternGlobal(String arg)
	{
		String strRegex = "^s\\/(.+)\\/(.*)\\/g$";
		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if(matcher.find())
			return matcher.group(1);
		else
			return "";
	}
	
	private String getReplacementLocal(String arg)
	{
		String strRegex = "^s\\/(.+)\\/(.*)\\/$";
		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if(matcher.find())
			return matcher.group(2);
		else
			return "";
	}
	
	private String getReplacementGlobal(String arg)
	{
		String strRegex = "^s\\/(.+)\\/(.*)\\/g$";
		Pattern pattern = Pattern.compile(strRegex);
		Matcher matcher = pattern.matcher(arg);
		if(matcher.find())
			return matcher.group(2);
		else
			return "";
	}
	
	private String getPattern(String arg)
	{
		if(isLocalSed(arg)){
			return getPatternLocal(arg);
		} else if(isGlobalSed(arg)){
			return getPatternGlobal(arg);
		} else
			return null;
	}
	
	private String getReplacement(String arg)
	{
		if(isLocalSed(arg)){
			return getReplacementLocal(arg);
		} else if(isGlobalSed(arg)){
			return getReplacementGlobal(arg);
		} else
			return null;
	}
	
	private String concatenateStringsToString(String[] inputStrings)
	{
		String strOutput = "";
		
		for(String string : inputStrings)
			strOutput += string + System.getProperty("line.separator");
		
		return strOutput;
		
	}
	
	private String[] splitStringToArray(String inputString)
	{
		return  inputString.split(System.getProperty("line.separator"));
	}
}
