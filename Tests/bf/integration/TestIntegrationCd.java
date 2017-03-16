package bf.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationCd {

	ShellImpl shellImpl;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		shellImpl = new ShellImpl();
		sg.edu.nus.comp.cs4218.Environment.currentDirectory = System.getProperty("user.dir");
		
	}
	
	/*
	 * Test "cd" return the user to the home directory
	 * assertEquals is used to compared the expected directory and the actual current directory
	 */
	@Test
	public void testEmptyArgs() throws CdException{
		String input = "cd ~";
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String dir = System.getProperty("user.home");
		
		try {
			shellImpl.parseAndEvaluate(input, output);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (ShellException e) {
			// TODO Auto-generated catch block
			fail();
		}
		
		assertEquals(dir, Environment.currentDirectory);
	}
	
	
	@After
	public void tearDown(){
		shellImpl = null;
	}

}
