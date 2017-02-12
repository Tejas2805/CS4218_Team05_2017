package sg.edu.nus.comp.cs4218.impl.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.core.IsEqual;
import org.omg.CORBA.ObjectHolder;

import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SortApplication implements Sort{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SortException {
		// TODO Auto-generated method stub
		/*
		System.out.println("sorting");
		for(int i=0; i<args.length; i++){
			System.out.println(i + " " + args[i]);
		}
		
		
		byte[] ascii = "Java".getBytes(StandardCharsets.US_ASCII); 
		String asciiString = Arrays.toString(ascii); 
		System.out.println(asciiString); // print [74, 97, 118, 97]
		
		ArrayList<String> al = new ArrayList<String>();
		System.out.println("Initial size of al: " + al.size());
		
		// add elements to the array list
		al.add("C");
		al.add("A");
		al.add("E");
		al.add("B");
		al.add("D");
		al.add("F");
		al.add(1, "A2");
		System.out.println("Size of al after additions: " + al.size());
		
		// display the array list
		System.out.println("Contents of al: " + al);
		
		for(int i=0; i< al.size(); i++){
			System.out.println(al.get(i));
		}
		*/
		
		ArrayList<String> fileList = new ArrayList<String>();
		String FILENAME = "sort.txt";
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

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
						
						System.out.println(i+ " pikachu");
						//Compare char of 2 string
						for(int j =0; j<Math.min(lenAsciiLine, lenAsciiNewLine); j++){
						
							if(isSmaller == true){
								break;
							}
							
							if(isGreater == true){
								break;
							}
							
							//System.out.println(j + " " + asciiLine[j]);
							System.out.println(asciiNewLine[j] + " : " + asciiLine[j]);
							
							if(asciiNewLine[j] < asciiLine[j]){
								//new value smaller
								insertIndex = i;
								isSmaller = true;
							}
							if(asciiNewLine[j] > asciiLine[j]){
								//new value bigger
								insertIndex = i+1;
								isGreater = true;
							}
							if(asciiNewLine[j] == asciiLine[j]){
								if( j+1 == lenAsciiLine){
									insertIndex = i+1;
								}
								if( j+1 == lenAsciiNewLine){
									insertIndex = i;
								}					
							}
						}
						
						if(isSmaller == true){
							break;
						}
						
						
					}
					System.out.println("old file list: " + fileList);
					System.out.println(insertIndex + " : " + newLine);
					fileList.add(insertIndex, newLine);
					System.out.println("new file list: " + fileList);
					System.out.println("--------------------------------------");
				
				}else{
					fileList.add(newLine);
					isNotFirstLine = true;	
				}
				
				//System.out.println(line);
			}
			
			System.out.println(fileList);

		} catch (IOException e) {
			e.printStackTrace();
		}
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
