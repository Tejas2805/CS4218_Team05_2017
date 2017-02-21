package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

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

	@Before
	public void setup() throws SortException {
		sortApp = new SortApplication();
		sortRead = new SortRead();
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String data = sortRead.readFromFile(sortFilename);
		toSort = "" + System.lineSeparator() + data;
	}

	@Test
	public void testEmptyInputStreamArgs() throws SortException {
		String[] args = {};
		String data = "";
		stdin = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		
		thrown.expect(SortException.class);
		thrown.expectMessage("Input stream empty");
		
		sortApp.run(args, stdin, stdout);
	}


	@Test
	public void testSortStringsSimple() throws SortException {
		String actualResults = sortApp.sortStringsSimple(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimple.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortStringsCapital() throws SortException {
		String actualResults = sortApp.sortStringsCapital(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapital.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortNumbers() throws SortException {
		String actualResults = sortApp.sortNumbers(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortNumbersWithN() throws SortException {
		String actualResults = sortApp.sortNumbers("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile).trim();

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSpecialChars() throws SortException {
		String actualResults = sortApp.sortSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapital() throws SortException {
		String actualResults = sortApp.sortSimpleCapital(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapital.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleNumbers() throws SortException {
		String actualResults = sortApp.sortSimpleNumbers(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleNumbersWithN() throws SortException {
		String actualResults = sortApp.sortSimpleNumbers("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleSpecialChars() throws SortException {
		String actualResults = sortApp.sortSimpleSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalNumbers() throws SortException {
		String actualResults = sortApp.sortCapitalNumbers(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalNumbersWithN() throws SortException {
		String actualResults = sortApp.sortCapitalNumbers("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalSpecialChars() throws SortException {
		String actualResults = sortApp.sortCapitalSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortNumbersSpecialChars() throws SortException {
		String actualResults = sortApp.sortNumbersSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortNumbersSpecialCharsWithN() throws SortException {
		String actualResults = sortApp.sortNumbersSpecialChars("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersSpecialChars-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapitalNumber() throws SortException {
		String actualResults = sortApp.sortSimpleCapitalNumber(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapitalNumberWithN() throws SortException {
		String actualResults = sortApp.sortSimpleCapitalNumber("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapitalSpecialChars() throws SortException {
		String actualResults = sortApp.sortSimpleCapitalSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortSimpleCapitalSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleNumbersSpecialChars() throws SortException {
		String actualResults = sortApp.sortSimpleNumbersSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortSimpleNumbersSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleNumbersSpecialCharsWithN() throws SortException {
		String actualResults = sortApp.sortSimpleNumbersSpecialChars("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortSimpleNumbersSpecialChars-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalNumbersSpecialChars() throws SortException {
		String actualResults = sortApp.sortCapitalNumbersSpecialChars(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortCapitalNumbersSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalNumbersSpecialCharsWithN() throws SortException {
		String actualResults = sortApp.sortCapitalNumbersSpecialChars("-n" + toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator
				+ "sortCapitalNumbersSpecialChars-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void sortAll() throws SortException {
		String actualResults = sortApp.sortAll(toSort);

		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);

		assertEquals(expectedResults, actualResults);
	}

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
