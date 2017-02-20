package ef2.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.DateApplication;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class TestWcApplication {
	WcApplication wcApp;

	@Before
	public void setup(){
		wcApp = new WcApplication();
		try{
		    PrintWriter writer = new PrintWriter("test.txt", "UTF-8");
		    writer.println("The first line");
		    writer.println("The second line");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	@Test
	public void testprintCharacterCountInFile() {
		String actual = wcApp.printCharacterCountInFile("-m test.txt");
		assertEquals(actual,"25");
		
	}
	@Test
	public void testprintWordCountInFile() {
		String actual = wcApp.printCharacterCountInFile("-w test.txt");
		assertEquals(actual,"6");
		
	}
	public void testprintNewlineCountInFile() {
		String actual = wcApp.printCharacterCountInFile("-l test.txt");
		assertEquals(actual,"2");
		
	}
	public void testprintAllCountsInFile() {
		String actual = wcApp.printCharacterCountInFile("test.txt");
		assertEquals(actual,"58 6 2");
		
	}
	public void testprintCharacterCountInStdin() {
		String actual = wcApp.printCharacterCountInFile("-m");
		assertEquals(actual,"25");
		
	}
	public void testprintWordCountInStdin() {
		String actual = wcApp.printCharacterCountInFile("-w");
		assertEquals(actual,"6");
		
	}
	public void testprintNewlineCountInStdin() {
		String actual = wcApp.printCharacterCountInFile("-l");
		assertEquals(actual,"2");
		
	}
	public void testprintAllCountsInStdin() {
		String actual = wcApp.printCharacterCountInFile("");
		assertEquals(actual,"58 6 2");
		
	}
}
