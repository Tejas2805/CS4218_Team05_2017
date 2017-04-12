package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplication implements Application {

	private static final String NULL_ALL = "args, stdin, stdout are null";

	private static final int DEFAULT_LINES = 10;
	private static final String NEWLINE = System.getProperty("line.separator");
	private static final String INVALID_FORMAT = "Invalid Command Format" + NEWLINE + "Usage: head [-n lines] [file]";

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if (checkNullInput(args, stdin, stdout)) {
			throw new HeadException(NULL_ALL);
		}else if(stdout==null){
			throw new HeadException("stdout is null");
		}
		checkArgumentLengthAndRun(args, stdin, stdout);
	}

	/**
	 * @param args
	 *            contains array of data that consists of number of lines and
	 *            file path"
	 * @param stdin
	 *            is the InputStream
	 * @param stdout
	 *            is the OutputStream 
	 *This method check the arguments and print the output accordingly
	 */
	private void checkArgumentLengthAndRun(String[] args, InputStream stdin, OutputStream stdout) throws HeadException {
		if (args == null || args.length == 0) {
			if(stdin==null){
				throw new HeadException("stdin is null");
			}
			readFromStdin(stdin, stdout);
		}else{

			int lineNumber = -1;
			ArrayList<String> listOfArgs = new ArrayList<String>();
			lineNumber = checkDuplicateLineNumbers(args,  listOfArgs);
			if (lineNumber == -1) {
				String[] checkedArgs = new String[1];
				readEveryFilePath(checkedArgs, stdout, listOfArgs);
			}else if(listOfArgs.isEmpty()){
				if(stdin==null){
					throw new HeadException("stdin is null");
				}
				if (args[0].equals("-n")) {
					try {
						readWithLines(args, stdin, stdout);
					} catch (IOException io) {
						throw (HeadException) new HeadException(io.getMessage()).initCause(io);
					}
				} else {
					throw new HeadException(INVALID_FORMAT);
				}
			}
			else {readEveryFilePathWithLineNumbers(stdout, lineNumber, listOfArgs);
			}
		}
	}
	/**
	 * @param args
	 *            contains array of data that consists of number of lines and
	 *            file path"
	 * @param stdin
	 *            is the InputStream
	 * @param stdout
	 *            is the OutputStream 
	 * This method read every file path and print the requested line numbers of the files
	 * 
	 */
	private void readEveryFilePathWithLineNumbers(OutputStream stdout, int lineNumber, ArrayList<String> listOfArgs)
			throws HeadException {
		String[] args;
		args = new String[3];
		args[0] = "-n";
		args[1] = "" + lineNumber;
		for (int i = 0; i < listOfArgs.size(); i++) {
			args[2] = listOfArgs.get(i);
			try {
				readWithLinesAndDirectory(args, stdout);
			} catch (IOException ioe) {
				throw (HeadException) new HeadException(ioe.getMessage()).initCause(ioe);
			}
		}
	}
	/**
	 * @param args
	 *            contains array of data that consists of number of lines and
	 *            file path"
	 * @param stdout
	 *            is the OutputStream 
	 * @param listOfArgs
	 * 			  is the Arraylist that stores the files path           
	 * This method read every file path and print first 10 lines of each file
	 * 
	 */
	private void readEveryFilePath(String[] args, OutputStream stdout, ArrayList<String> listOfArgs)
			throws HeadException {
		for (int i = 0; i < listOfArgs.size(); i++) {
			args[0] = listOfArgs.get(i);
			try {
				readFromPath(args, stdout);
			} catch (IOException ioe) {
				throw (HeadException) new HeadException(ioe.getMessage()).initCause(ioe);
			}
		}
	}
	/**
	 * @param args1
	 *            contains array of data that consists of number of lines and
	 *            file path"
	 * @param listOfArgs
	 *            is the ArrayList that stores all the path after checking 
	 * This method read the String in args1 to check the validity of command, 
	 * remove duplicate "-n" command and store the parth in ArrayList
	 * 
	 */
	private int checkDuplicateLineNumbers(String[] args1,  ArrayList<String> listOfArgs)
			throws HeadException {
		int lineCount = -1;
		boolean checkGotLineNumber = false;
		for (int i = 0; i < args1.length; i++) {
			try {
				if (args1[i].equals("-n")) {
					lineCount = Integer.parseInt(args1[i + 1]);
					i++;
					checkGotLineNumber = true;
				} else if (args1[i].startsWith("-")) {
					throw new HeadException(INVALID_FORMAT);
				} else {
					listOfArgs.add(args1[i]);
				}
			} catch (Exception e) {
				throw (HeadException) new HeadException(INVALID_FORMAT).initCause(e);
			}
		}
		if(checkGotLineNumber && lineCount<0){
			throw (HeadException) new HeadException("Invalid Format"+NEWLINE+"Line Number must be non-negative value");
		}
		return lineCount;
	}

	/**
	 * @param args
	 *            contains array of data that consists of number of lines and
	 *            file path"
	 * @param stdout
	 *            is the OutputStream This method reads the file provided and
	 *            print the output accordingly
	 */
	private void readWithLinesAndDirectory(String[] args, OutputStream stdout) throws HeadException, IOException {
		int lineCount = Integer.parseInt(args[1]);
		if (lineCount <= 0) {
			throw new HeadException("Invalid Line Count");
		}

		Path path = Paths.get(args[2]);
		if (Files.notExists(path)) {
			throw new HeadException("File Not Exists");
		}
		BufferedReader buffReader = Files.newBufferedReader(path);
		String line;
		for (int i = 0; i < lineCount; i++) {
			if ((line = buffReader.readLine()) == null) {
				break;
			}
			stdout.write(line.getBytes());
			stdout.write(NEWLINE.getBytes());
		}
	}

	/**
	 * @param args
	 *            contains array of data that consists of number of lines"
	 * @param stdin
	 *            is the InputStream
	 * @param stdout
	 *            is the OutputStream This method reads the file from stdin and
	 *            print the output accordingly
	 * @throws HeadException
	 */
	private void readWithLines(String[] args, InputStream stdin, OutputStream stdout)
			throws IOException, HeadException {

		int lineCount;
		try {
			lineCount = Integer.parseInt(args[1]);
		} catch (Exception e) {
			throw (HeadException) new HeadException(INVALID_FORMAT).initCause(e);
		}
		try {
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));
			String line = null;

			for (int i = 0; i < lineCount; i++) {
				if ((line = buffReader.readLine()) == null) {
					break;
				}
				stdout.write(line.getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		} catch (Exception e) {
			throw (HeadException) new HeadException(e.getMessage()).initCause(e);
		}
	}

	/**
	 * @param args
	 *            contains array of data that consists of file path"
	 * @param stdout
	 *            is the OutputStream This method reads the file from path
	 *            provided in args and print the output accordingly
	 */
	private void readFromPath(String[] args, OutputStream stdout) throws HeadException, IOException {
		Path path = Paths.get(args[0]);
		if (Files.notExists(path)) {
			throw new HeadException("File Not Exists");
		}
		BufferedReader buffReader = Files.newBufferedReader(path);
		String line;
		for (int i = 0; i < DEFAULT_LINES; i++) {
			if ((line = buffReader.readLine()) == null) {
				break;
			}
			stdout.write(line.getBytes());
			stdout.write(NEWLINE.getBytes());
		}
	}

	/**
	 * @param stdin
	 *            is the InputStream
	 * @param stdout
	 *            is the OutputStream This method reads the file from stdin and
	 *            print the output accordingly
	 * @throws HeadException
	 */
	private void readFromStdin(InputStream stdin, OutputStream stdout) throws HeadException {
		for (int i = 0; i < DEFAULT_LINES; i++) {
			try {
				int intCount;
				while ((intCount = stdin.read()) != -1) {
					stdout.write(intCount);
				}

			} catch (IOException io) {
				throw (HeadException) new HeadException(io.getMessage()).initCause(io);
			}
		}
	}

	/**
	 * @param args
	 *            contains array of data that consists of number of lines and
	 *            file path"
	 * @param stdin
	 *            is the InputStream
	 * @param stdout
	 *            is the OutputStream 
	 * This method checks whether args, stdin and stdout are all null
	 */
	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args == null) && (stdin == null) && (stdout == null);
	}

}
