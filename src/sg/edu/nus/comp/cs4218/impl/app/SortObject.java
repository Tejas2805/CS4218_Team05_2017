package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class SortObject {
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
		ArrayList<String> numDataList = new ArrayList<String>();

		for(String line : dataArr){
			if(isFirstWordNum(line)){
				numDataList.add(line);
			}else{
				dataList.add(line);
			}
		}
		SortNumbers sortNum = new SortNumbers(); 
		ArrayList<String> numList = sortNum.sortByNum(numDataList);
		ArrayList<String> asciiList = sortByAscii(dataList);
		numList.addAll(asciiList);
		return arrayListToString(numList);
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
	 * Determine if the first word of each line is a number
	 * @param line the input line
	 * @return boolean true if first word is a number, false if not
	 */
	private boolean isFirstWordNum(String line){
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

	

}
