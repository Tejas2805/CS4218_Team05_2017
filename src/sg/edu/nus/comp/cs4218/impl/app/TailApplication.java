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
import sg.edu.nus.comp.cs4218.exception.TailException;


public class TailApplication implements Application{

	private static final String NULL_ALL = "args, stdin, stdout are null";

	private static final int LINE_COUNT = 10;

	private static final String NEWLINE = System.getProperty("line.separator");
	private static final String INVALID_FORMAT = "Invalid Command Format" + NEWLINE + "Usage: tail [-n lines] [file]";

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if(checkNullInput(args,stdin,stdout)){
			throw new TailException(NULL_ALL);
		}else if(stdout==null){
			throw new TailException("stdout is null");
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
	private void checkArgumentLengthAndRun(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		if(args.length==0 || args==null){
			if(stdin==null){
				throw new TailException("stdin is null");
			}
			readFromStdin(stdin, stdout);
			}else{
			ArrayList<String> listOfArgs = new ArrayList<String>();
			int lineNumber = checkDuplicateLineNumbers(args, listOfArgs);
			if (lineNumber == -1) {
				if(stdin==null){
					throw new TailException("stdin is null");
				}
				String[] checkedArgs = new String[1];
				for (int i = 0; i < listOfArgs.size(); i++) {
					checkedArgs[0] = listOfArgs.get(i);
					try {
						readFromPath(checkedArgs, stdout);
					}catch (TailException ioe) {
						throw (TailException) new TailException(ioe.getMessage()).initCause(ioe);
					}
				}
			}else if(listOfArgs.isEmpty()){
				readWithLineNumber(args, stdin, stdout);
			}else{
				String[] args1;
				args1 = new String[3];
				args1[0] = "-n";
				args1[1] = "" + lineNumber;
				for (int i = 0; i < listOfArgs.size(); i++) {
					args1[2] = listOfArgs.get(i);
					if(args1[0].equals("-n")){
						readWithLinesAndDirectory(args1, stdout);
					}else{
						throw new TailException(INVALID_FORMAT);
					}
				}
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
			throws TailException {
		int lineCount = -1;
		for (int i = 0; i < args1.length; i++) {
			try {
				if (args1[i].equals("-n")) {
					lineCount = Integer.parseInt(args1[i + 1]);
					i++;
				} else if (args1[i].startsWith("-")) {
					throw new TailException(INVALID_FORMAT);
				} else {
					listOfArgs.add(args1[i]);
				}
			} catch (Exception e) {
				throw (TailException) new TailException(INVALID_FORMAT).initCause(e);
			}
		}
		return lineCount;
	}


	/**
	 * @param args contains array of data that consists of number of lines and file path"
	 * @param stdout is the OutputStream
	 * This method read the file with path provided and print the number of lines as given in args
	 * @throws TailException 
	 */	
	private void readWithLinesAndDirectory(String[] args, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = Integer.parseInt(args[1]);
			if(lineCount<=0){
				throw new TailException("Invalid Line Count");
			}
			Path path = Paths.get(args[2]);
			if(Files.notExists(path)){
				throw new TailException("File Not Exists");
			}
			BufferedReader buffRead = Files.newBufferedReader(path);
			String line;
			while((line = buffRead.readLine())!=null){
				if(counter<lineCount){
					output.add(line);
					counter++;
				}else{
					output.remove(0);
					output.add(line);
				}
			}
			for (int i = 0; i < counter; i++) {
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			throw (TailException) new TailException(io.getMessage()).initCause(io);
		}catch (Exception e){
			throw (TailException) new TailException(e.getMessage()).initCause(e);
		}
	}
	/**
	 * @param args contains array of data that consists of number of lines"
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method read from stdin and print number of lines as provided in args
	 */
	private void readWithLineNumber(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = Integer.parseInt(args[1]);
			if(lineCount<=0){
				throw new Exception("Invalid Line Count");
			}
			if(stdin==null){
				throw new Exception("stdin is null");
			}
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));
			String line = null;
			while((line = buffReader.readLine()) != null){
				if(counter==lineCount){
					output.remove(0);
				}else{
					counter++;
				}output.add(line);
			}
			for(int i=0;i<counter;i++){
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			throw (TailException) new TailException(io.getMessage()).initCause(io);
		}catch (Exception e){
			throw (TailException) new TailException(e.getMessage()).initCause(e);
		}
	}
	/**
	 * @param args contains array of data that consists of file path"
	 * @param stdout is the OutputStream
	 * This method read the file from path provided in args and print accordingly
	 */
	private void readFromPath(String[] args, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = LINE_COUNT;
			Path path = Paths.get(args[0]);
			if(Files.notExists(path)){
				throw new TailException("File Not Exists");
			}
			BufferedReader buffRead = Files.newBufferedReader(path);
			String line;
			while((line = buffRead.readLine())!=null){
				if(counter<lineCount){
					output.add(line);
					counter++;
				}else{
					output.remove(0);
					output.add(line);
				}
			}
			for (int i = 0; i < counter; i++) {
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			throw (TailException) new TailException(io.getMessage()).initCause(io);
			//throw new TailException("");
		}catch (Exception e){
			throw (TailException) new TailException(e.getMessage()).initCause(e);
		}
	}
	/**
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method read from stdin and print the output accordingly
	 */
	private void readFromStdin(InputStream stdin, OutputStream stdout) {
		BufferedReader buffRead = null;

		String line;
		ArrayList<String> output = new ArrayList<String>();
		int counter = 0;
		try {
			buffRead = new BufferedReader(new InputStreamReader(stdin));
			while ((line = buffRead.readLine()) != null) {
				if(counter<LINE_COUNT){
					output.add(line);
					counter++;
				}else{
					output.remove(0);
					output.add(line);
				}
			}
			for(int i = 0; i < output.size(); i++){
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}

		} catch (IOException io) {
			new TailException(io.getMessage());
		}
	}

	/**
	 * @param args contains array of data that consists of number of lines and file path"
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method checks whether args, stdin and stdout are all null
	 */
	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args==null) && (stdin==null) && (stdout==null);
	}

}