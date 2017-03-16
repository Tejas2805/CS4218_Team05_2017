package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.app.Date;
import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplication implements Date{
	
	/*
	 * Print the date in the format of  [week day] [month] [day] [hh:mm:ss] [time zone] [year] 
	 * Wed Dec 30 17:23:43 SGT 2009
	 * @param args take in args
	 * Date does not support any args so if there is args, the date will still be printed but an error will be shown to notify about the invalid args
	 * @param stdin inputStream
	 * @param stdout outputStream
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws DateException {
		// TODO Auto-generated method stub
		
		try {
			stdout.write(printCurrentDate("").getBytes());
			if(args.length >0){
				throw new DateException("Invalid args");
			}
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
