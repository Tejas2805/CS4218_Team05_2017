package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplication implements Application {

	private static final String NULL_ALL = "args, stdin, stdout are null";

	private static final int DEFAULT_LINES = 10;
	private static final String NEWLINE = System.getProperty("line.separator");
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if(checkNullInput(args,stdin,stdout)){
			throw new HeadException(NULL_ALL);
		}
		checkArgumentLengthAndRun(args, stdin, stdout);
	}


	/**
	 * @param args contains array of data that consists of number of lines and file path"
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method check the arguments and print the output accordingly
	 */
	private void checkArgumentLengthAndRun(String[] args, InputStream stdin, OutputStream stdout) throws HeadException {
		if(args.length==0){
			readFromStdin(stdin, stdout);
		}else if(args.length==1){
			try {
				readFromPath(args, stdout);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				//throw new HeadException(ioe.getMessage());
			}
		}else if(args.length==2){
			if(args[0].equals("-n")){
				try {
					readWithLines(args, stdin, stdout);} catch (IOException io) {
						io.printStackTrace();
						//throw new HeadException(io.getMessage());
					}
			}else{
				throw new HeadException("Invalid Command Format" + NEWLINE +"Usage: head [-n lines] [file]");
			}
		}else if(args.length==3){
			if(args[0].equals("-n")){
				try{
					readWithLinesAndDirectory(args, stdout);
				}catch (IOException ioe) {
					ioe.printStackTrace();
					//throw new HeadException(ioe.getMessage());
				}
			}else{
				throw new HeadException("Invalid Command Format" + NEWLINE +"Usage: head [-n lines] [file]");
			}
		}else{
			throw new HeadException("Invalid Command Format" + NEWLINE +"Usage: head [-n lines] [file]");
		}
	}

	/**
	 * @param args contains array of data that consists of number of lines and file path"
	 * @param stdout is the OutputStream
	 * This method reads the file provided and print the output accordingly
	 */
	private void readWithLinesAndDirectory(String[] args, OutputStream stdout) throws HeadException, IOException {
		int lineCount = Integer.parseInt(args[1]);
		if(lineCount<=0){
			throw new HeadException("Invalid Line Count");
		}

		Path path = Paths.get(args[2]);
		if(Files.notExists(path)){
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
	 * @param args contains array of data that consists of number of lines"
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method reads the file from stdin and print the output accordingly
	 */
	private void readWithLines(String[] args, InputStream stdin, OutputStream stdout) throws IOException {
		int lineCount = Integer.parseInt(args[1]);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));
		String line = null;

		for (int i = 0; i < lineCount; i++) {
			if ((line = buffReader.readLine()) == null) {
				break;
			}
			stdout.write(line.getBytes());
			stdout.write(NEWLINE.getBytes());
		}

	}

	/**
	 * @param args contains array of data that consists of file path"
	 * @param stdout is the OutputStream
	 * This method reads the file from path provided in args and print the output accordingly
	 */	
	private void readFromPath(String[] args, OutputStream stdout) throws HeadException, IOException {
		Path path = Paths.get(args[0]);
		if(Files.notExists(path)){
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
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method reads the file from stdin and print the output accordingly
	 */
	private void readFromStdin(InputStream stdin, OutputStream stdout) {
		for (int i = 0; i < DEFAULT_LINES; i++) {
			try {
				int intCount;
				while ((intCount = stdin.read()) != -1) {
					stdout.write(intCount);
				}

			} catch (IOException io) {
				io.printStackTrace();
				//throw new HeadException(io.getMessage());
			}
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
