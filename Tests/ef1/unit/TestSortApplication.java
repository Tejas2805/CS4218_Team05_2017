package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortApplication {

	SortApplication sortApp;
	SortRead sortRead;
	OutputStream stdout;
	InputStream stdin;
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
	

	static final String FILE_PATH = "tests" + File.separator + "sortFiles" + File.separator;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/*
	 * Set the "toSort" string data for which contains all the lines in "sort.txt"
	 * Each line is separated by a file separator
	 */
	@Before
	public void setup() throws SortException {
		sortApp = new SortApplication();
		sortRead = new SortRead();
		String sortFilename = FILE_PATH + UNSORTED_FILE;
		String data = sortRead.readFromFile(sortFilename);
		toSort = "" + System.lineSeparator() + data;
		stdout = new ByteArrayOutputStream();
	}

	@Test (expected = SortException.class)
	public void testStdoutNull() throws SortException{
		String [] args = {"no empty"};
		stdin = new ByteArrayInputStream("test".getBytes());
		sortApp.run(args, stdin, null);
	}
	
	@Test (expected = SortException.class)
	public void testArgsNullStdintNull() throws SortException{
		sortApp.run(null, null, stdout);
	}
	
	@Test (expected = SortException.class)
	public void testArgsEmptyStdintNull() throws SortException{
		String [] args = {};
		sortApp.run(args, null, stdout);
	}
	
	@Test
	public void testOneArg() throws SortException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		String[] args= {strArgs};
		sortApp.run(args, null, stdout);
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testOneArgWithN() throws SortException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		String[] args= {"-n", strArgs};
		sortApp.run(args, null, stdout);
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testInvalidOptionValidFile() throws SortException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		String[] args= {"-nn", strArgs};
		sortApp.run(args, null, stdout);
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testValidAndInvalidOptionValidFile() throws SortException{
		
		String strArgs = FILE_PATH + UNSORTED_FILE;
		String[] args= {"-m", strArgs, "-n"};
		sortApp.run(args, null, stdout);
		String expectedResults = sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);

		assertEquals(expectedResults, stdout.toString());
	}
	
	@Test
	public void testSortMultipleFiles() throws SortException{
		
		String strSimple = FILE_PATH + SIMPLE_FILE;
		String strCapital = FILE_PATH + CAPITAL_FILE;
		String strNumber = FILE_PATH + NUMBER_FILE;
		String strSpecial = FILE_PATH + SPECIAL_FILE;
		
		String[] args1 = {strSimple, strCapital};
		sortApp.run(args1, null, stdout);
		String expectedResults1 = sortRead.readFromFile(FILE_PATH + SORT_SIMPLE_CAPITAL);
		assertEquals(expectedResults1, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		String[] args2 = {strCapital, strNumber, strSpecial, strSimple};
		sortApp.run(args2, null, stdout);
		String expectedResults2 = sortRead.readFromFile(FILE_PATH + SORT_ALL_FILE);
		assertEquals(expectedResults2, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		String[] args3 = {strCapital, strNumber, strSpecial, strSimple, "-n"};
		sortApp.run(args3, null, stdout);
		String expectedResults3 = sortRead.readFromFile(FILE_PATH + SORT_ALL_N_FILE);
		assertEquals(expectedResults3, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		String[] args4 = {strSimple, strCapital, "-n", strNumber,};
		sortApp.run(args4, null, stdout);
		String expectedResults4 = sortRead.readFromFile(FILE_PATH + SORT_SIMPLE_CAPITAL_NUMBER_N);
		assertEquals(expectedResults4, stdout.toString());
	}
	
	
	
	/*
	 * Throw an an error message "Input stream null" if inputstream is empty
	 */
	@Test
	public void testEmptyInputStreamArgs() throws SortException {
		String[] args = {};
		stdin = null;//new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		
		thrown.expect(SortException.class);
		thrown.expectMessage("Input stream null");
		
		sortApp.run(args, stdin, stdout);
	}

	/*
	 * Test that sortStringsSimple return a string of lines starting with simple letters sorted in ascending order
	 * sortSimple.txt contains the actual results for comparison
	 */
	@Test
	public void testSortStringsSimple() throws SortException {
		String actualResults = sortApp.sortStringsSimple(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortStringsCapital return a string of lines starting with capital letters sorted in ascending order
	 * sortCapital.txt contains the actual results for comparison
	 */
	@Test
	public void testSortStringsCapital() throws SortException {
		String actualResults = sortApp.sortStringsCapital(toSort);

		String resultsFile = FILE_PATH + SORT_CAPITAL_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortNumbers return a string of lines starting with numbers sorted in ascii value order
	 * sortNumbers.txt contains the actual results for comparison
	 */
	@Test
	public void testSortNumbers() throws SortException {
		String actualResults = sortApp.sortNumbers(toSort);

		String resultsFile = FILE_PATH + SORT_NUMBER_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortNumbers return a string of lines starting with numbers sorted in numerical order
	 * "-n" condition is specific for sorting in numerical order
	 * sortNumbers-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortNumbersWithN() throws SortException {
		String actualResults = sortApp.sortNumbers("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_NUMBER_N_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile).trim();

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSpecialChars return a string of lines starting with special chars sorted in ascii value order
	 * sortSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSpecialChars() throws SortException {
		String actualResults = sortApp.sortSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_SPECIAL_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleCapital return a string of lines starting with capital & simple letter sorted in the order of captial, simple
	 * sortSimpleCapital.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleCapital() throws SortException {
		String actualResults = sortApp.sortSimpleCapital(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_CAPITAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleNumbers return a string of lines starting with Numbers & Simple letter sorted in the order of Numbers, simple
	 * sortSimpleNumbers.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleNumbers() throws SortException {
		String actualResults = sortApp.sortSimpleNumbers(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_NUMBER;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleNumbers return a string of lines starting with Numbers & Simple letter sorted in the order of Numbers, simple
	 * "-n" condition is specific for sorting in numerical order
	 * sortSimpleNumbers-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleNumbersWithN() throws SortException {
		String actualResults = sortApp.sortSimpleNumbers("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_NUMBER_N;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleSpecialChars return a string of lines starting with Special chars & Simple letter 
	 * sorted in the order of Special Chars, simple
	 * sortSimpleSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleSpecialChars() throws SortException {
		String actualResults = sortApp.sortSimpleSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_SPECIAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortCapitalNumbers return a string of lines starting with Numbers & Capital letters
	 * sorted in the order of numbers, capital letters
	 * sortCapitalNumbers.txt contains the actual results for comparison
	 */
	@Test
	public void testSortCapitalNumbers() throws SortException {
		String actualResults = sortApp.sortCapitalNumbers(toSort);

		String resultsFile = FILE_PATH + SORT_CAPITAL_NUMBER;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortCapitalNumbers return a string of lines starting with Numbers & Capital letters
	 * sorted in the order of numbers, capital letters
	 * "-n" condition is specific for sorting in numerical order
	 * sortCapitalNumbers-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortCapitalNumbersWithN() throws SortException {
		String actualResults = sortApp.sortCapitalNumbers("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_CAPITAL_NUMBER_N;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortCapitalSpecialChars return a string of lines starting with Special Chars & Capital letters
	 * sorted in the order of special chars, capital letters
	 * sortCapitalSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortCapitalSpecialChars() throws SortException {
		String actualResults = sortApp.sortCapitalSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_CAPITAL_SPECIAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortNumbersSpecialChars return a string of lines starting with Special Chars & numbers
	 * sorted in the order of special chars, numbers
	 * sortNumbersSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortNumbersSpecialChars() throws SortException {
		String actualResults = sortApp.sortNumbersSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_NUMBER_SPECIAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortNumbersSpecialChars return a string of lines starting with Special Chars & numbers
	 * sorted in the order of special chars, numbers
	 * "-n" condition is specific for sorting in numerical order
	 * sortNumbersSpecialChars-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortNumbersSpecialCharsWithN() throws SortException {
		String actualResults = sortApp.sortNumbersSpecialChars("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_NUMBER_SPECIAL_N;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleCapitalNumbers return a string of lines starting with Numbers, capital, simple letters
	 * sorted in the order of numbers, capital, simple letters
	 * sortSimpleCaptialNumbers.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleCapitalNumber() throws SortException {
		String actualResults = sortApp.sortSimpleCapitalNumber(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_CAPITAL_NUMBER;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleCapitalNumbers return a string of lines starting with Numbers, capital, simple letters
	 * sorted in the order of numbers, capital, simple letters
	 * "-n" condition is specific for sorting in numerical order
	 * sortSimpleCaptialNumbers-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleCapitalNumberWithN() throws SortException {
		String actualResults = sortApp.sortSimpleCapitalNumber("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_CAPITAL_NUMBER_N;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleCapitalSpecialChars return a string of lines starting with Special Chars, capital, simple letters
	 * sorted in the order of special chars, capital, simple letters
	 * sortSimpleCaptialSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleCapitalSpecialChars() throws SortException {
		String actualResults = sortApp.sortSimpleCapitalSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_CAPITAL_SPECIAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortSimpleNumbersSpecialChars return a string of lines starting with Special Chars, Numbers, simple letters
	 * sorted in the order of special chars, numbers, simple letters
	 * sortSimpleNumbersSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleNumbersSpecialChars() throws SortException {
		String actualResults = sortApp.sortSimpleNumbersSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_NUMBER_SPECIAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * Test that sortSimpleNumbersSpecialChars return a string of lines starting with Special Chars, Numbers, simple letters
	 * sorted in the order of special chars, numbers, simple letters
	 * "-n" condition is specific for sorting in numerical order
	 * sortSimpleNumbersSpecialChars-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortSimpleNumbersSpecialCharsWithN() throws SortException {
		String actualResults = sortApp.sortSimpleNumbersSpecialChars("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_SIMPLE_NUMBER_SPECIAL_N;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortCapitalNumbersSpecialChars return a string of lines starting with Special Chars, Numbers, capital letters
	 * sorted in the order of special chars, numbers, capital letters
	 * sortCapitalNumbersSpecialChars.txt contains the actual results for comparison
	 */
	@Test
	public void testSortCapitalNumbersSpecialChars() throws SortException {
		String actualResults = sortApp.sortCapitalNumbersSpecialChars(toSort);

		String resultsFile = FILE_PATH + SORT_CAPITAL_NUMBER_SPECIAL;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortCapitalNumbersSpecialChars return a string of lines starting with Special Chars, Numbers, capital letters
	 * sorted in the order of special chars, numbers, capital letters
	 * "-n" condition is specific for sorting in numerical order
	 * sortCapitalNumbersSpecialChars-n.txt contains the actual results for comparison
	 */
	@Test
	public void testSortCapitalNumbersSpecialCharsWithN() throws SortException {
		String actualResults = sortApp.sortCapitalNumbersSpecialChars("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_CAPITAL_NUMBER_SPECIAL_N;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortAll return a string of lines starting with Special Chars, Numbers, capital & simple letters
	 * sorted in the order of special chars, numbers, capital & simple letters
	 * sortAll.txt contains the actual results for comparison
	 */
	@Test
	public void sortAll() throws SortException {
		String actualResults = sortApp.sortAll(toSort);

		String resultsFile = FILE_PATH + SORT_ALL_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	/*
	 * Test that sortAll return a string of lines starting with Special Chars, Numbers, capital & simple letters
	 * sorted in the order of special chars, numbers, capital & simple letters
	 * "-n" condition is specific for sorting in numerical order
	 * sortAll-n.txt contains the actual results for comparison
	 */
	@Test
	public void sortAllWithN() throws SortException {
		String actualResults = sortApp.sortAll("-n" + toSort);

		String resultsFile = FILE_PATH + SORT_ALL_N_FILE;
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@After
	public void tearDown() {
		sortApp = null;
	}

}
