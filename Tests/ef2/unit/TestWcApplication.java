package ef2.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.crypto.Data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.DateApplication;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcCheckRead;

public class TestWcApplication {
	WcApplication wcApp;
	WcCheckRead wcCheckRead;
	String filePath = "tests" + File.separator + "wcFiles" + File.separator;
	String fileName = "wc.txt";
	String data = "";
	
	@Before
	public void setup(){
		wcApp = new WcApplication();
		wcCheckRead = new WcCheckRead();
		data = wcCheckRead.readFromFileOrInputStream(filePath + fileName, null);
	}
	
	/*
	 * Test that printCharacterCountInFile output the char count of the file
	 */
	@Test
	public void testprintCharacterCountInFile() {
		String actual = wcApp.printCharacterCountInFile(data);
		assertEquals("104",actual);
		
	}
	/*
	 * Test that printWordCountInFile output the word count of the file
	 */
	@Test
	public void testprintWordCountInFile() {
		String actual = wcApp.printWordCountInFile(data);
		assertEquals("19",actual);
		
	}
	
	/*
	 * Test that printNewlineCountInFile output the newline count of the file
	 */
	@Test
	public void testprintNewlineCountInFile() {
		String actual = wcApp.printNewlineCountInFile(data);
		assertEquals("15", actual);
		
	}
	
	/*
	 * Test that printAllCountInFile output the newline, word & char count of the file
	 */
	@Test
	public void testprintAllCountsInFile() {
		String actual = wcApp.printAllCountsInFile(data);
		assertEquals("15 19 104", actual);
		
	}
	
	/*
	 * Test that printCharacterCountInStdin output the char count of the file
	 */
	@Test
	public void testprintCharacterCountInStdin() {
		String actual = wcApp.printCharacterCountInStdin(data);
		assertEquals("104",actual);
		
	}
	
	/*
	 * Test that printWordCountInStdin output the word count of the file
	 */
	@Test
	public void testprintWordCountInStdin() {
		String actual = wcApp.printWordCountInStdin(data);
		assertEquals("19",actual);
		
	}
	
	/*
	 * Test that printNewlineCountInStdin output the newline count of the file
	 */
	@Test
	public void testprintNewlineCountInStdin() {
		String actual = wcApp.printNewlineCountInStdin(data);
		assertEquals("15", actual);
		
	}
	
	/*
	 * Test that printAllCountInStdin output the newline, word & char count of the file
	 */
	@Test
	public void testprintAllCountsInStdin() {
		String actual = wcApp.printAllCountsInStdin(data);
		assertEquals("15 19 104", actual);
	}
	
	@After
	public void tearDown(){
		wcApp = null;
		wcCheckRead = null;
	}
}
