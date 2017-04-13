import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.DateException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class HackathonBugFixTestCases {

	static ShellImpl shellImpl;
	static OutputStream stdout;
	static SortRead sortRead;	
	static CatApplication catApplication;
	static CalApplication calApplication;
	static GrepApplication grepApplication;
	static SedApplication sedApp;
	
	private final static String HACKATHON_FILE_PATH = "HackathonTestFiles" + File.separator; 
	private final static String WC_FILE = "wc.txt";
	private final static String UNSORTED_FILE = "sort.txt";
	private final static String NEWLINE = System.lineSeparator();
	
	
	static final String SORTED_TEXT_N = 
			System.lineSeparator() +
			"@" + System.lineSeparator() +
			"-1" + System.lineSeparator() +
			"100" +	System.lineSeparator() +	 
			"A" + System.lineSeparator() +
			"a" + System.lineSeparator();
	
	/*
	 * Reported Bug:
	 * 01 - Cat: No exception when file is not valid
	 * 02 - Cal: No exception if number argument is more than 3
	 * 03 - Cal: No exception if month is not valid (negative or > 12)
	 * 04 - Command substitution: Cat does not work with echo 
	 * 05 - grep & sed do not throw exception for null args
	 * 06 - Head, Tail: run correctly and throws exception message when input negative line numbers
	 * 08 - Date: run correctly and also throw exception on more than 0 input args
	 * 09 - Grep: 0 args does not return exception
	 * 10 - Grep: Does not throw exception on invalid pattern
	 * 11 - Grep: Does not throw exception for nonexistent file
	 * 12 - Sed: return null for correct case using | as separator
	 * 13 - Wc: throw exception when using option + stdin
	 * 14 - Wc: return nothing when using more than 1 options + stdin
	 * 15 - Globbing: run correctly and for nonexistent path, keep ARG and throwing nonexistent file exception
	 * 16 - Globbing: run correctly and asterisk (*) in quotation is not interpreted as globbing
	 * 19 - Sort: numbers (-n option) wit stdin returns nothing
	 * 20 - Pipe: no exception thrown when a component is invalid 
	 */
	
	
	
	
	@Before
    public void setUp(){
		shellImpl = new ShellImpl();
		stdout = new ByteArrayOutputStream();
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
	 * Fixed Bug No.4
	 * Test for Bug No. 4
	 * Command substitution of Cat and Echo will run correctly
	 */
	@Test
	public void testCommandSubstitutionCatAndEcho() {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		try{
		shell.parseAndEvaluate("cat `echo \"Tests/headFiles/123.txt\"`",outStream);
		}catch(Exception e){
			assertEquals(e.getMessage(),"Head: File Not Exists");
		}
		
		String testStr = "31423"+NEWLINE+"1" + NEWLINE + "15ew"+ NEWLINE + "afg" + NEWLINE + "gaqwtq345" + NEWLINE +"tqtqt" + NEWLINE + "c592859v" +NEWLINE +"gasgsad";
		assertEquals( testStr,outStream.toString());
	}
	
	
	/*
	 * Fixed Bug No.5 and 9
	 * Test for Bug No. 5 and 9
	 * "grep" command will throw exception if args is not null or 0
	 */
	@Test
	public void testGrepWithoutArguments(){
		
		String[] argsNull = null;
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		String expected = "";
		String actual;

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
	 * Fixed Bug No.6
	 * Test for Bug No. 6
	 * "head" command will throw exception if receives negative value of line numbers
	 */
	@Test
	public void testHeadNegativeLineValues() throws ShellException{
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		
		try {
			shell.parseAndEvaluate("head -n -5 Tests/headFiles/123.txt ", stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("Head: Invalid Format"+NEWLINE+"Line Number must be non-negative value",actual);
		}
		
	}
	
	/*
	 * Fixed Bug No.6
	 * Test for Bug No. 6
	 * "tail" command will throw exception if receives negative value of line numbers
	 */
	@Test
	public void testTailNegativeLineValues() throws ShellException{
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();

		try {
			shell.parseAndEvaluate("tail -n -5 Tests/headFiles/123.txt ", stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("Tail: Invalid Format"+NEWLINE+"Line Number must be non-negative value",actual);
		}
		
	}
	
	
	
	
	/*
	 * Fixed Bug No.8
	 * Test for Bug No. 8
	 * "date" command does not accept arg. If there is no arg the the date and time will be printed
	 */
	@Test
	public void testPrintDateNoArg() throws AbstractApplicationException, ShellException {	
		String cmdline = "date";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		
		String results = "";
		java.util.Date date = new java.util.Date();
		results = date.toString().replace('+', '-') + System.lineSeparator();	
		results = results.replaceAll("GMT-08:00", "SGT");
	
		assertEquals(results, stdout.toString());
	}
	

	/*
	 * Fixed Bug No.8
	 * Test for Bug No. 8
	 * Error msg will be thrown when "date" cmd has an arg
	 */
	@Test (expected = DateException.class)
	public void testDateInvalidArgs() throws AbstractApplicationException, ShellException {
		String cmdline = "date invalidArg";
		shellImpl.parseAndEvaluate(cmdline, stdout);
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
	
	/*
	 * Fixed Bug No.12
	 * Test for Bug No. 12
	 * "sed" command will handle different seperators as well
	 */
	@Test
	public void testSedWithDifferentSeparator() throws Exception {
		String[] args = {"s|e|E|","Tests\\sedFiles\\test.txt"};
		InputStream inputStream = System.in;
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			sedApp.run(args, inputStream, outputStream);
			
		} catch (AbstractApplicationException e) {
			fail();
		}
		String actual = outputStream.toString();
		String expected = "hEllo"+System.lineSeparator();
		
		assertEquals(expected, actual);
	}

	/*
	 * Fixed Bug No 13
	 * Test for Bug No.13
	 * wc return results when using more than 1 options + stdin
	 */
	@Test
	public void testWcTwoOptionStdin() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + WC_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "wc -lwm < " + strArg ;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = "   65   10   11" + System.lineSeparator();
		assertEquals(expectedResults, stdout.toString());
		
	}
	
	/*
	 * Fixed Bug No 14
	 * Test for Bug No.14
	 * wc does not throw exception when using option + stdin
	 * using wc -m -w -l < wc.txt
	 */
	@Test
	public void testWcOptionStdinRedir() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + WC_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "wc -m -w -l < " + strArg ;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = "   65   10   11" + System.lineSeparator();
		assertEquals(expectedResults, stdout.toString());
		
	}
	
	/*
	 * Fixed Bug No 14
	 * Test for Bug No.14
	 * wc does not throw exception when using option + stdin
	 * using cat wc.txt | wc -m -w -l
	 */
	@Test
	public void testWcOptionStdinPipe() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + WC_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "cat " + strArg  + " | wc -m -w -l ";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = "   65   10   11" + System.lineSeparator();
		assertEquals(expectedResults, stdout.toString());
		
	}
	
	
	/*
	 * Fixed Bug No.15
	 * Test for Bug No.15
	 * For nonexistent path in globbing, keep ARG and throwing nonexistent file exception
	 */
	@Test
	public void testGlobNonExistentPath() throws AbstractApplicationException, ShellException {	
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 HackathonTestFiles\\1*";
		
		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("grep: Error occurred",actual);
		}
	}
	
	/*
	 * Fixed Bug No.16
	 * Test for Bug No.16
	 * asterisk (*) in double quotes is not interpreted as globbing
	 */
	@Test
	public void testGlobWithDoubleQuotes() throws AbstractApplicationException, ShellException {	
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 \"HackathonTestFiles\\*\"";
		
		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("grep: Error occurred",actual);
		}
	}
	
	/*
	 * Fixed Bug No.16
	 * Test for Bug No.16
	 * asterisk (*) in single quotes is not interpreted as globbing
	 */
	@Test
	public void testGlobWithSingleQuotes() throws AbstractApplicationException, ShellException {	
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 'HackathonTestFiles\\*'";
		
		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("grep: Error occurred",actual);
		}
	}
	/*
	 * Fixed Bug No.16
	 * Test for Bug No.16
	 * asterisk (*) without quote is interpreted as globbing
	 */
	@Test
	public void testGlobWithoutQuotes() throws AbstractApplicationException, ShellException {	
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 HackathonTestFiles\\*";
		
		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
		}
		assertEquals("The power is over 9000 !\n",stdout.toString());
	}
	
	/*
	 * Fixed Bug No 19
	 * Test for Bug No.19
	 * sort numbers (-n option) with stdin returns a result
	 * using sort -n < sort.txt
	 */
	@Test
	public void testSortOptionNStdinRedir() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + UNSORTED_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "sort -n < " + strArg ;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = SORTED_TEXT_N;
		assertEquals(expectedResults, stdout.toString());		
	}
	
	/*
	 * Fixed Bug No 19
	 * Test for Bug No.19
	 * sort numbers (-n option) with stdin returns a result
	 * using cat sort.txt | sort -n
	 */
	@Test
	public void testSortOptionNStdinPipe() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + UNSORTED_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "cat " + strArg  + " | sort -n";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = SORTED_TEXT_N;
		assertEquals(expectedResults, stdout.toString());		
	}
	
	
	
	/*
	 * Fixed Bug No.20
	 * Test for Bug No. 20
	 * "head" command will throw exception if a component is invalid
	 */
	
	@Test
	public void testPipeWithInvalidComponent() throws AbstractApplicationException, ShellException {	
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		try{
		shell.parseAndEvaluate("echo `head invalid.txt` | grep \"usage\"",outStream);
		}catch(Exception e){
			assertEquals(e.getMessage(),"Head: File Not Exists");
		}
		
	}
	
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}

}
