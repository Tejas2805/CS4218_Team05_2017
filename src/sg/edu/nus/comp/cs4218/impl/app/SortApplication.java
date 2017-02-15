package sg.edu.nus.comp.cs4218.impl.app;

import java.io.*;
import java.util.ArrayList;



import sg.edu.nus.comp.cs4218.app.Sort;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortApplication implements Sort{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException {
		// TODO Auto-generated method stub

		// intilize an InputStream
		//InputStream is = new ByteArrayInputStream("file content..blah blah".getBytes());
		//InputStream is = new ByteArrayInputStream(new byte[0]);

		String fileName = "";
		String sortCondition = "";
		String results = "";

		if(args.length == 0){
			String readStdin = readInputStream(stdin);
			if(readStdin.length() == 0){
				throw new SortException("Input stream empty");
			}
			results = sortAll(sortCondition + System.lineSeparator() + readStdin);
		}else if(args.length == 1){
			fileName = args[0];
			checkValidFile(fileName);
			results = sortAll(sortCondition + System.lineSeparator() + fileName);

		}else if(args.length == 2){
			sortCondition = args[0];
			checkValidCondition(sortCondition);
			checkValidFile(fileName);
			fileName = args[1];
			results = sortAll(sortCondition + System.lineSeparator() + fileName);
		}else{
			throw new SortException("More than two arguements");
		}
		printResults(results);
	}






	@Override
	public String sortStringsSimple(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String fileName= args[1];
		String data = fileName;
		if(isFile(fileName)){
			data = readFromFile(fileName);
		}
		String simpleData = getSimpleData(data);
		String sortSimple = sortData(simpleData);

		return sortSimple;
	}

	@Override
	public String sortStringsCapital(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String fileName= args[1];
		String data = fileName;
		if(isFile(fileName)){
			data = readFromFile(fileName);
		}
		String capitalData = getCapitalData(data);
		String sortCapital = sortData(capitalData);

		return sortCapital;

	}

	@Override
	public String sortNumbers(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String sortCondtion = args[0];
		String fileName = args[1];
		String data = fileName;
		if(isFile(fileName)){
			data = readFromFile(fileName);
		}
		String numberData = getNumberData(data);
		String sortNumber = sortData(numberData);

		if(isSortByNumCondition(sortCondtion)){
			sortNumber = sortNumData(numberData);
		}

		return sortNumber;
	}

	@Override
	public String sortSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String fileName= args[1];
		String data = fileName;
		if(isFile(fileName)){
			data = readFromFile(fileName);
		}
		String specialCharData = getSpecialCharData(data);
		String sortSpecialChar = sortData(specialCharData);

		return sortSpecialChar;
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		// TODO Auto-generated method stub
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		// TODO Auto-generated method stub
		String numbers = sortNumbers(toSort);
		String simple = sortStringsSimple(toSort);

		String results = numbers + System.lineSeparator() + simple;
		return processResults(results);
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + simple;
		return processResults(results);
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		// TODO Auto-generated method stub
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);

		String results = numbers + System.lineSeparator() + capital;
		return processResults(results);
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String capital = sortStringsCapital(toSort);

		String results = specialChars + System.lineSeparator() + capital;
		return processResults(results);
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);

		String results = specialChars + System.lineSeparator() + numbers;
		return processResults(results);
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		// TODO Auto-generated method stub
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = numbers + System.lineSeparator() + capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + numbers + System.lineSeparator() + simple;
		return processResults(results);
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);

		String results = specialChars + System.lineSeparator() + numbers + System.lineSeparator() + capital;
		return processResults(results);
	}

	@Override
	public String sortAll(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);



		String results = specialChars + System.lineSeparator() + numbers + System.lineSeparator() + capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a special character
	 */
	private String getSpecialCharData(String data){
		String [] dataAarr = data.split(System.lineSeparator());
		String specialCharData = "";

		for(String line : dataAarr){
			char[] charArr = line.toCharArray();
			if(isSpecialChar(charArr[0])){
				specialCharData += line + System.lineSeparator();
			}
		}
		return specialCharData.trim();
	}

	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a number
	 */
	private String getNumberData(String data){
		String [] dataAarr = data.split(System.lineSeparator());
		String numberData = "";

		for(String line : dataAarr){
			char[] charArr = line.toCharArray();
			if(charArr[0] >= 48 && charArr[0] <= 57){
				numberData += line + System.lineSeparator();
			}
		}
		return numberData.trim();
	}

	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a capital Letter
	 */
	private String getCapitalData(String data){
		String [] dataAarr = data.split(System.lineSeparator());
		String capitalData = "";

		for(String line : dataAarr){
			char[] charArr = line.toCharArray();
			if(charArr[0] >= 65 && charArr[0] <= 90){
				capitalData += line + System.lineSeparator();
			}
		}
		return capitalData.trim();
	}

	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a simple letter
	 */
	private String getSimpleData(String data){
		String [] dataAarr = data.split(System.lineSeparator());
		String simpleData = "";

		for(String line : dataAarr){
			char[] charArr = line.toCharArray();
			if(charArr[0] >= 97 && charArr[0] <= 122){
				simpleData += line + System.lineSeparator();
			}
		}
		return simpleData.trim();
	}

	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data sorted in ascending ASCII value order
	 */
	private String sortData(String data){
		String [] dataArr = data.split(System.lineSeparator());
		ArrayList<String> dataList = new ArrayList<String>();

		for(String line : dataArr){
			dataList.add(line);
		}
		ArrayList<String> fileList = sortByAscii(dataList);
		return arrayListToString(fileList);
	}

	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data sorted in ascending Numerical order
	 */
	private String sortNumData(String data){
		String [] dataArr = data.split(System.lineSeparator());
		ArrayList<String> dataList = new ArrayList<String>();

		for(String line : dataArr){
			dataList.add(line);
		}
		ArrayList<String> fileList = dataList;//sortByNum(dataList);
		return arrayListToString(fileList);
	}


	/**
	 * @param dataList ArrayList containing lines to be sorted in ascending ASCII value order
	 * @return ArrayList<String> data sorted in ascending ASCII value order
	 */
	private ArrayList<String> sortByAscii(ArrayList<String> dataList){

		ArrayList<String> fileList = new ArrayList<String>();
		boolean isNotFirstLine = false;

		for(int i=0; i<dataList.size(); i++){
			String newLine = dataList.get(i);
			if(isNotFirstLine){
				char[] charArr = newLine.toCharArray();
				fileList = sortAsciiList(fileList, charArr, newLine);
			}else{
				fileList.add(newLine);
				isNotFirstLine = true;	
			}
		}

		return fileList;
	}

	/**
	 * This methods insert the newLine in the correct sorted order of the ArrayList
	 * @param dataList ArrayList containing lines to be sorted in ascending ASCII value order
	 * @param newLineCharArr the new line which is split into a char Array
	 * @param newLine the new line to be sorted and inserted into the correct order in the Array List
	 * @return ArrayList<String> data sorted in ascending ASCII value order
	 */
	private ArrayList<String> sortAsciiList(ArrayList<String> dataList, char[] newLineCharArr, String newLine){
		ArrayList<String> sortedList = dataList;
		int newLineLen = newLine.length();
		int insertIndex = 0;
		boolean isSmaller = false;
		boolean isGreater = false;	
		for(int i=0; i<sortedList.size(); i++){
			String line = sortedList.get(i);
			char[] lineCharArr = line.toCharArray();
			int lineLen = line.length();

			isSmaller = false;
			isGreater = false;

			for(int j=0; j<Math.min(lineLen, newLineLen); j++){

				if(isSmaller || isGreater){
					break;
				}		
				int asciiNewLineValue =  asciiNumAlpha(newLineCharArr[j]);
				int asciiLineValue =   asciiNumAlpha(lineCharArr[j]);		
				if(asciiNewLineValue <asciiLineValue){
					insertIndex = i;
					isSmaller = true;
				}
				if(asciiNewLineValue > asciiLineValue){
					insertIndex = i+1;
					isGreater = true;
				}
				if(asciiNewLineValue == asciiLineValue){
					if( j+1 == lineLen){
						insertIndex = i+1;
						isGreater = true;
					}
					if( j+1 == newLineLen){
						insertIndex = i;
						isGreater = true;
					}				
				}
			}
			if(isSmaller){
				break;
			}
		}	
		sortedList.add(insertIndex, newLine);
		return sortedList;
	}

	/**
	 * This methods increase the ASCII value of number (0-9), capital (A-Z) & simple (a-z)
	 * @param char the character to be compared
	 * @return int the increase ACCI value multiply by 10
	 */
	private int asciiNumAlpha(char asciiValue){

		if(asciiValue >= 48 && asciiValue <= 59){
			return asciiValue * 10;
		}
		if(asciiValue >= 65 && asciiValue <= 90){
			return asciiValue * 10;
		}
		if(asciiValue >= 97 && asciiValue <= 122){
			return asciiValue * 10;
		}

		return asciiValue;
	}

	/**
	 * Determine if the ASCII value belongs to a special char
	 * @param asciiValue ASCII value of char
	 * @return boolean true if the ASCII value belongs to a special char, false if not
	 */
	private boolean isSpecialChar(char asciiValue){
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
	private boolean isFile(String fileName){
		File file = new File(fileName);
		return file.exists();
	}


	/**
	 * Determine if "-n" is present and sort number by numerical order 
	 * @param condition the argument(e.g. "-n")
	 * @return boolean true "-n" is is present
	 */
	private boolean isSortByNumCondition(String conditon){
		boolean isSortByNum = false;

		if("-n".equals(conditon)){
			isSortByNum = true;
		}
		return isSortByNum;
	}

	/**
	 * Determine if the condition is valid. Throw exception if the condition is not "-n"
	 * @param condition the argument(e.g. "-n")
	 */
	private void checkValidCondition(String condition) throws SortException{
		if(!"".equals(condition) && !"-n".equals(condition)){
			throw new SortException("Invalid Condition Statment");
		}
	}

	/**
	 * Determine if the file is valid. Throw exception if the file is not valid
	 * @param fileName name of the file
	 */
	private void checkValidFile(String fileName) throws SortException{
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

	/**
	 * This methods convert the inputStream data to string
	 * @param input stream data of inputStream
	 * @return String inputStream data to String
	 */
	private String readInputStream(InputStream inputStream) throws SortException {

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
	private String readFromFile(String fileName){
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

	/**
	 * This method concat all the ArrayList data into a single string
	 * @param list ArrayList of line of data
	 * @return String concat data of string separated by System.lineSeparator
	 */
	private String arrayListToString(ArrayList<String> list){
		String results = "";
		for(String line : list){
			results += line + System.lineSeparator();
		}
		return results.trim();
	}

	/**
	 * This method remove unnecessary System.lineSeparator from the final results
	 * @param results the results to be process
	 * @return String the proccessed results string
	 */
	private String processResults(String results){
		String[] resultsList = results.split(System.lineSeparator());
		String newResults = "";
		for(String strResults : resultsList){
			if(!"".equals(strResults)){
				newResults += strResults + System.lineSeparator();
			}
		}

		return newResults.trim();
	}

	/**
	 * This method print the final results in the command line
	 * @param results the results to be printed
	 */
	private void printResults(String results){
		String newLine = System.lineSeparator();
		String[] resultsList = results.split(newLine);

		for(String strResults : resultsList){
			System.out.println(strResults);
		}
	}










}
