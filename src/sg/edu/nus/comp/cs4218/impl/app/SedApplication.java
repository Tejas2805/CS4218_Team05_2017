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
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.app.file.FileHandler;
import sg.edu.nus.comp.cs4218.impl.app.sed.SedHelper;

public class SedApplication implements Sed{

	SedHelper sedHelper = new SedHelper();
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
					String[] newArgs = createNewArgsByAppeningInputStreamData(args, stdin);
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
				String[] results = sedHelper.splitStringToArray(result);
				
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

	private String[] createNewArgsByAppeningInputStreamData(String[] args, InputStream stdin) throws SedException {
		int intCount;
		String input = "";
		char character;
		String[] newArgs = {};
		try {
			while ((intCount = stdin.read()) != -1) {
				character = (char)intCount;
				input += character;
			}
			newArgs = new String[]{args[0], input};
		} catch (IOException e) {
			throw (SedException) new SedException(e.getMessage()).initCause(e);
		}
		return newArgs;
	}

	private String replaceInputStreamString(String... args) throws SedException {

		if(sedHelper.isInValidReplacement(args[0])){
			return args[1];
		}	
		else if(sedHelper.isInvalidPattern(args[0])){
			return args[1];
		} else{
			if(sedHelper.isLocalSed(args[0])){
				return replaceFirstSubStringFromStdin(sedHelper.concatenateStringsToString(args));
			} else if(sedHelper.isGlobalSed(args[0])){
				return replaceAllSubstringsInStdin(sedHelper.concatenateStringsToString(args));
			}else {
				throw new SedException("WTF IS THIS");
			}		
	
		}
	}

	//args contain  /// file
	//returns null if fileNotReadable
	private String replaceString(String... args) throws SedException{
		String filePath = args[1];
		FileHandler fileHandler = new FileHandler();
		
		try {
			if(fileHandler.checkIfFileIsReadable(Paths.get(filePath)))
			{
				if(sedHelper.isInValidReplacement(args[0])){
					return replaceSubstringWithInvalidReplacement(sedHelper.concatenateStringsToString(args));
				}	
				else if(sedHelper.isInvalidPattern(args[0])){
					return replaceSubstringWithInvalidRegex(sedHelper.concatenateStringsToString(args));
				} else{
					
					if(sedHelper.isLocalSed(args[0])){
						return replaceFirstSubStringInFile(sedHelper.concatenateStringsToString(args));
					} else if(sedHelper.isGlobalSed(args[0])){
						return replaceAllSubstringsInFile(sedHelper.concatenateStringsToString(args));
					}else {
						throw new SedException("WTF IS THIS");
					}
				}
					
			}
		} catch (Exception e) {
			throw (SedException) new SedException(e.getMessage()).initCause(e);
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
		String[] argsArray = sedHelper.splitStringToArray(args);
		FileHandler fileHandler = new FileHandler();
		List<String> lines = fileHandler.readAllLines(argsArray[1]);
		
		for(int i = 0; i < lines.size(); i++)
		{
			String regex = sedHelper.getPatternLocal(argsArray[0]);
			String replacement = sedHelper.getReplacementLocal(argsArray[0]);
			lines.set(i, replaceFirstWithRegex(regex, replacement, lines.get(i)));
			
		}
		
		String[] output = new String[lines.size()];
		output = lines.toArray(output);
		return sedHelper.concatenateStringsToString(output);
	}

	@Override
	public String replaceAllSubstringsInFile(String args) {
		String[] argsArray = sedHelper.splitStringToArray(args);
		FileHandler fileHandler = new FileHandler();
		List<String> lines = fileHandler.readAllLines(argsArray[1]);
		
		for(int i = 0; i < lines.size(); i++)
		{
			String regex = sedHelper.getPatternGlobal(argsArray[0]);
			String replacement = sedHelper.getReplacementGlobal(argsArray[0]);
			lines.set(i, replaceAllWithRegex(regex, replacement, lines.get(i)));
			
		}
		
		String[] output = new String[lines.size()];
		output = lines.toArray(output);
		return sedHelper.concatenateStringsToString(output);
	}

	@Override
	public String replaceFirstSubStringFromStdin(String args) {
		String[] argsArray = sedHelper.splitStringToArray(args);
		
		String lineString = "";
		for(int i = 1; i < argsArray.length; i++){
			lineString += argsArray[i]+"\n";
		}
			
		String[] lines = sedHelper.splitStringToArray(lineString);
		
		for(int i = 0; i < lines.length; i++)
		{
			String regex = sedHelper.getPatternLocal(argsArray[0]);
			String replacement = sedHelper.getReplacementLocal(argsArray[0]);
			lines[i] =  replaceFirstWithRegex(regex, replacement, lines[i]);
			
		}
		
		return sedHelper.concatenateStringsToString(lines);

	}

	@Override
	public String replaceAllSubstringsInStdin(String args) {
		String[] argsArray = sedHelper.splitStringToArray(args);
		
		String lineString = "";
		for(int i = 1; i < argsArray.length; i++){
			lineString += argsArray[i]+"\n";
		}
			
		
		String[] lines = sedHelper.splitStringToArray(lineString);
		
		for(int i = 0; i < lines.length; i++)
		{
			String regex = sedHelper.getPatternGlobal(argsArray[0]);
			String replacement = sedHelper.getReplacementGlobal(argsArray[0]);
			lines[i] = replaceAllWithRegex(regex, replacement, lines[i]);
			
		}
		
		return sedHelper.concatenateStringsToString(lines);

	}

	@Override
	public String replaceSubstringWithInvalidReplacement(String args) {
		String[] argsArray = sedHelper.splitStringToArray(args);
		
		FileHandler fileHandler = new FileHandler();
		List<String> lines = fileHandler.readAllLines(argsArray[1]);
		String output = "";
		
		for(String line : lines){
			output += line + "\n";
		}
			
		return output;
	}

	@Override
	public String replaceSubstringWithInvalidRegex(String args) {
		String[] argsArray = sedHelper.splitStringToArray(args);
		
		FileHandler fileHandler = new FileHandler();
		List<String> lines = fileHandler.readAllLines(argsArray[1]);
		String output = "";
		
		for(String line : lines){
			output += line + "\n";
		}
			
		return output;
	}

	// sed s/t/ijijj
	// sed s/t// 

}
