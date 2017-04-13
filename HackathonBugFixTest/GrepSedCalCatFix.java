import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.*;

public class GrepSedCalCatFix {

	static CatApplication catApplication;
	static CalApplication calApplication;
	static GrepApplication grepApplication;
	static SedApplication sedApp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		catApplication = new CatApplication();
		calApplication = new CalApplication();
		sedApp = new SedApplication();
		grepApplication = new GrepApplication();
	}
	
	/*
	 * Fixed Bug No.1
	 * Test for Bug No. 1
	 * "cat" command will throw exception if file is non existant
	 */
	@Test
	public void catWithNonExistantFile(){
		String[] args = {"asdsad.txt"};
		InputStream stdin = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String expected = "", actual;
		
		try {
			catApplication.run(args, stdin, output);
			fail();
		} catch (CatException e) {

			actual = output.toString();
			assertEquals(expected, actual);
			return;
		}

		fail();
		
	}
	
	/*
	 * Fixed Bug No.2
	 * Test for Bug No. 2
	 * "cal" command will throw exception if args is more than 3
	 */
	@Test
	public void testMoreThanThreeArgs()
	{
		OutputStream os = System.out;
		String[] args1 = {"1", "2", "3", "4"};
		String[] args2 = {"1", "2", "3", "4", "5"};
		
		try {
			calApplication.run(args1, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		
		try {
			calApplication.run(args2, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		
	}

	/*
	 * Fixed Bug No.3
	 * Test for Bug No. 3
	 * "cal" command will throw exception if args is not valid
	 */
	@Test
	public void testInvalidDates()
	{
		String[] args1 = {"cal", "-3"};
		String[] args2 = {"cal", "-m", "-3"};
		String[] args3 = {"cal", "-m", "-3", "-12"};
		String[] args4 = {"cal", "-3", "-12"};
		OutputStream os = System.out;
		try {
			calApplication.run(args1, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		try {
			calApplication.run(args2, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		try {
			calApplication.run(args3, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		try {
			calApplication.run(args4, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		
	}
	
	/*
	 * Fixed Bug No.5 and 9
	 * Test for Bug No. 5 and 9
	 * "grep" command will throw exception if args is not null or 0
	 */
	@Test
	public void testGrepWithoutArguments(){
		
		String[] argsEmpty = {};
		String[] argsNull = null;
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "";
		String actual;
//		try {
//			grepApplication.run(argsEmpty, inputStream, outputStream);
//			
//			fail();
//		} catch (AbstractApplicationException e) {
//			actual = outputStream.toString();
//			assertEquals(expected, actual);
//		}
//		
		
		try {
			grepApplication.run(argsNull, inputStream, outputStream);
			actual = outputStream.toString();
			assertEquals(expected, actual);
			fail();
		} catch (AbstractApplicationException e) {
		}
		
	}

	/*
	 * Fixed Bug No.5 
	 * Test for Bug No. 5 and 9
	 * "sed" command will throw exception if args is not null or 0
	 */
	@Test
	public void testSedsWithNoArguments() {
		String[] args = {};
		InputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		try {
			sedApp.run(args, stdin, stdout);
			fail();
		} catch (SedException e) {
			try {
				sedApp.run(args, stdin, stdout);
				fail();
			} catch (SedException ee) {
				return;
			}
		}
	}
	
	/*
	 * Fixed Bug No.10
	 * Test for Bug No. 10
	 * "grep" command will throw exception if pattern is invalid
	 */
	@Test
	public void testGrepWithInvalidPattern(){
		
		String[] argsOnlyPattern = {"***", "Tests\\grepFiles\\grepTestSource1.txt"};
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			fail();
		} catch (AbstractApplicationException e) {
			return;
		}
		
		fail();
	}
	
	
	/*
	 * Fixed Bug No.11
	 * Test for Bug No. 11
	 * "grep" command will throw exception if file is non-existant
	 */
	@Test
	public void testGrepWithNonExistantFile(){
		
		String[] argsOnlyPattern = {"pattern", "aaa.txt"};
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			grepApplication.run(argsOnlyPattern, inputStream, outputStream);
			fail();
		} catch (AbstractApplicationException e) {
			return;
		}
		
		fail();
		
	}
}
