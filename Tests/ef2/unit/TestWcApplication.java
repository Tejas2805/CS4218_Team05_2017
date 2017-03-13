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
	
	@Test
	public void testprintCharacterCountInFile() {
		String actual = wcApp.printCharacterCountInFile(data);
		assertEquals("104",actual);
		
	}
	@Test
	public void testprintWordCountInFile() {
		String actual = wcApp.printWordCountInFile(data);
		assertEquals("19",actual);
		
	}
	@Test
	public void testprintNewlineCountInFile() {
		String actual = wcApp.printNewlineCountInFile(data);
		assertEquals("15", actual);
		
	}
	@Test
	public void testprintAllCountsInFile() {
		String actual = wcApp.printAllCountsInFile(data);
		assertEquals("15 19 104", actual);
		
	}
	@Test
	public void testprintCharacterCountInStdin() {
		String actual = wcApp.printCharacterCountInStdin(data);
		assertEquals("104",actual);
		
	}
	@Test
	public void testprintWordCountInStdin() {
		String actual = wcApp.printWordCountInStdin(data);
		assertEquals("19",actual);
		
	}
	@Test
	public void testprintNewlineCountInStdin() {
		String actual = wcApp.printNewlineCountInStdin(data);
		assertEquals("15", actual);
		
	}
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
