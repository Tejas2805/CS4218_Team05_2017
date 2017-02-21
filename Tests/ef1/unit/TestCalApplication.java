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
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("ef1_test_cases\\cal\\calTestOutput1.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForMonthYear("2\n2017\n");
		
		
		assertEquals(expected, actual);
	}

	@Test
	public void testPrintCalMon() {
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("ef1_test_cases\\cal\\calTestOutput2.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForMonthYearMondayFirst("-m\n2\n2017\n");
	
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testPrintCalYear() {
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("ef1_test_cases\\cal\\calTestOutput3.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForYear("2017\n");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPrintCalYearMon() {
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("ef1_test_cases\\cal\\calTestOutput4.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForYearMondayFirst("-m\n2017\n");
		
		
		assertEquals(expected.length(), actual.length());
		assertEquals(expected, actual);
	}
}
