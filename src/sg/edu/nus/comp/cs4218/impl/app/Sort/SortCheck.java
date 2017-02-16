package sg.edu.nus.comp.cs4218.impl.app.Sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortCheck {
	/**
	 * Determine if the ASCII value belongs to a special char
	 * @param asciiValue ASCII value of char
	 * @return boolean true if the ASCII value belongs to a special char, false if not
	 */
	public boolean isSpecialChar(char asciiValue){
		boolean isSpecial = true;

		if(asciiValue >= 48 && asciiValue <= 59){
			isSpecial = false;
		}
		if(asciiValue >= 65 && asciiValue <= 90){
			isSpecial = false;
		}
		if(asciiValue >= 97 && asciiValue <= 122){
			isSpecial = false;
		}

		return isSpecial;
	}


	/**
	 * Determine if a file exist
	 * @param fileName name of the file
	 * @return boolean true if file exists
	 */
	public boolean isFile(String fileName){
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * Determine if "-n" is present and sort number by numerical order 
	 * @param condition the argument(e.g. "-n")
	 * @return boolean true "-n" is is present
	 */
	public boolean isSortByNumCondition(String conditon){
		boolean isSortByNum = false;

		if("-n".equals(conditon)){
			isSortByNum = true;
		}
		return isSortByNum;
	}

	/**
	 * Determine if the first word of each line is a number
	 * @param line the input line
	 * @return boolean true if first word is a number, false if not
	 */
	public boolean isFirstWordNum(String line){
		String firstWord = line.split(" ")[0];
		
		try { 
	        Integer.parseInt(firstWord); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		
		return true;
	}
	
	/**
	 * Determine if the condition is valid. Throw exception if the condition is not "-n"
	 * @param condition the argument(e.g. "-n")
	 */
	public void checkValidCondition(String condition) throws SortException{
		if(!"".equals(condition) && !"-n".equals(condition)){
			throw new SortException("Invalid Condition Statment");
		}
	}

	/**
	 * Determine if the file is valid. Throw exception if the file is not valid
	 * @param fileName name of the file
	 */
	public void checkValidFile(String fileName) throws SortException{
		BufferedReader bufReader = null;
		try {

			bufReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufReader.readLine()) != null) {
			}
		} catch (IOException e) {
			e.printStackTrace();
			//throw new SortException("error reading file");
			//e.printStackTrace();
		} finally {
			try {
				if (bufReader != null){
					bufReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				//throw new SortException("error closing buffer reader");
				//ex.printStackTrace();
			}
		}
	}
}
