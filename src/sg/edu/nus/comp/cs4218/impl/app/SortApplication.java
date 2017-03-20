package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortCheck;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortData;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortNumber;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortOrder;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class SortApplication implements Sort{
	
	SortRead sortRead = new SortRead();
	SortCheck sortCheck = new SortCheck();
	SortOrder sortOrder = new SortOrder();
	SortNumber sortNum = new SortNumber();
	SortData sortData = new SortData();
	
	/**
	 * This method execute the sort function and write the data to output stream
	 * FILE is read and converted to string 
	 * InputStream are also converted to string before being process
	 * Error message will be printed out if FILE or OPTION are invalid 
	 * However, if the remaining FILE or OPTION are valid the command will still be executed
	 * The position of FILE and OPTION does not have to be in sequence
	 * @param args contains an array of arguments such as FILE or OPTION -n
	 * args does not contains "sort" at the front
	 * @param stdin input stream of data
	 * @param stdout data is written to the output stream 
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException {
		String fileName = "";
		String sortCondition = "";
		String results = "";
		
		if(stdout == null){
			throw new SortException("No args or outputstream");
		}
		if(args == null || args.length == 0){
			if(stdin == null){
				throw new SortException("Input stream null");
			}
			String readStdin = sortRead.readInputStream(stdin);
			results = sortAll(sortCondition + System.lineSeparator() + readStdin);
		}else if(args.length == 1){
			fileName = args[0];
			sortCheck.checkValidFile(fileName);
			String data = sortRead.readFromFile(fileName);
			sortRead.readFileStdin(fileName, null);
			String toSort = "" + System.lineSeparator() + data;
			results = sortAll(sortCondition + System.lineSeparator() + toSort);
		}else if(args.length >= 2){
			String data = "";
			for(int i=0; i<args.length; i++){
				if("-n".equals(args[i])){
					sortCondition = args[i];
					continue;
				}
				fileName = args[i];
				sortCheck.checkValidFile(fileName);
				data += sortRead.readFromFile(fileName);
			}
			String toSort = "" + System.lineSeparator() + data;
			results = sortAll(sortCondition + System.lineSeparator() + toSort);
			
		}else{
			
			throw new SortException("Invalid Argument");
		}
		
		try {
			stdout.write(results.getBytes());
		} catch (IOException e) {	
			throw (SortException) new SortException("Error writing to stdout").initCause(e);
		}
	}

	/*
	 * 
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with simple letter sorted in ascending order.
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortStringsSimple(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String data = args[1];
		String simpleData = sortData.getSimpleData(data);
		String sortSimple = sortOrder.sortData(simpleData);

		return sortSimple;
	}
	
	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with capital letter sorted in ascending order.
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortStringsCapital(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String data = args[1];
		String capitalData = sortData.getCapitalData(data);
		String sortCapital = sortOrder.sortData(capitalData);

		return sortCapital;

	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with numbers sorted in ascending order.
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortNumbers(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String sortCondtion = args[0];
		
		String numberData = sortData.getNumberData(toSort);
		String sortNumber = "";

		if(sortCheck.isSortByNumCondition(sortCondtion)){
			sortNumber = sortNum.sortNumData(numberData);
		}else{
			sortNumber = sortOrder.sortData(numberData);
		}
		
		return sortNumber;
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char sorted in ascending order.
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String sortCondtion = args[0];
		String data = args[1];
		String specialCharData = sortData.getSpecialCharData(data);
		String sortSpecialChar = "";
		
		if(sortCheck.isSortByNumCondition(sortCondtion)){
			specialCharData = sortNum.sortNotNumData(specialCharData);
		}
		sortSpecialChar = sortOrder.sortData(specialCharData);
		
		return sortSpecialChar;
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with capital letter and simple letters sorted in order of capital letter, number 
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSimpleCapital(String toSort) {
		// TODO Auto-generated method stub
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with number and simple letters sorted in order of number, simple letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSimpleNumbers(String toSort) {
		// TODO Auto-generated method stub
		String numbers = sortNumbers(toSort);
		String simple = sortStringsSimple(toSort);

		String results = numbers + System.lineSeparator() + simple;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special chat and simple letters sorted in order of special char, simple letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSimpleSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + simple;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with number and capital letters sorted in order of number, capital letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortCapitalNumbers(String toSort) {
		// TODO Auto-generated method stub
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);

		String results = numbers + System.lineSeparator() + capital;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char and capital letters sorted in order of special char, capital letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String capital = sortStringsCapital(toSort);

		String results = specialChars + System.lineSeparator() + capital;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char and number sorted in order of special char, number
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);

		String results = specialChars + System.lineSeparator() + numbers;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with number, capital letter, simple letter sorted in order of number, capital letter, simple letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		// TODO Auto-generated method stub
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = numbers + System.lineSeparator() + capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char, capital letter, simple letter sorted in order of special char, capital letter, simple letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + capital + System.lineSeparator() + simple;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char, number, simple letter sorted in order of special char, number, simple letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + numbers + System.lineSeparator() + simple;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char, number, capital letter sorted in order of special char, number, capital letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);

		String results = specialChars + System.lineSeparator() + numbers + System.lineSeparator() + capital;
		return processResults(results);
	}

	/*
	 * @param toSort InputStream and lines from FILE that have been converted to String
	 * The String "toSort" does not contains "sort" or "-n" or "sort -n", only the data that is converted to string
	 * @return String a string of lines starting with special char, number, capital letter, simple letter sorted in order of special char, number, capital letter, simple letter
	 * Each line is separated by a line separator
	 */
	@Override
	public String sortAll(String toSort) {
		// TODO Auto-generated method stub
		String specialChars = sortSpecialChars(toSort);
		String numbers = sortNumbers(toSort);
		String capital = sortStringsCapital(toSort);
		String simple = sortStringsSimple(toSort);

		String results = specialChars + System.lineSeparator() + numbers + System.lineSeparator() + capital + System.lineSeparator() + simple;
		String finalResults = processResults(results);
		for(int i=0; i<sortRead.getNumNewLine(); i++){
			finalResults = System.lineSeparator() + finalResults;
		}
		return finalResults;
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
		;
		return newResults;
	}

	

}
