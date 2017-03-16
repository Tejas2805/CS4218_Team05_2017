package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.app.Date;
import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplication implements Date{
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws DateException {
		// TODO Auto-generated method stub
		try {
			stdout.write(printCurrentDate("").getBytes());
		} catch (IOException e) {
			throw (DateException) new DateException("IOException error").initCause(e);
		}
	}

	@Override
	public String printCurrentDate(String args) {
		// TODO Auto-generated method stub
		String results = "";
		java.util.Date date = new java.util.Date();
		results = date.toString().replace('+', '-') + System.lineSeparator();
		
		return results.replaceAll("GMT-08:00", "SGT");
	}

}
