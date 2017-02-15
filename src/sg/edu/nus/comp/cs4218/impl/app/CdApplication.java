package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

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
			String root = System.getProperty("user.home"); 
			Environment.currentDirectory = root;
		}
		else{
			String dir = args[0];
			
			if(dir.equals(File.separator)){
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
	 * @param os The name of os
	 * @return String indicating "win" for windows and "mac" for mac
	 */
	private String getOsType(String os){
		os = os.toLowerCase();
		
		if(os.contains("win")){
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
		while(dir.contains("/"));		
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
		String currDir = Environment.currentDirectory.replace("\\", "/");
		
		int lastFrontSlashIndex = currDir.lastIndexOf('/');
		if(lastFrontSlashIndex != -1){
			String prevDir = currDir.substring(0, lastFrontSlashIndex);
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
		if(OS == "win"){
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
		if(dir.charAt(0) == '/'){
			throw new CdException("The directory '" + dir + "' contains a '/' at the front which indicates it is an absoulte path");
		}
	}
	
	/**
	 * This methods check if the syntax of the directory is correct
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void checkValidSyntax(String dir) throws CdException{
		if(dir.matches(".*[\\?<>:*|\"].*"))
	    {
			throw new CdException("The directory '" + dir + "' contains invalid syntax \\?<>:*|\".");
	    }
	}
	
	/**
	 * This methods process the input directory
	 * @param dir The input directory
	 * @exception CdException On directory error.
	 * @return none
	 */
	private void processDirectory(String dir) throws CdException{
		
		if(dir.contains("/")){			
			String[] dirParts = dir.split("/");

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
		String currDir = Environment.currentDirectory.replace("\\", "/");
		String newDir = "";
	
		if(dir.equals(".")){
			return;
		}
			
		dir = appendFrontSlashToFront(dir);
		
		newDir =replaceContinuousFrontSlash(currDir + appendFrontSlashToFront(dir));
		
		File fileDir = new File(newDir);
	
		if(fileDir.exists() == false){
			throw new CdException("The directory '" + dir + "' does not exist.");
		}
		if(fileDir.isDirectory() == false){
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
		if(dir.charAt(0) != '/'){
			dir = "/" + dir;
		}
		return dir;
	}
	
	/**
	 * This methods remove the last char if the last char of a directory is a front slash '/'
	 * @param dir The input directory
	 * @return String directory with last char, front slash '/', removed
	 */
	private String removeLastFrontSlash(String dir){
		int dirLen = dir.length();
		
		if(dir.charAt(dirLen-1) == '/'){
			dir = dir.substring(0, dirLen-1);
		}
		return dir;
	}
	
	/**
	 * This methods convert continuous front slash (e.g. '/////////') to a single front slash '/'
	 * @param dir The input directory
	 * @return String directory with continuous front slash replace with a single front slash '/'
	 */
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
	
	
}
