package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.GrepException;

public class GrepApplication implements Grep {
	
	private Pattern pattern;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		
		
		if(stdout == null)
			throw new GrepException("OutputStream not provided");
		

		String newArg = "";
		String output;
		
		for(String arg : args){ newArg += arg + "\n";}
		
		if (args == null || args.length == 0) {
			if (stdin == null) {
				throw new GrepException("InputStream not provided");
			} else {
				//TODO
				output = grepFromStdin(newArg); 
			}
		}else {
			int numOfFiles = args.length - 1;

			switch(numOfFiles){
			case 0:
				output = "\n";
				break;
			case 1:
				output = grepFromOneFile(newArg);
				break;
			default:
				output = grepFromMultipleFiles(newArg);
				break;
			}
		}
		
		try {
			stdout.write(output.getBytes());
		} catch (IOException e) {
			throw new CatException("Exception Caught");
		}
			
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws CatException
	 *             If the file is not readable
	 * @throws GrepException 
	 */
	
	boolean checkIfFileIsReadable(Path filePath) throws GrepException {
		
		if (Files.isDirectory(filePath)) {
			throw new GrepException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new GrepException("Could not read file");
		}
	}
	
	List<Path> getValidFilePathsFromString(String[] strFilePaths, int start, int end){
		List<Path> validFilePaths = new ArrayList<>();
		Path filePath;
		
		for(int i = start; i <=end; i++){
			
			try{
				filePath = Paths.get(strFilePaths[i]);
			}catch(InvalidPathException e){
				continue;
			}
			
			try {
				if(checkIfFileIsReadable(filePath)){
					validFilePaths.add(filePath);
				}
			} catch (GrepException e) {
				continue;
			}
		}
		
		return validFilePaths;
	}
	
	@Override
	public String grepFromStdin(String args) {
		if(grepInvalidPatternInFile(args) != null){
			return "\n";
		}
		
		String[] eachArg = args.split("\n");
		String strPattern = eachArg[0];
		String output = "";
		
		for(int i = 1; i < eachArg.length; i++){
			output += performGrep(strPattern, eachArg[i]);
		}
		
		return output;
	}

	/*
	 *	Args is of format
	 * [pattern\nfilePath] 
	 */
	@Override
	public String grepFromOneFile(String args) {
		if(grepInvalidPatternInFile(args) != null){
			return "\n";
		}
		String strPattern = args.split("\n")[0];
		String[] eachArg = args.split("\n");
		
		if(eachArg.length == 0 || eachArg.length > 2){
			return "Invalid number of Arguments";
		}
		
		List<Path> validFilePaths = getValidFilePathsFromString(eachArg, 1, 1);
		
		if(validFilePaths.size() == 0)
			return "\n";
		
		return performGrep(strPattern, validFilePaths.get(0));
	}

	String performGrep(String strPattern, String line){
		Pattern pattern;
		pattern = Pattern.compile(strPattern);
		String output="";
		Matcher matcher =  pattern.matcher(line);
		if(matcher.find())
		{
			if(matcher.group().length() >0)
				return line + "\n";
		}
		
		return "";
	}
	
	String performGrep(String strPattern, Path filePath)
	{
		Pattern pattern;
		pattern = Pattern.compile(strPattern);
		List<String> linesInFile = new ArrayList<>();
		String output="";
		
		try {
			linesInFile = Files.readAllLines(filePath);
		} catch (IOException e) {
			return "";
		}
		
		Matcher matcher;
		for(String line : linesInFile){
			matcher =  pattern.matcher(line);
			if(matcher.find())
			{
				if(matcher.group().length() >0)
					output += line +"\n";
			}
		}
		
		return output;
	}
	/*
	 *	Args is of format
	 * [pattern\nfilePath] 
	 */
	@Override
	public String grepFromMultipleFiles(String args) {
		if(grepInvalidPatternInFile(args) != null){
			return "\n";
		}
		String strPattern = args.split("\n")[0];
		String[] eachArg = args.split("\n");
		
		if(eachArg.length >=0 && eachArg.length <= 2){
			return "Invalid number of Arguments";
		}
		
		List<Path> validFilePaths = getValidFilePathsFromString(eachArg, 1, eachArg.length-1);
		
		if(validFilePaths.size() == 0)
			return "\n";
		
		String output = "";
		
		for(Path filePath : validFilePaths){
			output+= performGrep(strPattern, filePath);
		}
		return output;
	}

	/*
	 *	Args is of format
	 * [pattern] 
	 */
	@Override
	public String grepInvalidPatternInStdin(String args) {
		
		String[] eachArg = args.split("\n");
		 
		if(eachArg.length == 0)
			return "No Pattern Found";
		 
		 // Check if pattern
		 Pattern p;
		 
		 try
		 {
			 p = Pattern.compile(eachArg[0]);
		 }catch(PatternSyntaxException e){
			 return "Invalid Pattern";
		 }
		 
		 return null;//No errors
	}

	/*
	 *	Args is of format
	 * [pattern\nfilePath] 
	 */
	@Override
	public String grepInvalidPatternInFile(String args) {
		 String[] eachArg = args.split("\n");
		 
		 if(eachArg.length == 0)
			 return "No Pattern Found";
		 
		 // Chheck if pattern is ok
		 Pattern p;
		 
		 try
		 {
			 p = Pattern.compile(eachArg[0]);
		 }catch(PatternSyntaxException e){
			 return "Invalid Pattern";
		 }
		 
		 return null;//No errors
	}

}
