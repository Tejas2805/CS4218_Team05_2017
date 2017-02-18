package ef1.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;

public class TestCdApplication {

	CdApplication cdApp;
	
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
	
	
	@After
	public void tearDown(){
		cdApp = null;
	}
	

}
