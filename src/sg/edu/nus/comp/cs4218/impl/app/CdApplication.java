package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.cert.CertPathChecker;

import javax.print.attribute.standard.PrinterLocation;
import javax.xml.bind.helpers.AbstractMarshallerImpl;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;

public class CdApplication implements Application{
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) 
			throws CdException {
		//System.out.println("Inside cd");
		/*
		System.out.println("Args: " + args.length);
		
		for(int i =-0; i<args.length; i++){
			System.out.println("args: " + args[i]);
		}
		*/
		if(args.length == 0){
			String root = System.getProperty("user.home"); //.dir
			Environment.currentDirectory = root;
		}
		else{
			
			String dir = args[0];
			//File file = new File("");
			
			//String currDir = Environment.currentDirectory;
			//System.out.println("current dir : " + currDir);
			//System.out.println("------------------------------------------");
			switch(dir){
			case "-":
				String s1 = "/df";
				isValidSyntax(s1);
				break;
			case "/":
			case "\\":
				rootDirectory();
				break;
			case "~":
				homeDirectory();
				break;
			case ".":
				//remain in current directory, so do nothing
				break;
			case "..":
				//return to previous directory
				previousDirectory();
				break;	
			
			default:
				changeDirectory(dir);
				break;
			}
		
		}
	}
	
	private void rootDirectory(){
		String dir;
		do{
			previousDirectory();
			dir = Environment.currentDirectory;
		}
		while(dir.contains("\\"));		
	}
	
	private void homeDirectory(){
		String dir = System.getProperty("user.home");
		Environment.currentDirectory = dir;
	}
	
	private void previousDirectory(){
		String currDir = Environment.currentDirectory;
		int lastBackSlashIndex = currDir.lastIndexOf('\\');
		if(lastBackSlashIndex != -1){
			String prevDir = currDir.substring(0, lastBackSlashIndex);
			Environment.currentDirectory = prevDir;
		}
		
	}
	
	private void changeDirectory(String dir) throws CdException{
		String currDir = Environment.currentDirectory;
		String newDir = "";
		String currDirPath = "";
		int indexOfFirstBackSlash = currDir.indexOf('\\');
		
		isValidSyntax(dir);
		//if(!isValidSyntax(dir)){
		//	throw new CdException("The directory '" + dir + "' has invalid syntax.");
		//}
		
		if(indexOfFirstBackSlash != -1){
			currDirPath = currDir.substring(indexOfFirstBackSlash).toLowerCase();
		}
		
		dir = appendBackSlashToFront(dir);
		
		
		//System.out.println(dir);
		//System.out.println(currDir.substring(indexOfFirstBackSlash));
		
		if(dir.toLowerCase().contains(currDirPath)){
			System.out.println("if");
			int currDirLen = currDirPath.length();
			
			String inputDirPath = dir.substring(0, currDirLen).toLowerCase() ;
		
			//System.out.println(inputDirPath);
			//System.out.println(currDirPath);
			
			if(inputDirPath.equals(currDirPath)){
				String inputDir = dir.substring(inputDirPath.length());
				//System.out.println(inputDir);
				newDir = replaceContinuousBackSlash(currDir + inputDir);
			}else{
				
			}
			
		}
		else{
			System.out.println("else");
			newDir =replaceContinuousBackSlash(currDir + appendBackSlashToFront(dir));
		}
		
		//System.out.println(newDir);
		File fileDir = new File(newDir);
		
		
		if(fileDir.exists() == false){
			throw new CdException("The directory '" + dir + "' does not exist.");
		}
		if(fileDir.isDirectory() == false){
			throw new CdException("The directory is in dir'" + dir + "' does not exist.");
		}
		
		Environment.currentDirectory = newDir;//removeBackSlash(newDir);
		
	}
	
	/*
	 * Append backslash to the front of directory
	 * Return: string 
	 */
	
	private String appendBackSlashToFront(String dir){
		if(dir.charAt(0) != '\\'){
			dir = "\\" + dir;
		}
		return dir;
	}
	
	private String removeLastBackSlash(String dir){
		int dirLen = dir.length();
		
		if(dir.charAt(dirLen-1) == '\\'){
			dir = dir.substring(0, dirLen-1);
		}
		//System.out.println("1 " + dir);
		return dir;
	}
	
	private String amendWrongSlash(String dir){
		dir = dir.replace("/", "\\");
		return dir;
	}
	
	private String replaceContinuousBackSlash(String dir){
		
		for(int i=0; i<dir.length()-1; i++){
			if(dir.charAt(i) == '\\'){
				if(dir.charAt(i+1) == '\\'){
					dir = dir.replace("\\\\", "\\");
				}
			}
		}
		
		if(dir.contains("\\\\")){
			dir = dir.replace("\\\\", "\\");
		}
		
		dir = removeLastBackSlash(dir);
		//System.out.println(dir);
		return dir;
	}
	
	private void isValidSyntax(String dir) throws CdException{
		
		if(dir.matches(".*[/?<>:*|\"].*"))
	    {
			throw new CdException("The directory '" + dir + "' contains invalid syntax.");
	    }
		
	}
	
	
}
