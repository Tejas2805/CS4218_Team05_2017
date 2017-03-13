package ef1.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortNumber;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortNumber {

	SortNumber sortNumber;
	SortRead sortRead;
	String filePath = "tests" + File.separator + "sortFiles" + File.separator;
	@Before
	public void setup(){
		sortNumber = new SortNumber();
		sortRead = new SortRead();
	}
	
	/*
	 * Test if the numbers are sorted in numerical order
	 * sortNumbersUnordered.txt contains the numbers arranged in random order
	 * sortNumbersOrdered.txt contains numbers sorted in numerical order
	 */
	@Test
	public void testSortNumData() throws SortException {
		String actualFile = filePath + "sortNumbersUnordered.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortNumber.sortNumData(actualData);
		
		String expectedFile = filePath + "sortNumbersOrdered.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * Test if the line extracted exclude first word of the line which is negative or positive number (e.g -1,0,1)
	 * sort.txt contains a file containing lines starting with special char, numbers, capital & simple 
	 * noNumber.txt contains lines which exclude first word of the line which is negative or positive number
	 */
	@Test
	public void testSortNotNumData() throws SortException {
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortNumber.sortNotNumData(actualData).trim();
		
		String expectedFile = filePath + "noNumber.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	@After
	public void tearDown(){
		sortNumber = null;
		sortRead = null;
	}

}
