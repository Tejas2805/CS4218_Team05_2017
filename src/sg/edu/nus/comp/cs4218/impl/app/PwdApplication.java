package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;

public class PwdApplication implements Application{
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) 
			throws PwdException {
		String currDir = "";
		
		if (args.length == 0) {
			//Return the current working directory followed by a newline
			currDir = Environment.currentDirectory + "\n\n";
		    try {
		    	stdout.write(currDir.getBytes());
            } catch (IOException e) {
               e.printStackTrace();    
            }
		} else {
			throw new PwdException("Invalid Arguments");
		}
	}
}
