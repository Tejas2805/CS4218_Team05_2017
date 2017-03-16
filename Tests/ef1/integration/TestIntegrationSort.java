package ef1.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestIntegrationSort {

	ShellImpl shellImpl;
	ByteArrayOutputStream stdout;
	SortRead sortRead;	
	String toSort;
	
	static final String UNSORTED_FILE = "sort.txt";
	static final String SPECIAL_FILE = "specialChar.txt";
	static final String NUMBER_FILE = "number.txt";
	static final String CAPITAL_FILE = "capital.txt";
	static final String SIMPLE_FILE = "simple.txt";
	static final String SORT_SIMPLE_FILE = "sortSimple.txt";
	static final String	SORT_CAPITAL_FILE = "sortCapital.txt";
	static final String SORT_NUMBER_FILE = "sortNumbers.txt";
	static final String SORT_NUMBER_N_FILE = "sortNumbers-n.txt";
	static final String SORT_SPECIAL_FILE = "sortSpecialChars.txt";
	static final String SORT_SIMPLE_CAPITAL = "sortSimpleCapital.txt";	
	static final String SORT_SIMPLE_NUMBER = "sortSimpleNumbers.txt";
	static final String SORT_SIMPLE_NUMBER_N = "sortSimpleNumbers-n.txt";
	static final String SORT_SIMPLE_SPECIAL = "sortSimpleSpecialChars.txt";
	static final String SORT_CAPITAL_NUMBER = "sortCapitalNumbers.txt";
	static final String SORT_CAPITAL_NUMBER_N ="sortCapitalNumbers-n.txt";
	static final String SORT_CAPITAL_SPECIAL = "sortCapitalSpecialChars.txt";
	static final String SORT_NUMBER_SPECIAL = "sortNumbersSpecialChars.txt";
	static final String SORT_NUMBER_SPECIAL_N = "sortNumbersSpecialChars-n.txt";
	static final String SORT_SIMPLE_CAPITAL_NUMBER = "sortSimpleCapitalNumbers.txt";
	static final String SORT_SIMPLE_CAPITAL_NUMBER_N = "sortSimpleCapitalNumbers-n.txt";
	static final String SORT_SIMPLE_CAPITAL_SPECIAL = "sortSimpleCapitalSpecialChars.txt";
	static final String SORT_SIMPLE_NUMBER_SPECIAL = "sortSimpleNumbersSpecialChars.txt";
	static final String SORT_SIMPLE_NUMBER_SPECIAL_N = "sortSimpleNumbersSpecialChars-n.txt";
	static final String SORT_CAPITAL_NUMBER_SPECIAL = "sortCapitalNumbersSpecialChars.txt";
	static final String SORT_CAPITAL_NUMBER_SPECIAL_N = "sortCapitalNumbersSpecialChars-n.txt";
	static final String SORT_ALL_FILE = "sortAll.txt";
	static final String SORT_ALL_N_FILE = "sortAll-n.txt";
	
	String FILE_PATH = "tests" + File.separator + "sortFiles" + File.separator;
	static final String SORT_CMD = "sort ";
	String cmdline;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
		sortRead = new SortRead();
		String sortFilename = FILE_PATH + UNSORTED_FILE;
		String data = sortRead.readFromFile(sortFilename);
		toSort = "" + System.lineSeparator() + data;
		stdout = new ByteArrayOutputStream();
	}
	
	@Test
	public void testOneArg() throws AbstractApplicationException, ShellException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		cmdline = SORT_CMD + strArgs;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testOneArgWithN() throws AbstractApplicationException, ShellException{
		
		String strArgs = "-n " + FILE_PATH + UNSORTED_FILE;
		cmdline = SORT_CMD + strArgs;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testInvalidOptionValidFile() throws AbstractApplicationException, ShellException{
		
		String strArgs = "-nn " + FILE_PATH + UNSORTED_FILE;
		cmdline = SORT_CMD + strArgs;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testValidAndInvalidOptionValidFile() throws AbstractApplicationException, ShellException{
		
		String strArgs = "-m " + FILE_PATH + UNSORTED_FILE + " -n";
		cmdline = SORT_CMD + strArgs;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testSortMultipleFiles() throws AbstractApplicationException, ShellException{
		
		String strSimple = FILE_PATH + SIMPLE_FILE;
		String strCapital = FILE_PATH + CAPITAL_FILE;
		String strNumber = FILE_PATH + NUMBER_FILE;
		String strSpecial = FILE_PATH + SPECIAL_FILE;
		
		cmdline = SORT_CMD + strSimple + " " + strCapital;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults1 = sortRead.readFromFile(FILE_PATH + SORT_SIMPLE_CAPITAL);
		assertEquals(expectedResults1, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		cmdline = SORT_CMD + strCapital + " " + strNumber + " " + strSpecial + " " + strSimple;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults2 = sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);
		assertEquals(expectedResults2, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		cmdline = SORT_CMD + strCapital + " " + strNumber + " " + strSpecial + " " + strSimple + " -n";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults3 = sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);
		assertEquals(expectedResults3, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		cmdline = SORT_CMD + strSimple + " " + strCapital + " -n " + strNumber ;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		String expectedResults4 = sortRead.readFromFile(FILE_PATH + SORT_SIMPLE_CAPITAL_NUMBER_N);
		assertEquals(expectedResults4, stdout.toString());
	}
	
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}

	

}
