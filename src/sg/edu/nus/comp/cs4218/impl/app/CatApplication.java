package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.impl.app.file.FileHandler;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>cat [FILE]...</code>
 * <dl>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class CatApplication implements Application {

	FileHandler fileHandler = new FileHandler();
	
	/**
	 * Runs the cat application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws CatException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws CatException {

		if(stdout == null){
			catWithNoOutputStream();
		}
		
		if (args == null || args.length == 0) {
			catWithNoArguments(stdin, stdout);
		} else {

			catWithArguments(args, stdout);
		}
	}

	/**
	 * This methods checks for valid filepath
	 * and concatenates the output and displays them
	 * @param args Array of paths to files
	 * @return none
	 */
	private void catWithArguments(String[] args, OutputStream stdout) {

		
			Path filePath;
			List<Path> filePathArray = new ArrayList<>();
			Path currentDir = Paths.get(Environment.currentDirectory);
			boolean isFileReadable = false;

			for (int i = 0; i < args.length; i++) {
				filePath = currentDir.resolve(args[i]);
				try {
					isFileReadable = fileHandler.checkIfFileIsReadable(filePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (isFileReadable) {
					filePathArray.add(filePath);
					
				}
			}

			
			// file could be read. perform cat command
			if (filePathArray.size() > 0) {
				for (int j = 0; j < filePathArray.size(); j++) {
					try {
						byte[] byteFileArray = Files
								.readAllBytes(filePathArray.get(j));
						stdout.write(byteFileArray);
						stdout.write("\n".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}else{
				try {
					stdout.write("\n".getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	}

	/**
	 * This methods throws an exception when no outputstream is included
	 * and concatenates the output and displays them
	 * @return none
	 */
	private void catWithNoOutputStream() throws CatException {
		throw new CatException("OutputStream not provided");
	}


	/**
	 * This methods throws an exception when no inputstream is found
	 * Else, it would perform cat with the inputstream
	 * @param stdin InputStream
	 * @param stdout OutputStream
	 * @return none
	 */

	private void catWithNoArguments(InputStream stdin, OutputStream stdout) throws CatException {
		if (stdin == null) {
			throw new CatException("InputStream not provided");
		}
		try {
			int intCount;
			while ((intCount = stdin.read()) != -1) {
				stdout.write(intCount);
			}
			stdout.write("\n".getBytes());
		} catch (Exception exIO) {
			exIO.printStackTrace();
		}
	}

}
