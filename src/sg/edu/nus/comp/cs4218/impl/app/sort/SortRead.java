package sg.edu.nus.comp.cs4218.impl.app.sort;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.sampled.Line;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.exception.WcException;

public class SortRead {

	int count = 0;

	/**
	 * This methods convert the inputStream data to string
	 * 
	 * @param input
	 *            stream data of inputStream
	 * @return String inputStream data to String
	 */
	public String readInputStream(InputStream inputStream) {// throws
															// SortException {

		BufferedReader bufReader = null;
		StringBuilder strBuilder = new StringBuilder();

		String line;
		try {

			bufReader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = bufReader.readLine()) != null) {
				if ("".equals(line)) {
					count += 1;
				}
				line += System.lineSeparator();
				strBuilder.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
			// throw (SortException) new SortException("error reading input
			// stream").initCause(e);
		} finally {
			if (bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
					// throw (SortException) new SortException("error closing
					// buffe read input stream").initCause(e);
				}
			}
		}
		return strBuilder.toString();
	}

	/**
	 * This method extract the data from a file and convert it to string
	 * 
	 * @param fileName
	 *            name of file
	 * @return String file data to String
	 * @throws SortException
	 */
	public String readFromFile(String fileName) {// throws SortException{
		BufferedReader bufReader = null;
		String fileContent = "";

		try {

			bufReader = new BufferedReader(new FileReader(fileName));
			String line;
			String temp = "";
			while ((line = bufReader.readLine()) != null) {
				if ("".equals(line)) {
					count += 1;
				}
				fileContent += line + System.lineSeparator();
				temp = line;
			}
			fileContent = fileContent.substring(0, fileContent.length() - System.lineSeparator().length());
		} catch (IOException e) {
			// e.printStackTrace();
			// throw (SortException)new SortException("Invalid
			// File").initCause(e);
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				// throw (SortException)new SortException("Buffer
				// error").initCause(ex);
			}
		}
		return fileContent;// .trim();
	}

	/*
	 * Read from either a file or inputstream and convert them to String
	 * 
	 * @param fileName name of the text file
	 * 
	 * @param stdin the inputstream data return the data in String format
	 */
	public String readFileStdin(String fileName, InputStream stdin) throws SortException {
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufRead = null;
		String results = "";
		try {
			if (fileName.isEmpty()) {
				inputStream = stdin;
			} else {
				inputStream = new FileInputStream(fileName);
			}
			inputStreamReader = new InputStreamReader(inputStream);
			bufRead = new BufferedReader(inputStreamReader);

			int value = 0;
			while ((value = bufRead.read()) != -1) {
				char charVal = (char) value;
				results += String.valueOf(charVal);
			}

		} catch (Exception e) {
			throw (SortException) new SortException("invalid file").initCause(e);
			// e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufRead != null) {
				try {
					bufRead.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return results;
	}

	/*
	 * Set the number of empty lines
	 */
	public int getNumNewLine() {
		return count;
	}
}
