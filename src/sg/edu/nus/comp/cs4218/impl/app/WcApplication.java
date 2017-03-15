package sg.edu.nus.comp.cs4218.impl.app;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcCheckRead;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcOption;

public class WcApplication implements Wc {

	WcCheckRead wcCheckRead = new WcCheckRead();
	WcOption wcOption = new WcOption();
	int numOfBlankSpace = 0;
	
	/**
	 * This method execute the wc function and write the data to output stream
	 * FILE is read and converted to string 
	 * InputStream are also converted to string before being process
	 * Error message will be printed out if FILE or OPTION are invalid 
	 * However, if the remaining FILE or OPTION are valid the command will still be executed
	 * The position of FILE and OPTION does not have to be in sequence
	 * @param args contains an array of arguments such as FILE or a combination of OPTION -l, -w, -m, -lwm, -ww, -mmw
	 * @param stdin input stream of data
	 * @param stdout data is written to the output stream 
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws WcException {
		String fileName = "", results = "";
	
		if(stdout == null){
			throw new WcException("No args or outputstream");
		}
		if(args == null || args.length == 0){
			if(stdin == null || stdout == null){
				throw new WcException("No args or inputstream");
			}
			String readStdin = wcCheckRead.readFileStdin("", stdin); 
			results = printCountInFileOrStdin("", readStdin) + System.lineSeparator();
		}else if(args.length == 1){
			fileName = args[0];
			wcCheckRead.checkValidFile(fileName);
			String data = wcCheckRead.readFileStdin(fileName, null);
			results = printCountInFileOrStdin("-lwm", data) + " " + fileName + System.lineSeparator();
		}else if(args.length >=2 ){
			
			String option = wcOption.processArgsOption(args);
			String data = "", totalData = "";
			int countFile = 0;
			
			
			for(int i=0; i<args.length; i++){
				fileName = args[i];
				
				if(wcOption.isValidOption(fileName)){
					continue;
				}		
				
				if(wcCheckRead.checkValidFile(fileName)){
					data = wcCheckRead.readFileStdin(fileName, null);
					results += printCountInFileOrStdin(option, data) + " " + fileName + System.lineSeparator();
					totalData += " " + wcCheckRead.readFileStdin(fileName, null);
					countFile += 1;
				}
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

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * The String args does not contains "-wc" or "-l" or "-wc -l" in front.
	 * Only the data that is converted to string
	 * @return String char count of a file/inputstream
	 */
	@Override
	public String printCharacterCountInFile(String args) {
		// TODO Auto-generated method stub
		String data = args;
		
		byte[] bytes = data.getBytes();
		int numByte = bytes.length - numOfBlankSpace;
		String strByte = String.valueOf(numByte);
		
		return "   " + strByte;
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String word count of a file/inputstream
	 */
	@Override
	public String printWordCountInFile(String args) {
		// TODO Auto-generated method stub
		String data = args;
		
		String[] wordArr;
		int  numWord  = 0;
		if(data.length() > 0){
			wordArr = data.split("\\s+");
			numWord = wordArr.length;
		}
		
		String strNumWord = String.valueOf(numWord);
		return "   " + strNumWord;
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String newline count of a file/inputstream
	 */
	@Override
	public String printNewlineCountInFile(String args) {
		// TODO Auto-generated method stub
		String data = args;
	
	
		boolean hasEndline = data.endsWith(System.lineSeparator());
	
		String[] lineArr = data.split(System.lineSeparator());
		int  numLine = lineArr.length;
		if(!hasEndline){
			numLine -= 1;
		}
		String strNumLine = String.valueOf(numLine);
		
		return "   " + strNumLine;
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String newline, word, char count of a file/inputstream
	 */
	@Override
	public String printAllCountsInFile(String args) {
		// TODO Auto-generated method stub
		String lineCount = printNewlineCountInFile(args);
		String wordCount= printWordCountInFile(args);
		String charCount = printCharacterCountInFile(args);
		String results = lineCount + wordCount + charCount;
		
		return results;
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String char count of a file/inputstream
	 */
	@Override
	public String printCharacterCountInStdin(String args) {
		// TODO Auto-generated method stub
		return printCharacterCountInFile(args);
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String word count of a file/inputstream
	 */
	@Override
	public String printWordCountInStdin(String args) {
		// TODO Auto-generated method stub
		return printWordCountInFile(args);
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String newline count of a file/inputstream
	 */
	@Override
	public String printNewlineCountInStdin(String args) {
		// TODO Auto-generated method stub
		return printNewlineCountInFile(args);
	}

	/*
	 * @param args InputStream and lines from FILE that have been converted to String
	 * @return String line, word, char count of a file/inputstream
	 */
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
			return printNewlineCountInFile(data) + printWordCountInFile(data);
		case "-lm":
			return printNewlineCountInFile(data) + printCharacterCountInFile(data);
		case "-wm":
			return printWordCountInFile(data) +  printCharacterCountInFile(data);
		case "-lwm":
			return printAllCountsInFile(data);
		default:
			return printAllCountsInFile(data);
		}
	}
	
}
