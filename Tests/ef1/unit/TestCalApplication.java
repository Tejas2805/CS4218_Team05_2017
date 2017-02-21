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
			List<String> lines = Files.readAllLines(Paths.get("calTestOutput1.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForMonthYear("2\n2017\n");
		
		char[] arr1 = expected.toCharArray();
		char[] arr2 = actual.toCharArray();
		
		for(int i =0; i < arr1.length; i++)
		{
			char a = arr1[i];
			char b = arr2[i];
			int aa = a;
			int bb = b;
			if(a == b)
				System.out.println("" + a + " is " + b);
			else
				System.out.println("" + a + " not " + b);
		}
		assertEquals(expected.length(), actual.length());
		assertEquals(expected, actual);
	}

	@Test
	public void testPrintCalMon() {
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("calTestOutput2.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForMonthYearMondayFirst("-m\n2\n2017\n");
		
		char[] arr1 = expected.toCharArray();
		char[] arr2 = actual.toCharArray();
		
		for(int i =0; i < arr1.length; i++)
		{
			char a = arr1[i];
			char b = arr2[i];
			int aa = a;
			int bb = b;
			if(a == b)
				System.out.println("" + a + " is " + b);
			else
				System.out.println("" + a + " not " + b);
			//assertEquals(arr1[i], arr2[i]);
		}
		assertEquals(expected.length(), actual.length());
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testPrintCalYear() {
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("calTestOutput3.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForYear("2017\n");
		
		char[] arr1 = expected.toCharArray();
		char[] arr2 = actual.toCharArray();
		
		for(int i =0; i < arr1.length; i++)
		{
			char a = arr1[i];
			char b = arr2[i];
			int aa = a;
			int bb = b;
			if(a == b)
				System.out.println("" + a + " is " + b);
			else
				System.out.println("" + a + " not " + b);
			//assertEquals(arr1[i], arr2[i]);
		}
		assertEquals(expected.length(), actual.length());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPrintCalYearMon() {
		String expected = "";
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("calTestOutput4.txt"));
			expected = "";
			for(String line : lines)
				expected += line + "\n";
		} catch (IOException e) {
			fail();
		}
		String actual = calApplication.printCalForYearMondayFirst("-m\n2017\n");
		
		char[] arr1 = expected.toCharArray();
		char[] arr2 = actual.toCharArray();
		
		for(int i =0; i < arr1.length; i++)
		{
			char a = arr1[i];
			char b = arr2[i];
			int aa = a;
			int bb = b;
			if(a == b)
				System.out.println("" + a + " is " + b);
			else
				System.out.println("" + a + " not " + b);
			//assertEquals(arr1[i], arr2[i]);
		}
		assertEquals(expected.length(), actual.length());
		assertEquals(expected, actual);
	}
}
