package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

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
	ByteArrayInputStream stdin;
	ByteArrayOutputStream stdout;
	String toSort;

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
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String data = sortRead.readFromFile(sortFilename);
		toSort = "" + System.lineSeparator() + data;
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimple.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapital.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbers.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbers-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapital.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbers.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbers-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbers.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbers-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersSpecialChars-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalNumbers.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalNumbers-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortSimpleCapitalSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortSimpleNumbersSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortSimpleNumbersSpecialChars-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortCapitalNumbersSpecialChars.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortCapitalNumbersSpecialChars-n.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll.txt";
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

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@After
	public void tearDown() {
		sortApp = null;
	}

}
