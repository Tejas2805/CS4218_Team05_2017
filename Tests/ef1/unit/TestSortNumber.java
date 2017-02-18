package ef1.unit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.sort.SortNumber;
import sg.edu.nus.comp.cs4218.impl.app.sort.SortRead;

public class TestSortNumber {

	SortNumber sortNumber;
	SortRead sortRead;
	
	@Before
	public void setup(){
		sortNumber = new SortNumber();
		sortRead = new SortRead();
	}
	
	
	@Test
	public void testSortNumData() {
		String actualFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersUnordered.txt";
		String actualData = sortRead.readFromFile(actualFile);
		String actualResults = sortNumber.sortNumData(actualData);
		
		String expectedFile = "tests" + File.separator + "sortFiles" + File.separator + "sortNumbersOrdered.txt";
		String expectedResults = sortRead.readFromFile(expectedFile);
		
		assertEquals(expectedResults, actualResults);
	}
	
	@After
	public void tearDown(){
		sortNumber = null;
		sortRead = null;
	}

}
