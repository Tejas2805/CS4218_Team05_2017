package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;



public class CdApplication implements Application{
	
	private String osType;
	
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) 
			throws CdException {
		
		String osName = System.getProperty("os.name");
		osType = getOsType(osName);
		
		if(args.length == 0){
			String root = System.getProperty("user.home"); 
			Environment.currentDirectory = root;
		}
		else{
			String dir = args[0];
			String fileSepar = File.separator;
			
			if(fileSepar.equals(dir)){
				rootDirectory();
			}else if("~".equals(dir)){
				homeDirectory();
			}else if(".".equals(dir)){
				//remain in current directory, so do nothing
			}else if("..".equals(dir)){
				previousDirectory();
			}else{
				specificDirectory(dir);
				
			}
		
		}
	}
	
	/**
	 * This methods checks if the OS type is windows or mac
	 * @param osName The name of os
	 * @return String indicating "win" for windows and "mac" for mac
	 */
	private String getOsType(String osName){
		//String lowerOs = osName.toLowerCase();
		
		if(osName.toLowerCase(Locale.ENGLISH).contains("win")){
			return "win";
		}else{
			return "mac";
		}
	}
	
	/**
	 * This methods returns to the root directory
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void rootDirectory(){
		String dir;
		do{
			previousDirectory();
			dir = Environment.currentDirectory;
		}
		while(dir.contains(File.separator));		
	}
	
	/**
	 * This methods returns to the home directory of the user
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void homeDirectory(){
		String dir = System.getProperty("user.home");
		Environment.currentDirectory = dir;
	}
	
	/**
	 * This methods returns to the previous directory
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void previousDirectory(){
		String currDir = Environment.currentDirectory;//.replace("\\", "/");
		
		int lastSlashIndex = currDir.lastIndexOf(File.separator);
		if(lastSlashIndex != -1){
			String prevDir = currDir.substring(0, lastSlashIndex);
			Environment.currentDirectory = prevDir;
		}
	}
	
	/**
	 * This methods goes to the specific directory given in the input directory
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void specificDirectory(String dir) throws CdException{
		checkAbsolutePath(dir);
		if("win".equals(osType)){
			checkValidSyntax(dir);
		}
		processDirectory(dir);
	}
	
	/**
	 * This methods check if the directory given is an absoulte path
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void checkAbsolutePath(String dir) throws CdException{
		if(String.valueOf(dir.charAt(0)).equals(File.separator)){
			throw new CdException("The directory " + dir + " contains a " + File.separator + " at the front which indicates it is an absoulte path");
		}
	}
	
	/**
	 * This methods check if the syntax of the directory is correct
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void checkValidSyntax(String dir) throws CdException{
		if(dir.matches(".*[\\/?<>:*|\"].*"))
	    {
			throw new CdException("The directory '" + dir + "' contains invalid syntax \\/?<>:*|\".");
	    }
	}
	
	/**
	 * This methods process the input directory
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void processDirectory(String dir) throws CdException{
		
		if(dir.contains(File.separator)){		
			
			String[] dirParts = dir.split(Pattern.quote(File.separator));
			
			for (int i =0 ;i<dirParts.length; i++){	
				if(!dirParts[i].equals("")){
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
	
	/**
	 * This methods changes the directory to the input directory
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void changeDirectory(String dir) throws CdException{
		String currDir = Environment.currentDirectory;//.replace("\\", "/");
		String newDir = dir;
	
		if(".".equals(newDir)){
			return;
		}
			
		newDir = appendFrontSlashToFront(newDir);
		
		//newDir =replaceContinuousFrontSlash(currDir + appendFrontSlashToFront(newDir));
		newDir = currDir + newDir;
		
		
		File fileDir = new File(newDir);
	
		if(!fileDir.exists()){
			throw new CdException("The directory '" + dir + "' does not exist.");
		}
		if(!fileDir.isDirectory()){
			throw new CdException("The directory '" + dir + "' does not exist.");
		}
		
		Environment.currentDirectory = newDir;
	}
	
	/**
	 * This methods appends a front slash '/' to the front of a directory if the first char is not a front slash '/'
	 * @param dir The input directory
	 * @return String directory with front slash '/' appended at the front
	 */
	private String appendFrontSlashToFront(String dir){
		String newDir = dir;
		if(!String.valueOf(dir.charAt(0)).equals(File.separator)){
			newDir = File.separator + dir;
		}
		return newDir;
	}
	
	/**
	 * This methods remove the last char if the last char of a directory is a front slash '/'
	 * @param dir The input directory
	 * @return String directory with last char, front slash '/', removed
	 */
	private String removeLastFrontSlash(String dir){
		int dirLen = dir.length();
		String newDir = dir;
		if(!String.valueOf(dir.charAt(dirLen-1)).equals(File.separator)){
			newDir = dir.substring(0, dirLen-1);
		}
		return newDir;
	}
	
	/**
	 * This methods convert continuous front slash (e.g. '/////////') to a single front slash '/'
	 * @param dir The input directory
	 * @return String directory with continuous front slash replace with a single front slash '/'
	 */
	private String replaceContinuousFrontSlash(String dir){
		String newDir = dir;
		for(int i=0; i<newDir.length()-1; i++){
			if(newDir.charAt(i) == '/' && newDir.charAt(i+1) == '/'){
				newDir = newDir.replace("//", "/");
			}
		}		
		if(newDir.contains("//")){
			newDir = newDir.replace("//", "/");
		}
		newDir = removeLastFrontSlash(newDir);
		return newDir;
	}
	
	
}
