package ef1.unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortOrder;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortOrder {

	SortOrder sortOrder;
	SortRead sortRead;
	String filePath = "tests" + File.separator + "sortFiles" + File.separator;
	@Before
	public void setup(){
		sortOrder = new SortOrder();
		sortRead = new SortRead();
	}
	
	/*
	 * Test that sortData(String) sort the line in the order of special char, numbers, capital letter & simple letters
	 * sort.txt contains line in random order
	 * sortAll.txt contains line sorted in the order of special char, numbers, capital letter & simple letters
	 */
	@Test
	public void testSortData() throws SortException {
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortOrder.sortData(actualData).trim();
		
		String expectedFile = filePath + "sortAll.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	/*
	 * Test that sortByAscii(String) sort the line in the order of special char, numbers, capital letter & simple letters
	 * each individual order(e.g. special chat, number) is sorted according to ascending ascii value
	 * sort.txt contains line in random order
	 * sortAll.txt contains line sorted in the order of special char, numbers, capital letter & simple letters
	 */
	@Test
	public void testSortByAscii() throws SortException {
		String actualFile = filePath + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String[] actualResults = (sortOrder.sortData(actualData).trim()).split(System.lineSeparator());
		ArrayList<String> actualArray = new ArrayList<String>();
		
		for(String actual : actualResults){
			actualArray.add(actual);
		}
		
		String expectedFile = filePath + "sortAll.txt";
		String[] expectedResults = (sortRead.readFromFile(expectedFile).trim()).split(System.lineSeparator());
		
		ArrayList<String> expectedArray = new ArrayList<String>();
		
		for(String expected : expectedResults){
			expectedArray.add(expected);
		}
		
		for(int i= 0; i<Math.min(actualArray.size(), expectedArray.size()); i++){
			assertEquals(expectedArray.get(i), actualArray.get(i));
		}
	}
	
	@After
	public void tearDown(){
		sortOrder = null;
		sortRead = null;
	}

}
