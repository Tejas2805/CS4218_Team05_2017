package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortCheck;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortNumber;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortOrder;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class SortApplication implements Sort{
	
	SortRead sortRead = new SortRead();
	SortCheck sortCheck = new SortCheck();
	SortOrder sortOrder = new SortOrder();
	SortNumber sortNum = new SortNumber();
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException {
		String fileName = "";
		String sortCondition = "";
		String results = "";
	
		if(args.length == 0){
			String readStdin = sortRead.readInputStream(stdin);
			if(readStdin.length() == 0){
				throw new SortException("Input stream empty");
			}
			//System.out.println(readStdin);
			results = sortAll(sortCondition + System.lineSeparator() + readStdin);
		}else if(args.length == 1){
			fileName = args[0];
			sortCheck.checkValidFile(fileName);
			results = sortAll(sortCondition + System.lineSeparator() + fileName);

		}else if(args.length == 2){
			sortCondition = args[0];
			fileName = args[1];
			sortCheck.checkValidCondition(sortCondition);
			sortCheck.checkValidFile(fileName);
			
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
		if(sortCheck.isFile(fileName)){
			data = sortRead.readFromFile(fileName);
		}
		String simpleData = getSimpleData(data);
		String sortSimple = sortOrder.sortData(simpleData);

		return sortSimple;
	}

	@Override
	public String sortStringsCapital(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String fileName= args[1];
		String data = fileName;
		if(sortCheck.isFile(fileName)){
			data = sortRead.readFromFile(fileName);
		}
		String capitalData = getCapitalData(data);
		String sortCapital = sortOrder.sortData(capitalData);

		return sortCapital;

	}

	@Override
	public String sortNumbers(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String sortCondtion = args[0];
		String fileName = args[1];
		String data = fileName;
		if(sortCheck.isFile(fileName)){
			data = sortRead.readFromFile(fileName);
		}
		String numberData = getNumberData(data);
		String sortNumber = "";

		if(sortCheck.isSortByNumCondition(sortCondtion)){
			sortNumber = sortNum.sortNumData(numberData);
		}else{
			sortNumber = sortOrder.sortData(numberData);
		}

		return sortNumber;
	}

	@Override
	public String sortSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		String[] args = toSort.split(System.lineSeparator(), 2);
		String fileName= args[1];
		String data = fileName;
		if(sortCheck.isFile(fileName)){
			data = sortRead.readFromFile(fileName);
		}
		String specialCharData = getSpecialCharData(data);
		String sortSpecialChar = sortOrder.sortData(specialCharData);

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
	
	/**
	 * @param data contains the line of data separated by "System.lineSeparator"
	 * @return String data which starts with a special character
	 */
	private String getSpecialCharData(String data){
		String [] dataAarr = data.split(System.lineSeparator());
		String specialCharData = "";

		for(String line : dataAarr){
			char[] charArr = line.toCharArray();
			if(sortCheck.isSpecialChar(charArr[0])){
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
