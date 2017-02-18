package ef1.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.crypto.SealedObject;
import javax.sound.sampled.Line;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CdException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortApplication {

	SortApplication sortApp;
	SortRead sortRead;
	ByteArrayInputStream stdin;
	ByteArrayOutputStream stdout;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setup(){
		sortApp = new SortApplication();
		sortRead = new SortRead();
	}
	
	@Test
	public void testValidInputStreamArgs() throws SortException{
		String[] args = {};
		String testString = "read";
		stdin = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		stdout = new ByteArrayOutputStream();
		sortApp.run(args, stdin, stdout);
		
	}
	
	@Test
	public void testValidFileArgs() throws SortException{
		assertTrue(true);
	}
	
	@Test
	public void testSortStringsSimple(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortStringsSimple(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimple.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortStringsCapital(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortStringsCapital(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapital.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortNumbers(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortNumbers(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortNumbersWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortNumbers(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapital(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleCapital(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapital.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleNumbers(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleNumbers(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortSimpleNumbersWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleNumbers(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalNumbers(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortCapitalNumbers(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortCapitalNumbersWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortCapitalNumbers(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortCapitalSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortCapitalSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortNumbersSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortNumbersSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortNumbersSpecialCharsWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortNumbersSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersSpecialChars-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapitalNumber(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleCapitalNumber(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalNumbers.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortSimpleCapitalNumberWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleCapitalNumber(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalNumbers-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleCapitalSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleCapitalSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleCapitalSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testSortSimpleNumbersSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleNumbersSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbersSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortSimpleNumbersSpecialCharsWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortSimpleNumbersSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortSimpleNumbersSpecialChars-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test 
	public void testSortCapitalNumbersSpecialChars(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortCapitalNumbersSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbersSpecialChars.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test 
	public void testSortCapitalNumbersSpecialCharsWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortCapitalNumbersSpecialChars(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortCapitalNumbersSpecialChars-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void sortAll(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortAll(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void sortAllWithN(){
		String sortFilename = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String toSort = "-n" + System.lineSeparator() + sortFilename;
		String actualResults = sortApp.sortAll(toSort);
		
		String resultsFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll-n.txt";
		String expectedResults = sortRead.readFromFile(resultsFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@After
	public void tearDown(){
		sortApp = null;
	}

}
