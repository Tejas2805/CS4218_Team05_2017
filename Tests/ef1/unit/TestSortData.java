package ef1.unit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.sort.SortData;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortData {

	SortData sortData;
	SortRead sortRead;
	String filePath = "tests" + File.separator + "sortFiles" + File.separator;
	
	@Before
	public void setup(){
		sortData = new SortData();
		sortRead = new SortRead();
	}
	
	/*
	 * Test if the line starting with special char is retrieved
	 * sort.txt contains a file containing lines starting with special char, numbers, capital & simple 
	 * specialChar.txt contains only lines starting with special char
	 */
	@Test
	public void testGetSpecialCharData(){
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortData.getSpecialCharData(actualData).trim();
		
		String expectedFile = filePath + "specialChar.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * Test if the line starting with number is retrieved
	 * sort.txt contains a file containing lines starting with special char, numbers, capital & simple 
	 * number.txt contains only lines starting with number
	 */
	@Test
	public void testGetNumberData(){
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortData.getNumberData(actualData).trim();
		
		String expectedFile = filePath + "number.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * Test if the line starting with capital is retrieved
	 * sort.txt contains a file containing lines starting with special char, numbers, capital & simple 
	 * capital.txt contains only lines starting with capital char
	 */
	@Test
	public void testGetCaptialData(){
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortData.getCapitalData(actualData).trim();
		
		String expectedFile = filePath + "capital.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * Test if the line starting with simple char is retrieved
	 * sort.txt contains a file containing lines starting with special char, numbers, capital & simple 
	 * simple.txt contains only lines starting with simple char
	 */
	@Test
	public void testGetSimpleData(){
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortData.getSimpleData(actualData).trim();
		
		String expectedFile = filePath + "simple.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	@After
	public void tearDown(){
		sortData = null;
		sortRead = null;
	}

}
