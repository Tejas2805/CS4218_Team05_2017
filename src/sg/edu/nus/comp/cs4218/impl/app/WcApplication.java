package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.sound.sampled.Line;

import org.omg.CosNaming._BindingIteratorImplBase;

import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortCheck;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class WcApplication implements Wc {

	SortRead wcRead = new SortRead();
	SortCheck wcCheck = new SortCheck();
	int numOfBlankSpace = 0;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws WcException {
		String fileName = "", results = "";
		if(args.length == 0){
			if(stdin == null){
				throw new WcException("No args or inputstream");
			}
			String readStdin = readFromFileOrInputStream("", stdin); //wcRead.readInputStream(stdin);
			results = printCountInFileOrStdin("", readStdin) + System.lineSeparator();
		}else if(args.length == 1){
			fileName = args[0];
			checkValidFile(fileName);
			String data = readFromFileOrInputStream(fileName, null); //wcRead.readFromFile(fileName);
			results = printCountInFileOrStdin("all", data) + " " + fileName + System.lineSeparator();
		}else if(args.length >=2 ){
			int index = 1;
			String option = "all";
			if("-m".equals(args[0])){
				option = "char";
			}else if("-w".equals(args[0])){
				option = "word";
			}else if("-l".equals(args[0])){
				option = "newline";
			}else{
				index = 0;
			}
			String data = "", totalData = "";
			int countFile = 0;
			for(int i=index; i<args.length; i++){
				fileName = args[i];
				if(checkValidFile(fileName)){
					//System.out.println("ddd: " + fileName);
					data = readFromFileOrInputStream(fileName, null);//wcRead.readFromFile(fileName);
					results += printCountInFileOrStdin(option, data) + " " + fileName + System.lineSeparator();
					totalData += " " + readFromFileOrInputStream(fileName, null);
					countFile += 1;
				}
				//System.out.println(totalData);
			}
			if(countFile > 1){
				numOfBlankSpace = countFile-1;
				results += printCountInFileOrStdin(option, totalData.substring(1)) + " total" + System.lineSeparator();
			}
		}
		try {
			stdout.write(results.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String printCharacterCountInFile(String args) {
		// TODO Auto-generated method stub
		//String[] dataArr = args.split(System.lineSeparator(), 2);
		//String filename = dataArr[0];
		String data = args;//dataArr[1];
		
		byte[] bytes = data.getBytes();
		int numByte = bytes.length - numOfBlankSpace;
		String strByte = String.valueOf(numByte);
		
		return strByte;
	}

	@Override
	public String printWordCountInFile(String args) {
		// TODO Auto-generated method stub
		//String[] dataArr = args.split(System.lineSeparator(), 2);
		//String filename = dataArr[0];
		String data = args;//dataArr[1];
		
		String[] wordArr = data.split("\\W+");
		int  numWord = wordArr.length;
		String strNumWord = String.valueOf(numWord);
		//System.out.println(wordArr.length + " " + filename);
		return strNumWord;
	}

	@Override
	public String printNewlineCountInFile(String args) {
		// TODO Auto-generated method stub
		//String[] dataArr = args.split(System.lineSeparator(), 2);
		//String filename = dataArr[0];
		String data = args;//dataArr[1];
	
	
		boolean hasEndline = data.endsWith(System.lineSeparator());
	
		String[] lineArr = data.split(System.lineSeparator());
		int  numLine = lineArr.length;
		if(!hasEndline){
			numLine -= 1;
		}
		String strNumLine = String.valueOf(numLine);
		
		return strNumLine;
	}

	@Override
	public String printAllCountsInFile(String args) {
		// TODO Auto-generated method stub
		String lineCount = printNewlineCountInFile(args);
		String wordCount= printWordCountInFile(args);
		String charCount = printCharacterCountInFile(args);
		String results = lineCount + " " + wordCount + " " + charCount;
		
		return results;
	}

	@Override
	public String printCharacterCountInStdin(String args) {
		// TODO Auto-generated method stub
		return printCharacterCountInFile(args);
	}

	@Override
	public String printWordCountInStdin(String args) {
		// TODO Auto-generated method stub
		return printWordCountInFile(args);
	}

	@Override
	public String printNewlineCountInStdin(String args) {
		// TODO Auto-generated method stub
		return printNewlineCountInFile(args);
	}

	@Override
	public String printAllCountsInStdin(String args) {
		// TODO Auto-generated method stub
		return printAllCountsInFile(args);
	}
	
	/*
	 * Print the count value of either "char", "word" or "newline" or all the "char", "word" & "newline"
	 * @param option "char", "word", "newline", "all"
	 * @param data the string of the text file or inputstream
	 * return the results of the count
	 */
	private String printCountInFileOrStdin(String option, String data){
		switch(option){
		case "char":
			return printCharacterCountInFile(data);
		case "word":
			return printWordCountInFile(data);
		case "newline":
			return printNewlineCountInFile(data);
		case "all":
			return printAllCountsInFile(data);
		default:
			return printAllCountsInFile(data);
		}
	}
	/*
	 * Read from either a file or inputstream and convert them to String
	 * @param fileName name of the text file
	 * @param stdin the inputstream data
	 * return the data in String format 
	 */
	private String readFromFileOrInputStream(String fileName, InputStream stdin){
		InputStream inputStream = null; 
		InputStreamReader inputStreamReader= null;
		BufferedReader bufRead = null;
		String results = "";
		try {
			if(fileName.isEmpty()){
				inputStream = stdin;
			}else{
				inputStream = new FileInputStream(fileName);
			}
			inputStreamReader = new InputStreamReader(inputStream);
			bufRead = new BufferedReader(inputStreamReader);
		
			int value = 0;
			
			while((value = bufRead.read()) != -1){
				char charVal = (char)value;
				results += String.valueOf(charVal);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		     
		     // releases resources associated with the streams
		     if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		     if(inputStreamReader!=null){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		     if(bufRead!=null){
				try {
					bufRead.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
	     }
		return results;
	}
	
	/**
	 * Determine if the file is valid. Throw exception if the file is not valid
	 * @param fileName name of the file
	 */
	public boolean checkValidFile(String fileName){
		BufferedReader bufReader = null;
		boolean isValid = false;
		try {

			bufReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufReader.readLine()) != null) {
			}
			isValid = true;
		} catch (IOException e) {
			System.out.println("No such file/directory: " + fileName); //e.printStackTrace();
			isValid = false;
			return isValid;
			//throw (WcException) new WcException("error reading file").initCause(e);
		} finally {
			try {
				if (bufReader != null){
					bufReader.close();
				}
				isValid = true;
			} catch (IOException ex) {
				ex.printStackTrace();
				isValid = false;
			
				//throw (SortException) new SortException("error closing buffer reader").initCause(ex);
			}
		}
		return isValid;
	}
}
