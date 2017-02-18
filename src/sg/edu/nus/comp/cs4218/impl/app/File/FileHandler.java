package sg.edu.nus.comp.cs4218.impl.app.File;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.GrepException;

public class FileHandler {
	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws CatException
	 *             If the file is not readable
	 * @throws GrepException 
	 */
	
	public boolean checkIfFileIsReadable(Path filePath) throws GrepException {
		
		if (Files.isDirectory(filePath)) {
			throw new GrepException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new GrepException("Could not read file");
		}
	}
	
	public List<Path> getValidFilePathsFromString(String[] strFilePaths, int start, int end){
		List<Path> validFilePaths = new ArrayList<>();
		Path filePath;
		
		for(int i = start; i <=end; i++){
			
			try{
				filePath = Paths.get(strFilePaths[i]);
			}catch(InvalidPathException e){
				continue;
			}
			
			try {
				if(checkIfFileIsReadable(filePath)){
					validFilePaths.add(filePath);
				}
			} catch (GrepException e) {
				continue;
			}
		}
		
		return validFilePaths;
	}
}
