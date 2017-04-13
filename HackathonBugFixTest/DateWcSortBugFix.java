import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DateException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class DateWcSortBugFix {

	//Reported Bug:
	//8 Date: run correctly and also throw exception on more than 0 input args
	//13 Wc: throw exception when using option + stdin
	//14 Wc: return nothing when using more than 1 options + stdin
	//19 Sort: numbers (-n option) wit stdin returns nothing
	
	
	ShellImpl shellImpl;
	OutputStream stdout;
	SortRead sortRead;	
	
	static final String HACKATHON_FILE_PATH = "HackathonTestFiles" + File.separator; 
	static final String WC_FILE = "wc.txt";
	static final String UNSORTED_FILE = "sort.txt";

	
	static final String SORTED_TEXT_N = 
			System.lineSeparator() +
			"@" + System.lineSeparator() +
			"-1" + System.lineSeparator() +
			"100" +	System.lineSeparator() +	 
			"A" + System.lineSeparator() +
			"a" + System.lineSeparator();
	
	@Before
    public void setUp(){
		shellImpl = new ShellImpl();
		stdout = new ByteArrayOutputStream();
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
	public void testWcOptionStdin1() throws AbstractApplicationException, ShellException{
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
	public void testWcOptionStdin2() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + WC_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "cat " + strArg  + " | wc -m -w -l ";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = "   65   10   11" + System.lineSeparator();
		assertEquals(expectedResults, stdout.toString());
		
	}
	
	/*
	 * Fixed Bug No 19
	 * Test for Bug No.19
	 * sort numbers (-n option) with stdin returns a result
	 * using sort -n < sort.txt
	 */
	@Test
	public void testSortOptionNStdin1() throws AbstractApplicationException, ShellException{
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
	public void testSortOptionNStdin2() throws AbstractApplicationException, ShellException{
		String strArg = HACKATHON_FILE_PATH + UNSORTED_FILE;
		stdout = new ByteArrayOutputStream();
		String cmdline = "cat " + strArg  + " | sort -n";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults = SORTED_TEXT_N;
		assertEquals(expectedResults, stdout.toString());		
	}
	
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}

	

}
