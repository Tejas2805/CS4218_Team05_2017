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
	
	@Before
	public void setup(){
		sortOrder = new SortOrder();
		sortRead = new SortRead();
	}
	
	@Test
	public void testSortData() throws SortException {
		String actualFile = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortOrder.sortData(actualData).trim();
		
		String expectedFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll.txt";
		String expectedResults = sortRead.readFromFile(expectedFile).trim();
		
		assertEquals(expectedResults, actualResults);
	}
	
	@Test
	public void testSortByAscii() throws SortException {
		String actualFile = "tests" + File.separator + "sortFiles" + File.separator + "sort.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String[] actualResults = (sortOrder.sortData(actualData).trim()).split(System.lineSeparator());
		ArrayList<String> actualArray = new ArrayList<String>();
		
		for(String actual : actualResults){
			actualArray.add(actual);
		}
		
		String expectedFile = "tests" + File.separator + "sortFiles" + File.separator + "sortAll.txt";
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
