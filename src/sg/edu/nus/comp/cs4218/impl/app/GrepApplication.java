package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.file.FileHandler;

public class GrepApplication implements Grep {

	FileHandler fileHandler = new FileHandler();

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {

		if (stdout == null) {
			throw new GrepException("OutputStream not provided");
		}
		String newArg = "";
		String output;

		if (args == null || args.length == 0) {
			throw new GrepException("No arguments found");
		} else {
			for (String arg : args) {
				newArg += arg + "\n";
			}
			int numOfFiles = args.length - 1;
			switch (numOfFiles) {
			case 0:
				output = grepZeroFiles(stdin, newArg);
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
			if (output == null)
				throw new GrepException("Error occurred");
			stdout.write(output.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This methods attempts to grep when no file(s) is/are specified It
	 * attempts to read from stdin to perform pattern match
	 * 
	 * @param stdin
	 *            Standard Input
	 * @param newArg
	 * @return String
	 * @throws GrepException
	 *             when stdin is null
	 */
	private String grepZeroFiles(InputStream stdin, String newArg) throws GrepException {
		String output;
		String temp = newArg;
		if (stdin == null) {
			throw new GrepException("InputStream not provided");
		}
		String stdinString = "";
		try {
			int intCharacter;
			char character;
			while ((intCharacter = stdin.read()) != -1) {
				character = (char) intCharacter;
				stdinString += character;
			}

		} catch (Exception exIO) {
			exIO.printStackTrace();
		}
		if (stdinString.compareTo("") == 0) {
			output = "";
		} else {
			temp += stdinString;
			output = grepFromStdin(temp);
			if (output == null)
				throw new GrepException("Error occurred");
		}
		return output;
	}

	/**
	 * This methods throws an exception when no inputstream is found Else, it
	 * would perform cat with the inputstream
	 * 
	 * @param stdin
	 *            InputStream
	 * @param stdout
	 *            OutputStream
	 * @return none
	 */
	@Override
	public String grepFromStdin(String args) {
		if (grepInvalidPatternInStdin(args) != null) {
			return null;
		}

		String[] eachArg = args.split("\n");
		String strPattern = eachArg[0];
		String output = "";

		for (int i = 1; i < eachArg.length; i++) {
			output += performGrep(strPattern, eachArg[i]);
		}

		return output;
	}

	/*
	 * Args is of format [pattern\nfilePath]
	 */
	@Override
	public String grepFromOneFile(String args) {
		if (grepInvalidPatternInFile(args) != null) {
			return null;
		}
		String strPattern = args.split("\n")[0];
		String[] eachArg = args.split("\n");

		if (eachArg.length == 0 || eachArg.length > 2) {
			return "Invalid number of Arguments";
		}

		List<Path> validFilePaths = fileHandler.getValidFilePathsFromString(eachArg, 1, 1);

		if (validFilePaths.isEmpty()) {
			return null;
		}

		return performGrep(strPattern, validFilePaths.get(0));
	}

	private String performGrep(String strPattern, String line) {
		Pattern pattern;
		pattern = Pattern.compile(strPattern);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find() && matcher.group().length() > 0) {
			return line + "\n";
		}

		return "";
	}

	String performGrep(String strPattern, Path filePath) {
		Pattern pattern = null;
		try {
			pattern = Pattern.compile(strPattern);
		} catch (PatternSyntaxException e) {

		}

		List<String> linesInFile = new ArrayList<>();
		String output = "";

		try {
			linesInFile = Files.readAllLines(filePath);
		} catch (IOException e) {
			return "";
		}

		Matcher matcher;
		for (String line : linesInFile) {
			matcher = pattern.matcher(line);
			if (matcher.find() && matcher.group().length() > 0) {
				output += line + "\n";
			}
		}

		return output;
	}

	/*
	 * Args is of format [pattern\nfilePath]
	 */
	@Override
	public String grepFromMultipleFiles(String args) {
		if (grepInvalidPatternInFile(args) != null) {
			return null;
		}
		String strPattern = args.split("\n")[0];
		String[] eachArg = args.split("\n");

		if (eachArg.length >= 0 && eachArg.length <= 2) {
			return "Invalid number of Arguments";
		}

		List<Path> validFilePaths = fileHandler.getValidFilePathsFromString(eachArg, 1, eachArg.length - 1);

		if (validFilePaths.isEmpty()) {
			return null;
		}

		String output = "";

		for (Path filePath : validFilePaths) {
			output += performGrep(strPattern, filePath);
		}
		return output;
	}

	/*
	 * Args is of format [pattern]
	 */
	@Override
	public String grepInvalidPatternInStdin(String args) {

		String[] eachArg = args.split("\n");

		if (eachArg.length == 0) {
			return "No Pattern Found";
		}

		try {
			Pattern.compile(eachArg[0]);
		} catch (PatternSyntaxException e) {
			return "Invalid Pattern";
		}

		return null;// No errors
	}

	/*
	 * Args is of format [pattern\nfilePath]
	 */
	@Override
	public String grepInvalidPatternInFile(String args) {
		String[] eachArg = args.split("\n");

		if (eachArg.length == 0) {
			return "No Pattern Found";
		}

		try {
			Pattern.compile(eachArg[0]);
		} catch (PatternSyntaxException e) {
			return "Invalid Pattern";
		}

		return null;// No errors
	}

}
