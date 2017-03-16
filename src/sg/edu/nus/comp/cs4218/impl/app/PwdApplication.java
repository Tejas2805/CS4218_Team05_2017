package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;

public class PwdApplication implements Application{
	
	/**
	 * This method execute the pwd function and write the data to output stream
	 * Print the current working directory
	 * @param args contains no argument
	 * @param stdin input stream of data
	 * @param stdout data is written to the output stream 
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) 
			throws PwdException {
		String currDir = "";
		
		if (args.length == 0) {
			//Return the current working directory followed by a newline
			currDir = Environment.currentDirectory + System.lineSeparator();
		    try {
		    	stdout.write(currDir.getBytes());
            } catch (IOException e) {
            	throw (PwdException) new PwdException("Error writing to stdout").initCause(e);
            }
		} else {
			throw new PwdException("Invalid Arguments");
		}
	}
}
