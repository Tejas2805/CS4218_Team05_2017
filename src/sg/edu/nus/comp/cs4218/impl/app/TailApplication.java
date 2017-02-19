package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;


public class TailApplication implements Application{

	private static final String NULL_ALL = "args, stdin, stdout are null";
	
	private static final int LINE_COUNT = 10;
	
	private static final String NEWLINE = System.getProperty("line.separator");
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if(checkNullInput(args,stdin,stdout)){
			throw new TailException(NULL_ALL);
		}// TODO Auto-generated method stub
		if(args.length==0){
			readWithNoArgument(stdin, stdout); 
		
	}else if(args.length==1){
			readWithDirectory(args, stdout);
		}else if(args.length==2){
			if(args[0].equals("-n")){
			readWithLineNumber(args, stdin, stdout);
			}else{
				throw new TailException("Invalid Command Format" + NEWLINE +"Usage: tail [-n lines] [file]");
			}
		}else if(args.length==3){
			if(args[0].equals("-n")){
			readWithLinesAndDirectory(args, stdout);
			}else{
				throw new TailException("Invalid Command Format" + NEWLINE +"Usage: tail [-n lines] [file]");
			}
		}else{
			throw new TailException("Invalid Command Format" + NEWLINE +"Usage: tail [-n lines] [file]");
		}
	}
	/**
	 * @param args contains array of data that consists of number of lines and file path"
	 * @param stdout is the OutputStream
	 * This method read the file with path provided and print the number of lines as given in args
	 * @throws TailException 
	 */	
	private void readWithLinesAndDirectory(String[] args, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = Integer.parseInt(args[1]);
			if(lineCount<=0){
				throw new TailException("Invalid Line Count");
			}
			Path path = Paths.get(args[2]);
			if(Files.notExists(path)){
				throw new TailException("File Not Exists");
			}
			BufferedReader buffRead = Files.newBufferedReader(path);
			String line;
			while((line = buffRead.readLine())!=null){
				if(counter<lineCount){
					output.add(line);
					counter++;
				}else{
					output.remove(0);
					output.add(line);
				}
			}
			for (int i = 0; i < counter; i++) {
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			throw (TailException) new TailException(io.getMessage()).initCause(io);
		}catch (Exception e){
			throw (TailException) new TailException(e.getMessage()).initCause(e);
}
	}
	/**
	 * @param args contains array of data that consists of number of lines"
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method read from stdin and print number of lines as provided in args
	 */
	private void readWithLineNumber(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = Integer.parseInt(args[1]);
			if(lineCount<=0){
				throw new TailException("Invalid Line Count");
			}
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(stdin));
			String line = null;
			while((line = buffReader.readLine()) != null){
				if(counter==lineCount){
					output.remove(0);
				}else{
					counter++;
				}output.add(line);
			}
			for(int i=0;i<counter;i++){
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			throw (TailException) new TailException(io.getMessage()).initCause(io);
		}catch (Exception e){
			throw (TailException) new TailException(e.getMessage()).initCause(e);
}
	}
	/**
	 * @param args contains array of data that consists of file path"
	 * @param stdout is the OutputStream
	 * This method read the file from path provided in args and print accordingly
	 */
	private void readWithDirectory(String[] args, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = LINE_COUNT;
			Path path = Paths.get(args[0]);
			if(Files.notExists(path)){
				throw new TailException("File Not Exists");
			}
			BufferedReader buffRead = Files.newBufferedReader(path);
			String line;
			while((line = buffRead.readLine())!=null){
				if(counter<lineCount){
					output.add(line);
					counter++;
				}else{
					output.remove(0);
					output.add(line);
				}
			}
			for (int i = 0; i < counter; i++) {
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			throw (TailException) new TailException(io.getMessage()).initCause(io);
			//throw new TailException("");
		}catch (Exception e){
			throw (TailException) new TailException(e.getMessage()).initCause(e);
}
	}
	/**
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method read from stdin and print the output accordingly
	 */
	private void readWithNoArgument(InputStream stdin, OutputStream stdout) {
		BufferedReader buffRead = null;

		String line;
		ArrayList<String> output = new ArrayList<String>();
		int counter = 0;
		try {
			buffRead = new BufferedReader(new InputStreamReader(stdin));
			while ((line = buffRead.readLine()) != null) {
				if(counter<LINE_COUNT){
					output.add(line);
					counter++;
				}else{
					output.remove(0);
					output.add(line);
					}
			}
			for(int i = 0; i < output.size(); i++){
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}

		} catch (IOException io) {
			new TailException(io.getMessage());
		}
	}

	/**
	 * @param args contains array of data that consists of number of lines and file path"
	 * @param stdin is the InputStream
	 * @param stdout is the OutputStream
	 * This method checks whether args, stdin and stdout are all null
	 */
	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args==null) && (stdin==null) && (stdout==null);
	}

}