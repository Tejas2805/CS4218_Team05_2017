package ef1.unit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;

public class TestCalApplication {

	static CalApplication calApplication;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		calApplication = new CalApplication();
	}

	@Test
	public void testPrintCal() {
		String expected = "    February 2017    \nSu Mo Tu We Th Fr Sa\n         1  2  3  4  \n5  6  7  8  9  10 11 \n12 13 14 15 16 17 18 \n19 20 21 22 23 24 25 \n26 27 28             \n";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("calTestOutput1.txt"));
			expected = "";
			for(String line : lines)
				expected += line + System.lineSeparator();
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForMonthYear("2\n2017\n");
		assertEquals(expected, actual);
	}

}
