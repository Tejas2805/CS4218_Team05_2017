package ef2.unit;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class TestSedApplication {
	
	SedApplication sedApp;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before 
	public void setup(){
		sedApp = new SedApplication();
	}
	
	@Test
	public void testReplaceFirstSubStringInFile() {
		String replacement = "s/unix/linux";
		String strFile = "unix is great os. unix is opensource. unix is free os. learn operating system";
		String args = replacement + System.lineSeparator() + strFile;
		String actualResults = sedApp.replaceFirstSubStringInFile(args);
		String expectedResults = "linux is great os. unix is opensource. unix is free os. learn operating system";
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testReplaceAllSubstringsInFile() {
		String replacement = "s/unix/linux/g";
		String strFile = "unix is great os. unix is opensource. unix is free os. learn operating system";
		String args = replacement + System.lineSeparator() + strFile;
		String actualResults = sedApp.replaceFirstSubStringInFile(args);
		String expectedResults = "linux is great os. linux is opensource. linux is free os. learn operating system";
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testReplaceFirstSubStringFromStdin() {
		String replacement = "s/unix/linux";
		String strFile = "unix is great os. unix is opensource. unix is free os. learn operating system";
		String args = replacement + System.lineSeparator() + strFile;
		String actualResults = sedApp.replaceFirstSubStringInFile(args);
		String expectedResults = "linux is great os. unix is opensource. unix is free os. learn operating system";
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testReplaceAllSubstringsInStdin() {
		String replacement = "s/unix/linux/g";
		String strFile = "unix is great os. unix is opensource. unix is free os. learn operating system";
		String args = replacement + System.lineSeparator() + strFile;
		String actualResults = sedApp.replaceFirstSubStringInFile(args);
		String expectedResults = "linux is great os. linux is opensource. linux is free os. learn operating system";
		
		assertEquals(expectedResults, actualResults);
	}

	@Test
	public void testReplaceSubstringWithInvalidReplacement() {
		String replacement = "s/un/ix/linux/g";
		String strFile = "unix is great os. unix is opensource. unix is free os. learn operating system";
		String args = replacement + System.lineSeparator() + strFile;
		
		thrown.expect(SedException.class);

		sedApp.replaceFirstSubStringInFile(args);
	}

	@Test
	public void testReplaceSubstringWithInvalidRegex() {
		String replacement = "s/unix/lin/ux/g";
		String strFile = "unix is great os. unix is opensource. unix is free os. learn operating system";
		String args = replacement + System.lineSeparator() + strFile;
		
		thrown.expect(SedException.class);

		sedApp.replaceFirstSubStringInFile(args);
		
	}
	
	
	@After
	public void tearDown(){
		sedApp = null;
	}

}
