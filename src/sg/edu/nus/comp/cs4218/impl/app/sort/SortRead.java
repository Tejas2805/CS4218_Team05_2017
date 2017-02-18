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
			e.printStackTrace();
			//throw new SortException("Error reading input stream");
		} finally {
			if (bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
					//throw new SortException("Error reading input stream");
				}
			}
		}
		return strBuilder.toString();
	}


	/**
	 * This method extract the data from a file and convert it to string
	 * @param fileName name of file
	 * @return String file data to String
	 */
	public String readFromFile(String fileName){
		BufferedReader bufReader = null;
		String fileContent = "";
		try {

			bufReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufReader.readLine()) != null) {
				
				fileContent += line + System.lineSeparator();
			}
		} catch (IOException e) {
			//throw new SortException("error reading file");
			e.printStackTrace();
		} finally {
			try {
				if (bufReader != null){
					bufReader.close();
				}

			} catch (IOException ex) {
				//throw new SortException("error closing buffer reader");
				ex.printStackTrace();
			}
		}
		return fileContent.trim();
	}
}
