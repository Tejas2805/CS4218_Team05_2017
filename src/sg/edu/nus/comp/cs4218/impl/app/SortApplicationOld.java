package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;



public class SortApplicationOld implements Sort{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException {
		// TODO Auto-generated method stub
		
		String fileName = "t";
		boolean isSortByNum = false;
		ArrayList<String> fileList = new ArrayList<String>();
		
		if(args.length == 1){
			fileName = args[0];
			isSortByNum = false;
		}
		else if(args.length == 2){
			String numParam = args[0];
			fileName = args[1];
			
			if("-n".equals(numParam)){
				isSortByNum = true;
			}else{
				throw new SortException("Invalid -n parameters");
			}
			
		}else{
			throw new SortException("Invalid parameters");
		}
		
		try {
			if(isSortByNum){
				//System.out.println("sort by num");
				fileList = sortByAsciiNum(fileName);
			}
			if(!isSortByNum){
				fileList = sortByAscii(fileName);
			}
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new SortException("Invalid file");
		}
		
		for(int i=0; i<fileList.size(); i++){
			System.out.println(fileList.get(i));
		}
		
	
	}
	
	/**
	 * This methods increases the ASCII value of alphabet & number 
	 * @param asciiValue ASCII value of Alphabet or Number
	 * @return int new ASCII value
	 */
	private int asciiNumAlpha(int asciiValue){
		
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
	 * This methods sorts and return a list of sentences sorted in ascending ASCII value
	 * @param fileName file name of the file
	 * @exception IOException On read file error
	 * @return ArrayList<String> list sorted in ascending ASCII value
	 */
	private ArrayList<String> sortByAscii(String fileName) throws IOException{
		BufferedReader bufRead = new BufferedReader(new FileReader(fileName));
		
		ArrayList<String> fileList = new ArrayList<String>();
		String newLine;
		boolean isNotFirstLine = false;
		
		while ((newLine = bufRead.readLine()) != null) {
			if(isNotFirstLine){
				byte[] asciiNewLine = newLine.getBytes();//StandardCharsets.US_ASCII); 
				fileList = sortAsciiFileList(fileList, asciiNewLine, newLine);
			}else{
				fileList.add(newLine);
				isNotFirstLine = true;	
			}
		}
		bufRead.close();
		return fileList;
	}
	
	/**
	 * This methods sorts and return a list of sentences sorted in the order of special char, number, capital & small letter
	 * @param fileList contains all the line
	 * @param asciiiNewLine the ascii value array of the new  line
	 * @param newLine the new line to be sorted 
	 * @return ArrayList<String> list of sentences sorted in the order of special char, number, capital & small letter
	 */
	private ArrayList<String> sortAsciiFileList(ArrayList<String> fileList, byte[] asciiNewLine, String newLine){
		ArrayList<String> newFileList = fileList;
		int insertIndex = 0;
		boolean isSmaller = false;
		boolean isGreater = false;
		int lenAsciiNewLine = asciiNewLine.length;
		
		for(int i=0; i<newFileList.size(); i++){
			String line = newFileList.get(i);
			byte[] asciiLine = line.getBytes();//StandardCharsets.US_ASCII); 
			int lenAsciiLine = asciiLine.length;
			
			isSmaller = false;
			isGreater = false;
			
			for(int j =0; j<Math.min(lenAsciiLine, lenAsciiNewLine); j++){
			
				if(isSmaller || isGreater){
					break;
				}
				int asciiNewLineValue =  asciiNumAlpha(asciiNewLine[j]);
				int asciiLineValue =   asciiNumAlpha(asciiLine[j]);
		
				if(asciiNewLineValue <asciiLineValue){
					insertIndex = i;
					isSmaller = true;
				}
				if(asciiNewLineValue > asciiLineValue){
					insertIndex = i+1;
					isGreater = true;
				}
				if(asciiNewLineValue == asciiLineValue){
					if( j+1 == lenAsciiLine){
						insertIndex = i+1;
						isGreater = true;
					}
					if( j+1 == lenAsciiNewLine){
						insertIndex = i;
						isSmaller = true;
					}				
				}
			}	
			if(isSmaller){
				break;
			}
		}
		newFileList.add(insertIndex, newLine);
		return newFileList;
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
	 * Determine if the ASCII value belongs to a special char
	 * @param asciiValue ASCII value of char
	 * @return boolean true if the ASCII value belongs to a special char, false if not
	 */
	private boolean isSpecialChar(int asciiValue){
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
	 * Determine the alphabetically sorted list and the numerically sorted list in special char, number, capital & small letter
	 * @param alphaList alphabetically sorted list
	 * @param numList numerically sorted list
	 * @return ArrayList<String> the merged list sorted in special char, number, capital & small letter
	 */
	private ArrayList<String> mergeAlphaNumList(ArrayList<String> alphaList, ArrayList<String> numList){
		ArrayList<String> mergeList = new ArrayList<String>();
		int index = 0;
		for(int i=0; i < alphaList.size(); i++){
			String alphaLine = alphaList.get(i);
			byte[] asciiNewLine = alphaLine.getBytes();//StandardCharsets.US_ASCII); 
			if(isSpecialChar(asciiNewLine[0])){
				mergeList.add(alphaLine);
			}else{
				mergeList.addAll(numList);
				index = i;
				break;
			}
		}
		for(int j = index; j < alphaList.size(); j++){
			String alphaLine = alphaList.get(j);
			mergeList.add(alphaLine);
		}
		
		return mergeList;
	}
	
	/**
	 * This methods sorts and return a list of sentences sorted in ascending ASCII value
	 * If the first word of a line is a number they will be sorted in numerical order
	 * @param fileName file name of the file
	 * @exception IOException On read file error
	 * @return ArrayList<String> list sorted in ascending ASCII value
	 */
	private ArrayList<String> sortByAsciiNum(String fileName) throws IOException{
		BufferedReader bufRead = new BufferedReader(new FileReader(fileName));
		
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<String> numFileList = new ArrayList<String>();
		String newLine;
		boolean isNotFirstLine = false;
		
		while ((newLine = bufRead.readLine()) != null) {
			if(isFirstWordNum(newLine)){
				numFileList.add(newLine);
				continue;
			}
			
			if(isNotFirstLine){
				
				byte[] asciiNewLine = newLine.getBytes();//StandardCharsets.US_ASCII); 
				
				fileList = sortAsciiFileList(fileList, asciiNewLine, newLine);
				
			}else{
				if(isFirstWordNum(newLine)){
					numFileList.add(newLine);	
				}else{
					fileList.add(newLine);
					isNotFirstLine = true;
				}
			}
		}	
		numFileList = sortNumList(numFileList);
		fileList = mergeAlphaNumList(fileList, numFileList);
		bufRead.close();
		return fileList;
	}
	
	
	/**
	 * This methods sorts the list in numerical order
	 * @param numFileList list containing all the lines which first word is a number
	 * @return ArrayList<String> list sorted in numerical order
	 */
	private ArrayList<String> sortNumList(ArrayList<String> numList){
		String oldLine = "";
		String newLine = "";
		ArrayList<String> numFileList = numList;
		
		int fileSize = numFileList.size();
		
		for(int i=0; i < fileSize; i++){
			boolean flag = true;
			
			for(int j=0; j < (fileSize - i -1 ); j++){
				
				oldLine = numFileList.get(j);
				newLine = numFileList.get(j+1);
				
				int oldLineValue = Integer.parseInt(oldLine.split(" ")[0]); 
				int newLineValue = Integer.parseInt(newLine.split(" ")[0]); 
				
				if(oldLineValue > newLineValue){
					flag = false;
					numFileList.set(j, newLine);
					numFileList.set(j+1, oldLine);
				}
				
				if(oldLineValue == newLineValue){
					numFileList = sortSameNum(numFileList, oldLine, newLine, j);
				}
			}
			if (flag) {
				break;
			}	
		}
		return numFileList;
	}
	
	private ArrayList<String> sortSameNum(ArrayList<String> numList, String oldLine, String newLine, int index){
		ArrayList<String> numFileList = numList;
		int indexOldLine= oldLine.trim().indexOf(" ");
		int indexNewLine = newLine.trim().indexOf(" ");
		
		if(indexOldLine == -1){
			numFileList.set(index, oldLine);
			numFileList.set(index+1, newLine);
		}else if(indexNewLine == -1){
			numFileList.set(index, newLine);
			numFileList.set(index+1, oldLine);
		}else{
			String noFirstOldLine = oldLine.substring(indexOldLine+1);
			String noFirstNewLine = newLine.substring(indexNewLine+1);
			
			byte[] asciiOldLine = noFirstOldLine.getBytes();//StandardCharsets.US_ASCII); 
			byte[] asciiNewLine = noFirstNewLine.getBytes();//StandardCharsets.US_ASCII); 
			
			int shorterLineLen = Math.min(noFirstOldLine.length(), noFirstNewLine.length());
			
			for(int k=0; k < shorterLineLen; k++){			
				int asciiOldLineValue =  asciiNumAlpha(asciiOldLine[k]);
				int asciiNewLineValue =  asciiNumAlpha(asciiNewLine[k]);
	
				if(asciiOldLineValue < asciiNewLineValue){
					numFileList.set(index, oldLine);
					numFileList.set(index+1, newLine);
					break;
				}
				if(asciiOldLineValue > asciiNewLineValue){
					numFileList.set(index+1, oldLine);
					numFileList.set(index, newLine);
					break;
				}
			}
		}	
		
		return numFileList;
	}

	@Override
	public String sortStringsSimple(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortStringsCapital(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortNumbers(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortAll(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}
}
