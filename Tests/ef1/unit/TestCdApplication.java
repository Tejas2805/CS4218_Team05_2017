package ef1.unit;

import static org.junit.Assert.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
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
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		cdApp = new CdApplication();
	}
	
	@Test
	public void testEmptyArgs() throws CdException{
		String[] args = {};
		String dir = System.getProperty("user.home");
		cdApp.run(args, null, null);
		assertEquals(dir, Environment.currentDirectory);
	}
	
	@Test
	public void testRootDirectory() throws CdException{
		String[] args = {File.separator};
		String userDir = System.getProperty("user.home");
		String[] root = userDir.split(Pattern.quote(File.separator));
		String rootDir = root[0];
		cdApp.run(args, null, null);
		assertEquals(rootDir, Environment.currentDirectory);
	}
	
	@Test
	public void testHomeDirectory() throws CdException{
		String[] args = {"~"};
		String dir = System.getProperty("user.home");
		cdApp.run(args, null, null);
		assertEquals(dir, Environment.currentDirectory);
	}
	
	@Test
	public void testCurrentDirectory() throws CdException{
		String[] args = {"."};
		String dir = Environment.currentDirectory;
		cdApp.run(args, null, null);
		assertEquals(dir, Environment.currentDirectory);
	}
	
	@Test
	public void testPreviousDirectory() throws CdException{
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
	
	@Test
	public void testCdToInvalidDirectory() throws CdException{
		String[] args = {"a"};
		thrown.expect(CdException.class);
		//Cd: The directory 'a' does not exist.
	    thrown.expectMessage("Cd: The directory '" + args[0] + "' does not exist.");
		cdApp.run(args, null, null);
	}
	
	@Test
	public void testCdToValidDirectory() throws CdException{
		String[] args = {"tests"};
		String validDir = Environment.currentDirectory + File.separator + args[0];
		
		
		cdApp.run(args, null, null);
		assertEquals(validDir, Environment.currentDirectory);
	}
	
	
	@After
	public void tearDown(){
		cdApp = null;
	}
	

}
