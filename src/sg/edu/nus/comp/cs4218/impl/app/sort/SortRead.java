package sg.edu.nus.comp.cs4218.impl.app.sort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortRead {
	/**
	 * This methods convert the inputStream data to string
	 * @param input stream data of inputStream
	 * @return String inputStream data to String
	 */
	public String readInputStream(InputStream inputStream) throws SortException {

		BufferedReader bufReader = null;
		StringBuilder strBuilder = new StringBuilder();

		String line;
		try {

			bufReader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = bufReader.readLine()) != null) {
				strBuilder.append(line);
			}

		} catch (IOException e) {
			throw (SortException) new SortException("error reading input stream").initCause(e); 
		} finally {
			if (bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					throw (SortException) new SortException("error closing buffe read input stream").initCause(e); 
				}
			}
		}
		return strBuilder.toString();
	}


	/**
	 * This method extract the data from a file and convert it to string
	 * @param fileName name of file
	 * @return String file data to String
	 * @throws SortException 
	 */
	public String readFromFile(String fileName) throws SortException{
		BufferedReader bufReader = null;
		String fileContent = "";
		
		try {
			
			bufReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufReader.readLine()) != null) {
				
				fileContent += line + System.lineSeparator();
			}
		} catch (IOException e) {
			throw (SortException)new SortException("Invalid File").initCause(e);
		} finally {
			try {
				if (bufReader != null){
					bufReader.close();
				}
			} catch (IOException ex) {
				throw (SortException)new SortException("Buffer error").initCause(ex);
			}
		}
		return fileContent;//.trim();
	}
}
