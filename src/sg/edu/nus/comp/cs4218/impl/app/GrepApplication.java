package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		
		pattern = Pattern.compile(args[0]);
		
		if(stdout == null)
			throw new GrepException("OutputStream not provided");
		
		if (args == null || args.length == 0) {
			if (stdin == null) {
				throw new GrepException("InputStream not provided");
			} else {
				//TODO
				//return grepFromStdin(); 
			}
		}else {
			int numOfFiles = args.length - 1;

			if (numOfFiles > 0) {
				Path filePath;
				Path[] filePathArray = new Path[numOfFiles];
				Path currentDir = Paths.get(Environment.currentDirectory);
				boolean isFileReadable = false;

				for (int i = 1; i < numOfFiles; i++) {
					filePath = currentDir.resolve(args[i]);
					isFileReadable = checkIfFileIsReadable(filePath);
					if (isFileReadable) {
						filePathArray[i] = filePath;
					}
				}

				// file could be read. perform grep command
				if (filePathArray.length == 1) {
					grepFromOneFile(filePathArray[0]);					
				}
				else if(filePathArray.length>1){

					grepFromMultipleFiles(filePathArray);;
				}
			}
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
	 */
	
	boolean checkIfFileIsReadable(Path filePath) throws CatException {
		
		if (Files.isDirectory(filePath)) {
			throw new CatException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new CatException("Could not read file");
		}
	}
		
	@Override
	public String grepFromStdin(String filePath) {
		List<String> linesfromFile = Files.readAllLines(filePath);
		
	}

	@Override
	public List<String> grepFromOneFile(Path filePath) {
		List<String> linesInFile = new ArrayList<>();
		try {
			linesInFile = Files.readAllLines(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> output = new ArrayList<String>();
		Matcher matcher;
		
		for(String line : linesInFile){
			matcher =  pattern.matcher(line);
			if(matcher.find())
			{
				if(matcher.group().length() >0)
					output.add(line);
			}
		}
		
		return output;
	}

	@Override
	public List<String> grepFromMultipleFiles(Path[] filePaths) {
		List<String> output = new ArrayList<>();
		List<String> fileOutput;
		
		for(Path filePath : filePaths){
			fileOutput = grepFromOneFile(filePath);
			for(String string : fileOutput){
				output.add(filePath.toString() + ": " + string);
			}
		}
		return output;
	}

	@Override
	public String grepInvalidPatternInStdin(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grepInvalidPatternInFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

}
