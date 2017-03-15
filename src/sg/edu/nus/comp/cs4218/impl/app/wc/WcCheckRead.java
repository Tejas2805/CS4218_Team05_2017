package sg.edu.nus.comp.cs4218.impl.app.wc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WcCheckRead {
	/*
	 * Read from either a file or inputstream and convert them to String
	 * @param fileName name of the text file
	 * @param stdin the inputstream data
	 * return the data in String format 
	 */
	public String readFileStdin(String fileName, InputStream stdin){
		InputStream inputStream = null; 
		InputStreamReader inputStreamReader= null;
		BufferedReader bufRead = null;
		String results = "";
		try {
			if(fileName.isEmpty()){
				inputStream = stdin;
			}else{
				inputStream = new FileInputStream(fileName); 	
			}
			inputStreamReader = new InputStreamReader(inputStream);
			bufRead = new BufferedReader(inputStreamReader);
		
			int value = 0;
			
			while((value = bufRead.read()) != -1){
				char charVal = (char)value;
				results += String.valueOf(charVal);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		     if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		     if(inputStreamReader!=null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		     if(bufRead!=null){
				try {
					bufRead.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
	     }
		return results;
	}
	
	/**
	 * Determine if the file is valid. Throw exception if the file is not valid
	 * @param fileName name of the file
	 */
	public boolean checkValidFile(String fileName){
		BufferedReader bufReader = null;
		boolean isValid = false;
		try {

			bufReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufReader.readLine()) != null) {
			}
			isValid = true;
		} catch (IOException e) {
			System.out.println("Invalid file or option: " + fileName); //e.printStackTrace();
			isValid = false;
			return isValid;
			//throw (WcException) new WcException("error reading file").initCause(e);
		} finally {
			try {
				if (bufReader != null){
					bufReader.close();
				}
				isValid = true;
			} catch (IOException ex) {
				ex.printStackTrace();
				isValid = false;
			
				//throw (SortException) new SortException("error closing buffer reader").initCause(ex);
			}
		}
		return isValid;
	}
}
