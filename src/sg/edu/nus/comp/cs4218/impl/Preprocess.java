package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Environment;

public class Preprocess {
	 
	private static ArrayList<String> allFiles = new ArrayList<String>();

	public Preprocess(){
		// This constructor is intentionally empty. Nothing special is needed here.
	 }
	/**
	 * Static method to preprocess the argument in order to find the file which meet the regex.
	 * 
	 * @param argsArray
	 *             String array containing the arguments to pass to the
	 *            applications for running.
	 */
	public ArrayList<String> preprocessArg(String... argsArray) {

		for(int i=0;i<argsArray.length;i++){
			int numberOfLevel=0;
			if(argsArray[i].contains("*")){
				numberOfLevel = processAsterisk(argsArray, numberOfLevel, i);
			}else{
				allFiles.add(argsArray[i]);
			}
		}
		return allFiles;
	}

	/**
	 * Static method to find the file when the input contains Asterisk.
	 * 
	 * @param argsArray
	 *            String array containing the arguments to pass to the
	 *            applications for running.
	 * @param numberOfLevel
	 *            the level of the file location.
	 * @param num
	 *            the position of the argsArray.
	 * 
	 */
	private int processAsterisk(String[] argsArray, int numberOfLevel, int num) {
		String pathUsed="";
		String[] inputTemp = argsArray[num].split("/");
		String input = "";
		for(int j=0;j<inputTemp.length;j++){
			if(inputTemp[j].contains("*")){
				for(int k=0;k<inputTemp.length-j;k++){
					if(k==inputTemp.length-j-1){
						input=input+inputTemp[j+k];
					}else{
						input=input+inputTemp[j+k]+"/";
					}
				}
				break;
			}else{
				pathUsed = pathUsed+inputTemp[j]+"/";
			}
		}
		int output;
		output = preprocessMatchFile(numberOfLevel, pathUsed, input);
		return output;
	}

	/**
	 * Static method to preprocess in order to find the matched file.
	 * 
	 * @param number
	 *            the level of the file location.
	 * @param path
	 *            the path of the file.
	 * @param inputFile
	 *            filename of the file which need to search.
	 * 
	 */
	private int preprocessMatchFile(int number, String path, String inputFile) {
		String filePathString;
		int numberOfLevel=number;
		Path currentDir = Paths.get(Environment.currentDirectory);
		Path filePath = currentDir.resolve(path);
		filePathString=filePath.toString();
		String inputRegex = "^" +filePathString+"\\"+inputFile.replaceAll("\\*", ".*")+"$";
		String doubleBackSlash="\\\\";
		String finalInputRegex= inputRegex.replace("\\", doubleBackSlash);
		finalInputRegex =finalInputRegex.replace("/", doubleBackSlash);
		
		Pattern pattern = Pattern.compile(finalInputRegex);
		for(int k=0;k<finalInputRegex.split(doubleBackSlash).length;k++){
			if(!finalInputRegex.split(doubleBackSlash)[k].isEmpty()){
				numberOfLevel= numberOfLevel+1;
			}
		}
		final File folder = new File(filePath.toString());
		listFilesForFolder(folder,pattern,numberOfLevel);
		if(allFiles.isEmpty()){
			allFiles.add("");
		}
		return numberOfLevel;
	}

	/**
	 * Static method to find the file which matched the regex.
	 * 
	 * @param folder
	 *            the folder of the corresponding path.
	 * @param pattern
	 *            the pattern which the file need to meet.
	 * @param numOfLevel
	 *            the level of the file location.
	 * 
	 */
	public void listFilesForFolder(final File folder, Pattern pattern, int numOfLevel) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,pattern,numOfLevel);
	        } else {
	        	Matcher matcher = pattern.matcher(fileEntry.getPath());
	        	while (matcher.find()) {
	        		if(numOfLevel==fileEntry.getPath().split("\\\\").length){
	        			allFiles.add(fileEntry.getPath());
	        		}
	        	}
	        }
	    }
	}
}
