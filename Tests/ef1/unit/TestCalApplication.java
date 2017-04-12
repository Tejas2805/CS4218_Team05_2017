package ef1.unit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
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
			List<String> lines = Files.readAllLines(Paths.get("Tests\\calFiles\\calTestOutput1.txt"));
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
			List<String> lines = Files.readAllLines(Paths.get("Tests\\calFiles\\calTestOutput2.txt"));
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
			List<String> lines = Files.readAllLines(Paths.get("Tests\\calFiles\\calTestOutput3.txt"));
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
			List<String> lines = Files.readAllLines(Paths.get("Tests\\calFiles\\calTestOutput4.txt"));
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
	
	@Test
	public void testInvalidDates()
	{
		String[] args1 = {"cal", "-3"};
		String[] args2 = {"cal", "-m", "-3"};
		String[] args3 = {"cal", "-m", "-3", "-12"};
		String[] args4 = {"cal", "-3", "-12"};
		OutputStream os = System.out;
		try {
			calApplication.run(args1, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		try {
			calApplication.run(args2, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		try {
			calApplication.run(args3, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		try {
			calApplication.run(args4, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		
	}
	@Test
	public void testMoreThanThreeArgs()
	{
		OutputStream os = System.out;
		String[] args1 = {"1", "2", "3", "4"};
		String[] args2 = {"1", "2", "3", "4", "5"};
		
		try {
			calApplication.run(args1, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		
		try {
			calApplication.run(args2, null, os);
			fail();
		} catch (AbstractApplicationException e) {
			
		}
		
	}
}
