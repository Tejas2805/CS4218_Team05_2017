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
	
	private static final int DEFAULT_LINE_COUNT = 10;
	
	private static final String NEWLINE = System.getProperty("line.separator");
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if(checkNullInput(args,stdin,stdout)){
			throw new HeadException(NULL_ALL);
		}
		if(args.length==0){
			for (int i = 0; i < DEFAULT_LINE_COUNT; i++) {
				try {
					int intCount;
					while ((intCount = stdin.read()) != -1) {
						stdout.write(intCount);
					}

				} catch (IOException io) {
					throw new HeadException(io.getMessage());
				}
			}
		
		// TODO Auto-generated method stub
		
	}
		if(args.length==1){
			try {
				Path path = Paths.get(args[0]);
				BufferedReader br = Files.newBufferedReader(path);
				String line;
				for (int i = 0; i < DEFAULT_LINE_COUNT; i++) {
					if ((line = br.readLine()) == null) {
						break;
					}
					stdout.write(line.getBytes());
					stdout.write(NEWLINE.getBytes());
				}
				
			} catch (IOException ioe) {
				throw new HeadException(ioe.getMessage());
		}
		}
		if(args.length==2){
			try {
				int lineCount = Integer.parseInt(args[1]);
			for (int i = 0; i < lineCount ; i++) {
				
					
					int intCount;
					while ((intCount = stdin.read()) != -1) {
						stdout.write(intCount);
					}
			}} catch (IOException io) {
					throw new HeadException(io.getMessage());
				}
			}
			
		if(args.length==3){
			try{
				int lineCount = Integer.parseInt(args[1]);
				if(lineCount<=0){
					throw new HeadException("Invalid Line Count");
				}
				Path path = Paths.get(args[2]);
				BufferedReader br = Files.newBufferedReader(path);
				String line;
				for (int i = 0; i < lineCount; i++) {
					if ((line = br.readLine()) == null) {
						break;
					}
					stdout.write(line.getBytes());
					stdout.write(NEWLINE.getBytes());
				}
			}catch (IOException ioe) {
				throw new HeadException(ioe.getMessage());
		}
		}else{
			throw new HeadException("Invalid Command Format\nUsage: head [-n lines] [file]");
		}
		
	}
			

	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args==null) && (stdin==null) && (stdout==null);
	}

}
