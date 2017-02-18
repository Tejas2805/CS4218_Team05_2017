package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplication implements Application {

	private static final String NULL_ALL = "args, stdin, stdout are null";
	
	private static final int DEFAULT_LINES = 10;
	
	private static final String NEWLINE = System.getProperty("line.separator");
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if(checkNullInput(args,stdin,stdout)){
			throw new HeadException(NULL_ALL);
		}
		checkArgumentLengthAndRun(args, stdin, stdout);
	}


	private void checkArgumentLengthAndRun(String[] args, InputStream stdin, OutputStream stdout) throws HeadException {
		if(args.length==0){
			readFromStdin(stdin, stdout);
			}else if(args.length==1){
			try {
				readFromPath(args, stdout);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				//throw new HeadException(ioe.getMessage());
		}
		}else if(args.length==2){
			try {
				readWithLines(args, stdin, stdout);} catch (IOException io) {
					io.printStackTrace();
					//throw new HeadException(io.getMessage());
				}
			}else if(args.length==3){
			try{
				readWithLinesAndDirectory(args, stdout);
			}catch (IOException ioe) {
				ioe.printStackTrace();
				//throw new HeadException(ioe.getMessage());
		}
		}else{
			throw new HeadException("Invalid Command Format\nUsage: head [-n lines] [file]");
		}
	}


	private void readWithLinesAndDirectory(String[] args, OutputStream stdout) throws HeadException, IOException {
		int lineCount = Integer.parseInt(args[1]);
		if(lineCount<=0){
			throw new HeadException("Invalid Line Count");
		}
		
		Path path = Paths.get(args[2]);
		if(Files.notExists(path)){
			throw new HeadException("File Not Exists");
		}
		BufferedReader buffReader = Files.newBufferedReader(path);
		String line;
		for (int i = 0; i < lineCount; i++) {
			if ((line = buffReader.readLine()) == null) {
				break;
			}
			stdout.write(line.getBytes());
			stdout.write(NEWLINE.getBytes());
		}
	}


	private void readWithLines(String[] args, InputStream stdin, OutputStream stdout) throws IOException {
		int lineCount = Integer.parseInt(args[1]);
for (int i = 0; i < lineCount ; i++) {
		
			
			int intCount;
			while ((intCount = stdin.read()) != -1) {
				stdout.write(intCount);
			}
}
	}


	private void readFromPath(String[] args, OutputStream stdout) throws HeadException, IOException {
		Path path = Paths.get(args[0]);
		if(Files.notExists(path)){
			throw new HeadException("File Not Exists");
		}
		BufferedReader buffReader = Files.newBufferedReader(path);
		String line;
		for (int i = 0; i < DEFAULT_LINES; i++) {
			if ((line = buffReader.readLine()) == null) {
				break;
			}
			stdout.write(line.getBytes());
			stdout.write(NEWLINE.getBytes());
		}
	}


	private void readFromStdin(InputStream stdin, OutputStream stdout) {
		for (int i = 0; i < DEFAULT_LINES; i++) {
			try {
				int intCount;
				while ((intCount = stdin.read()) != -1) {
					stdout.write(intCount);
				}

			} catch (IOException io) {
				io.printStackTrace();
				//throw new HeadException(io.getMessage());
			}
		}
	}
			

	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args==null) && (stdin==null) && (stdout==null);
	}

}
