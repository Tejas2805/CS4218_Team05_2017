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
	
	private String OS;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) 
			throws CdException {
		
		String osName = System.getProperty("os.name");
		OS = getOsType(osName);
		
		
		
		
		
		if(args.length == 0){
			String root = System.getProperty("user.home"); //.dir
			Environment.currentDirectory = root;
		}
		else{
			String dir = args[0];
			
			switch(dir){
			case "/":
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
				validateDirectory(dir);
				break;
			}
		
		}
	}
	
	private String getOsType(String os){
		os = os.toLowerCase();
		
		if(os.contains("win")){
			return "win";
		}else{
			return "mac";
		}
	}
	
	private void rootDirectory(){
		String dir;
		do{
			previousDirectory();
			dir = Environment.currentDirectory;
		}
		while(dir.contains("/"));		
	}
	
	private void homeDirectory(){
		String dir = System.getProperty("user.home");
		Environment.currentDirectory = dir;
	}
	
	private void previousDirectory(){
		String currDir = Environment.currentDirectory.replace("\\", "/");
		
		int lastFrontSlashIndex = currDir.lastIndexOf('/');
		if(lastFrontSlashIndex != -1){
			String prevDir = currDir.substring(0, lastFrontSlashIndex);
			Environment.currentDirectory = prevDir;
		}
		//System.out.println("Env dir: " + Environment.currentDirectory);
	}
	
	private void validateDirectory(String dir) throws CdException{
		checkAbsolutePath(dir);
		if(OS == "win"){
			checkValidSyntax(dir);
		}
		checkValidDirectory(dir);
	}
	
	private void changeDirectory(String dir) throws CdException{
		String currDir = Environment.currentDirectory.replace("\\", "/");
		String newDir = "";
		//String currDirPath = "";
		//int indexOfFirstFrontSlash = currDir.indexOf('/');
		
		//Check valid syntax
		if(dir.equals(".")){
			return;
		}
		
		/*
		if(indexOfFirstFrontSlash != -1){
			currDirPath = currDir.substring(indexOfFirstFrontSlash).toLowerCase();
		}
		*/
		
		dir = appendFrontSlashToFront(dir);
		
		newDir =replaceContinuousFrontSlash(currDir + appendFrontSlashToFront(dir));
		/*
		if(dir.toLowerCase().contains(currDirPath)){
			//Absoulute path
			int currDirLen = currDirPath.length();
			
			String inputDirPath = dir.substring(0, currDirLen).toLowerCase() ;
		
			if(inputDirPath.equals(currDirPath)){
				String inputDir = dir.substring(inputDirPath.length());
				newDir = replaceContinuousFrontSlash(currDir + inputDir);
			}
		}
		else{
			newDir =replaceContinuousFrontSlash(currDir + appendFrontSlashToFront(dir));
		}
		*/
		File fileDir = new File(newDir);
	
		if(fileDir.exists() == false){
			throw new CdException("The directory '" + dir + "' does not exist.");
		}
		if(fileDir.isDirectory() == false){
			throw new CdException("The directory '" + dir + "' does not exist.");
		}
		
		Environment.currentDirectory = newDir;//removeBackSlash(newDir);
		
	}
	
	/*
	 * Append backslash to the front of directory
	 * Return: string 
	 */
	
	private String appendFrontSlashToFront(String dir){
		if(dir.charAt(0) != '/'){
			dir = "/" + dir;
		}
		return dir;
	}
	
	private String removeLastFrontSlash(String dir){
		int dirLen = dir.length();
		
		if(dir.charAt(dirLen-1) == '/'){
			dir = dir.substring(0, dirLen-1);
		}
		return dir;
	}
	
	
	
	private String replaceContinuousFrontSlash(String dir){
		
		for(int i=0; i<dir.length()-1; i++){
			if(dir.charAt(i) == '/'){
				if(dir.charAt(i+1) == '/'){
					dir = dir.replace("//", "/");
				}
			}
		}
		
		if(dir.contains("//")){
			dir = dir.replace("//", "/");
		}
		
		dir = removeLastFrontSlash(dir);
		return dir;
	}
	
	private void checkAbsolutePath(String dir) throws CdException{
		if(dir.charAt(0) == '/'){
			throw new CdException("The directory '" + dir + "' contains a '/' at the front which indicates it is an absoulte path");
		}
	}
	
	private void checkValidSyntax(String dir) throws CdException{
		
		if(dir.matches(".*[\\?<>:*|\"].*"))
	    {
			throw new CdException("The directory '" + dir + "' contains invalid syntax \\?<>:*|\".");
	    }
		
	}
	
	private void checkValidDirectory(String dir) throws CdException{
		
		if(dir.contains("/")){	
			//dir = dir.replace("\\", "\\\\");
			//String[] dirParts = dir.split("\\\\");
			
			
			String[] dirParts = dir.split("/");

			for (int i =0 ;i<dirParts.length; i++){	
				if(!dirParts[i].equals("")){
					/*
					if(dirParts[i].contains(".")){
						if(!dirParts[i].equals(".") && !dirParts[i].equals("..")){
							throw new CdException("More dots The directory '" + dir + "' is invalid");
						}
					}
					*/
					//if(dirParts[i].equals(".") && i != 0){
					//	throw new CdException("Wrong dots The directory '" + dir + "' is invalid");
					//}
					if(dirParts[i].equals("..")){
						previousDirectory();
					}
					else{
						changeDirectory(dirParts[i]);
					}
				}
			}
		
		}
		else{
			changeDirectory(dir);
		}
	}
	
	
}
