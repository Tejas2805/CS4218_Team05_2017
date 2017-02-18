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
			readWithLineNumber(args, stdin, stdout);
		}else if(args.length==3){
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
				io.printStackTrace();
				//throw new TailException(io.getMessage());
			}catch (Exception e){
				e.printStackTrace();
				//throw new TailException(e.getMessage());
		}
		}else{
			throw new TailException("Invalid Command Format\nUsage: tail [-n lines] [file]");
		}
	}
	private void readWithLineNumber(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		try{
			ArrayList<String> output = new ArrayList<String>();
			int counter = 0;
			int lineCount = Integer.parseInt(args[1]);
			if(lineCount<=0){
				throw new TailException("Invalid Line Count");
			}
			int intCount;
			StringBuilder line = new StringBuilder();
			while((intCount = stdin.read())!=-1){
				line.append((char)intCount);
				while((intCount = stdin.read())!=-1){
					line.append((char)intCount);
				}
				if(counter<lineCount){
					output.add(line.toString());
					counter++;
				}else{
					output.remove(0);
					output.add(line.toString());
			}
			}
			for (int i = 0; i < counter; i++) {
				stdout.write(output.get(i).getBytes());
				stdout.write(NEWLINE.getBytes());
			}
		}catch (IOException io) {
			io.printStackTrace();
			//throw new TailException(io.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			//throw new TailException(e.getMessage());
}
	}
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
			io.printStackTrace();
			//throw new TailException("");
		}catch (Exception e){
			e.printStackTrace();
			//throw new TailException(e.getMessage());
}
	}
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
	private boolean checkNullInput(String[] args, InputStream stdin, OutputStream stdout) {
		// TODO Auto-generated method stub
		return (args==null) && (stdin==null) && (stdout==null);
	}

}