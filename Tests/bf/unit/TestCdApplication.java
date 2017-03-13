package bf.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.channels.ScatteringByteChannel;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;

public class TestCdApplication {

	CdApplication cdApp;
	String strUserHome = "user.home";
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		cdApp = new CdApplication();
	}
	
	/*
	 * Test "cd" return the user to the home directory
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testEmptyArgs() throws CdException{
		String[] args = {};
		
		String dir = System.getProperty(strUserHome);
		cdApp.run(args, null, null);
		assertEquals(dir, Environment.currentDirectory);
	}
	/*
	 * Test "cd /" (for mac or linux) return the user to the root directory
	 * Test "cd \" (for windows) returns the user to the root directory
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testRootDirectory() throws CdException{
		String[] args = {File.separator};
		String userDir = System.getProperty(strUserHome);
		String[] root = userDir.split(Pattern.quote(File.separator));
		String rootDir = root[0];
		cdApp.run(args, null, null);
		assertEquals(rootDir, Environment.currentDirectory);
	}
	
	/*
	 * Test "cd ~" return the user to the home directory
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testHomeDirectory() throws CdException{
		String[] args = {"~"};
		String dir = System.getProperty(strUserHome);
		cdApp.run(args, null, null);
		assertEquals(dir, Environment.currentDirectory);
	}
	
	/*
	 * Test "cd ." remain in the current working directory
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testCurrentDirectory() throws CdException{
		String[] args = {"."};
		String dir = Environment.currentDirectory;
		cdApp.run(args, null, null);
		assertEquals(dir, Environment.currentDirectory);
	}
	
	/*
	 * Test "cd .." returns to the parent directory
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testParentDirectory() throws CdException{
		String[] args = {".."};
		String currDir = Environment.currentDirectory;
		String prevDir = currDir;
		
		int lastSlashIndex = currDir.lastIndexOf(File.separator);
		if(lastSlashIndex != -1){
			prevDir = currDir.substring(0, lastSlashIndex);
		}
		
		cdApp.run(args, null, null);
		assertEquals(prevDir, Environment.currentDirectory);
	}
	
	/*
	 * Test "cd a" where "a" is a invalid directory. An error will be thrown when the directory is invalid or does not exist
	 */
	@Test
	public void testCdToInvalidDirectory() throws CdException{
		String[] args = {"a"};
		thrown.expect(CdException.class);
		//Cd: The directory 'a' does not exist.
	    thrown.expectMessage("Cd: The directory '" + args[0] + "' does not exist.");
		cdApp.run(args, null, null);
	}
	
	/*
	 * Test "cd [valid directory]"
	 * This test simulate changing to a valid directory
	 * Let the userDir = C:\Users\Tom
	 * prevDir = C:\Users
	 * Set the "Environment.currentDirectory = prevDir (C:\Users)
	 * args = {Tom}
	 * When cdApp.run(args, null, null) is called, it will simulate "cd Tom"
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testCdToValidDirectory() throws CdException{
		
		String userDir = System.getProperty(strUserHome);
		String [] userDirParts = userDir.split(Pattern.quote(File.separator));
		
		if(userDirParts.length > 1){
			String currDir = userDir;
			String prevDir = currDir;
			
			int lastSlashIndex = currDir.lastIndexOf(File.separator);
			if(lastSlashIndex > -1){
				prevDir = currDir.substring(0, lastSlashIndex);
			}
			Environment.currentDirectory = prevDir;
			
			String[] args = {userDirParts[userDirParts.length-1]};
			
			cdApp.run(args, null, null);
				
			assertEquals(prevDir + File.separator + args[0], Environment.currentDirectory);
			
		}else{
			assertFalse(false);
		}
		
	}
	
	
	@After
	public void tearDown(){
		cdApp = null;
	}
	

}
