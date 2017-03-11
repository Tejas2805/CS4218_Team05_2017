package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.cert.PKIXRevocationChecker.Option;

import javax.sound.sampled.Line;

import org.omg.CosNaming._BindingIteratorImplBase;

import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortCheck;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcCheckRead;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcOption;

public class WcApplication implements Wc {

	WcCheckRead wcCheckRead = new WcCheckRead();
	WcOption wcOption = new WcOption();
	int numOfBlankSpace = 0;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws WcException {
		String fileName = "", results = "";
		if(args.length == 0){
			if(stdin == null){
				throw new WcException("No args or inputstream");
			}
			String readStdin = wcCheckRead.readFromFileOrInputStream("", stdin); //wcRead.readInputStream(stdin);
			results = printCountInFileOrStdin("", readStdin) + System.lineSeparator();
		}else if(args.length == 1){
			fileName = args[0];
			wcCheckRead.checkValidFile(fileName);
			String data = wcCheckRead.readFromFileOrInputStream(fileName, null); //wcRead.readFromFile(fileName);
			results = printCountInFileOrStdin("all", data) + " " + fileName + System.lineSeparator();
		}else if(args.length >=2 ){
			
			String option = wcOption.processArgsOption(args);
			String data = "", totalData = "";
			int countFile = 0;
			
			//for(int i=index; i<args.length; i++){
			for(int i=0; i<args.length; i++){
				fileName = args[i];
				
				if(wcOption.isValidOption(fileName)){
					continue;
				}
				
				if(wcCheckRead.checkValidFile(fileName)){
					//System.out.println("ddd: " + fileName);
					data = wcCheckRead.readFromFileOrInputStream(fileName, null);//wcRead.readFromFile(fileName);
					results += printCountInFileOrStdin(option, data) + " " + fileName + System.lineSeparator();
					totalData += " " + wcCheckRead.readFromFileOrInputStream(fileName, null);
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
		//System.out.println(option);
		switch(option){
		case "-m":
			return printCharacterCountInFile(data);
		case "-w":
			return printWordCountInFile(data);
		case "-l":
			return printNewlineCountInFile(data);
		case "-lw":
			return printNewlineCountInFile(data) + " " + printWordCountInFile(data);
		case "-lm":
			return printNewlineCountInFile(data) + " " + printCharacterCountInFile(data);
		case "-wm":
			return printWordCountInFile(data) + " " + printCharacterCountInFile(data);
		case "-lwm":
			return printAllCountsInFile(data);
		default:
			return printAllCountsInFile(data);
		}
	}
	
}
