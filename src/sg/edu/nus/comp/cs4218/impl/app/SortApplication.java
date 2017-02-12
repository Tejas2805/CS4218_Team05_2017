package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import javax.security.auth.kerberos.KerberosKey;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.omg.CORBA.ObjectHolder;

import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.exception.SortException;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SortApplication implements Sort{

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
			
			if(numParam.equals("-n")){
				isSortByNum = true;
			}else{
				throw new SortException("Invalid -n parameters");
			}
			
		}else{
			throw new SortException("Invalid parameters");
		}
		
		
		
		
		try {
			if(isSortByNum == true){
				//System.out.println("sort by num");
				fileList = sortByAsciiNum(fileName);
			}
			if(isSortByNum == false){
				fileList = sortByAscii(fileName);
			}
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new SortException("Invalid file");
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
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		ArrayList<String> fileList = new ArrayList<String>();
		String newLine;
		boolean isNotFirstLine = false;
		
		while ((newLine = br.readLine()) != null) {
			if(isNotFirstLine == true){
				
				byte[] asciiNewLine = newLine.getBytes(StandardCharsets.US_ASCII); 
				int lenAsciiNewLine = asciiNewLine.length;
				int insertIndex = 0;
				boolean isSmaller = false;
				boolean isGreater = false;
				
				//Puting in file list
				for(int i=0; i<fileList.size(); i++){
					String line = fileList.get(i);
					byte[] asciiLine = line.getBytes(StandardCharsets.US_ASCII); 
					int lenAsciiLine = asciiLine.length;
					
					//System.out.println(i + " " + line);
					
					isSmaller = false;
					isGreater = false;
					
					//System.out.println(i+ " pikachu");
					//Compare char of 2 string
					for(int j =0; j<Math.min(lenAsciiLine, lenAsciiNewLine); j++){
					
						if(isSmaller == true){
							break;
						}
						
						if(isGreater == true){
							break;
						}
						
						//System.out.println(j + " " + asciiLine[j]);
						//System.out.println(asciiNewLine[j] + " : " + asciiLine[j]);
						int asciiNewLineValue =  asciiNumAlpha(asciiNewLine[j]);
						int asciiLineValue =   asciiNumAlpha(asciiLine[j]);
						
						
						
						
						if(asciiNewLineValue <asciiLineValue){
							//new value smaller
							insertIndex = i;
							isSmaller = true;
						}
						if(asciiNewLineValue > asciiLineValue){
							//new value bigger
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
					
					//System.out.println("Boolean: " + isGreater + " " + isSmaller + " " + isEqual);
					
					
					if(isSmaller == true){
						break;
					}
					
					
				}
				fileList.add(insertIndex, newLine);
				/*
				System.out.println("old file list: " + fileList);
				System.out.println(insertIndex + " : " + newLine);
				fileList.add(insertIndex, newLine);
				System.out.println("new file list: " + fileList);
				System.out.println("--------------------------------------");
				*/
			
			}else{
				fileList.add(newLine);
				isNotFirstLine = true;	
			}
			
			//System.out.println(line);
		}
		
		//System.out.println(fileList);
		br.close();
		return fileList;
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
		
		if(asciiValue >= 48 && asciiValue <= 59){
			return false;
		}
		if(asciiValue >= 65 && asciiValue <= 90){
			return false;
		}
		if(asciiValue >= 97 && asciiValue <= 122){
			return false;
		}
		
		return true;
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
			byte[] asciiNewLine = alphaLine.getBytes(StandardCharsets.US_ASCII); 
			if(isSpecialChar(asciiNewLine[0]) == true){
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
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<String> numFileList = new ArrayList<String>();
		String newLine;
		boolean isNotFirstLine = false;
		
		while ((newLine = br.readLine()) != null) {
			//System.out.println(isFirstWordNum(newLine));
			
			if(isFirstWordNum(newLine) == true){
				numFileList.add(newLine);
				continue;
			}
			
			if(isNotFirstLine == true){
				
				byte[] asciiNewLine = newLine.getBytes(StandardCharsets.US_ASCII); 
				int lenAsciiNewLine = asciiNewLine.length;
				int insertIndex = 0;
				boolean isSmaller = false;
				boolean isGreater = false;
				
				
				
				
				//Puting in file list
				for(int i=0; i<fileList.size(); i++){
					String line = fileList.get(i);
					byte[] asciiLine = line.getBytes(StandardCharsets.US_ASCII); 
					int lenAsciiLine = asciiLine.length;
					
					//System.out.println(i + " " + line);
					
					isSmaller = false;
					isGreater = false;
					
					//System.out.println(i+ " pikachu");
					//Compare char of 2 string
					for(int j =0; j<Math.min(lenAsciiLine, lenAsciiNewLine); j++){
					
						if(isSmaller == true){
							break;
						}
						
						if(isGreater == true){
							break;
						}
						
						//System.out.println(j + " " + asciiLine[j]);
						//System.out.println(asciiNewLine[j] + " : " + asciiLine[j]);
						int asciiNewLineValue =  asciiNumAlpha(asciiNewLine[j]);
						int asciiLineValue =   asciiNumAlpha(asciiLine[j]);
						
						
						
						
						if(asciiNewLineValue <asciiLineValue){
							//new value smaller
							insertIndex = i;
							isSmaller = true;
						}
						if(asciiNewLineValue > asciiLineValue){
							//new value bigger
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
					
					//System.out.println("Boolean: " + isGreater + " " + isSmaller + " " + isEqual);
					
					
					if(isSmaller == true){
						break;
					}
					
					
				}
				fileList.add(insertIndex, newLine);
				
				//System.out.println("old file list: " + fileList);
				//System.out.println(insertIndex + " : " + newLine);
				//fileList.add(insertIndex, newLine);
				//System.out.println("new file list: " + fileList);
				//System.out.println("--------------------------------------");
				
			
			}else{
				if(isFirstWordNum(newLine) == false){
					fileList.add(newLine);
					isNotFirstLine = true;	
				}else{
					numFileList.add(newLine);
				}
			}
			
			
			//System.out.println(line);
		}
		
		//System.out.println(fileList);
		
		numFileList = sortNumList(numFileList);
		
		//System.out.println(fileList);
		//System.out.println(numFileList);
		
		fileList = mergeAlphaNumList(fileList, numFileList);
		//System.out.println(fileList);
		br.close();
		return fileList;
	}
	
	/**
	 * This methods sorts the list in numerical order
	 * @param numFileList list containing all the lines which first word is a number
	 * @return ArrayList<String> list sorted in numerical order
	 */
	private ArrayList<String> sortNumList(ArrayList<String> numFileList){
		//ArrayList<String> sortedNumList = new ArrayList<String>();
		
		String oldLine = "";
		String newLine = "";
	
		//System.out.println("Start num list: " +  numFileList);
		
		int N= numFileList.size();
		
		for(int i=0; i < N; i++){
			boolean flag = true;
			
			for(int j=0; j < (N - i -1 ); j++){
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
					
					
					//compare second word
					int indexOldLine= oldLine.trim().indexOf(" ");
					int indexNewLine = newLine.trim().indexOf(" ");
					
				
					
					if(indexOldLine == -1){
						numFileList.set(j, oldLine);
						numFileList.set(j+1, newLine);
					}else if(indexNewLine == -1){
						numFileList.set(j, newLine);
						numFileList.set(j+1, oldLine);
					}else{
						
						String noFirstOldLine = oldLine.substring(indexOldLine+1);
						String noFirstNewLine = newLine.substring(indexNewLine+1);
						
						byte[] asciiOldLine = noFirstOldLine.getBytes(StandardCharsets.US_ASCII); 
						byte[] asciiNewLine = noFirstNewLine.getBytes(StandardCharsets.US_ASCII); 
						
						int shorterLineLen = Math.min(noFirstOldLine.length(), noFirstNewLine.length());
						
						for(int k=0; k < shorterLineLen; k++){
						
							int asciiOldLineValue =  asciiNumAlpha(asciiOldLine[k]);
							int asciiNewLineValue =  asciiNumAlpha(asciiNewLine[k]);
							
							
							if(asciiOldLineValue < asciiNewLineValue){
								numFileList.set(j, oldLine);
								numFileList.set(j+1, newLine);
								break;
							}
							if(asciiOldLineValue > asciiNewLineValue){
								numFileList.set(j+1, oldLine);
								numFileList.set(j, newLine);
								break;
							}
						}
					}
					
					
				}
				
				
			
			}
		
			if (flag == true) {
				break;
			}
			
			
		}
		
		//System.out.println("Sorted num list: " +  numFileList);
		
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
