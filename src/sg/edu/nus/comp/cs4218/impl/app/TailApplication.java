package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;


public class TailApplication implements Application{

	private static final String NULL_ALL = "args, stdin, stdout are null";
	
	private static final int DEFAULT_LINE_COUNT = 10;
	
	private static final String NEWLINE = System.getProperty("line.separator");
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		if(checkNullInput(args,stdin,stdout)){
			throw new TailException(NULL_ALL);
		}// TODO Auto-generated method stub
		if(args.length==0){
			try{
				for(int i=0;i< DEFAULT_LINE_COUNT;i++){
					int outputMsg;
					while((outputMsg = stdin.read()) != -1){
						stdout.write(outputMsg);
					}
				}
				
			}catch (IOException io) {
				throw new TailException(io.getMessage());
			}catch (Exception e){
				throw new TailException(e.getMessage());
		}
		
	}
		if(args.length==1){
			try (RandomAccessFile file = new RandomAccessFile(args[0], "r")) {
				long length = file.length();
				
				long position = length - (DEFAULT_LINE_COUNT * 3);
				if (position < 0) {
					position = 0;
				}

				file.seek(position);

				String line;
				while ((line = file.readLine()) != null) {
					stdout.write(line.getBytes());
					stdout.write(NEWLINE.getBytes());
				}

			}catch (IOException io) {
				throw new TailException(io.getMessage());
			}catch (Exception e){
				throw new TailException(e.getMessage());
		}
		}
		if(args.length==2){
			try{
				int lineCount = Integer.parseInt(args[1]);
				if(lineCount<=0){
					throw new TailException("Invalid Line Count");
				}
				for(int i=0;i< lineCount;i++){
					int outputMsg;
					while((outputMsg = stdin.read()) != -1){
						stdout.write(outputMsg);
					}
				}
			}catch (IOException io) {
				throw new TailException(io.getMessage());
			}catch (Exception e){
			
		}
		}
		if(args.length==3){
			try (RandomAccessFile file = new RandomAccessFile(args[0], "r")) {
				int lineCount = Integer.parseInt(args[1]);
				if(lineCount<=0){
					throw new TailException("Invalid Line Count");
				}
				long length = file.length();
				
				long position = length - (lineCount * 3);
				if (position < 0) {
					position = 0;
				}

				file.seek(position);

				String line;
				while ((line = file.readLine()) != null) {
					stdout.write(line.getBytes());
					stdout.write(NEWLINE.getBytes());
				}

			}catch (IOException io) {
				throw new TailException(io.getMessage());
			}catch (Exception e){
				throw new TailException(e.getMessage());
		}
		}
	}
	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args==null) && (stdin==null) && (stdout==null);
	}
}
