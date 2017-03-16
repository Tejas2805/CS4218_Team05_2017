package ef2.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcCheckRead;

public class TestWcApplication {
	WcApplication wcApp;
	WcCheckRead wcCheckRead;
	String filePath = "tests" + File.separator + "wcFiles" + File.separator;
	static final String WC_FILE = "wc.txt";
	static final String ONE_WORD_FILE = "1word.txt";
	static final String EMPTY_FILE = "empty.txt";
	static final String NO_NEWLINE_FILE = "noNewLine.txt";
	String data = "";
	OutputStream stdout;
	InputStream stdin;


	
	@Before
	public void setup(){
		wcApp = new WcApplication();
		wcCheckRead = new WcCheckRead();
		data = wcCheckRead.readFileStdin(filePath + WC_FILE, null);
		stdout = new ByteArrayOutputStream();
	}
	
	@Test (expected = WcException.class)
	public void testStdoutNull() throws WcException{
		String [] args = {"no empty"};
		stdin = new ByteArrayInputStream("test".getBytes());
		wcApp.run(args, stdin, null);
		
	}
	
	@Test (expected = WcException.class)
	public void testArgsNullStdintNull() throws WcException{
		wcApp.run(null, null, stdout);
	}
	
	@Test (expected = WcException.class)
	public void testArgsEmptyStdintNull() throws WcException{
		String [] args = {};
		wcApp.run(args, null, stdout);
	}
	
	@Test
	public void testEmptyFile() throws WcException{
		String strArg = filePath + EMPTY_FILE;
		String [] args = {strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   0   0   0 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testNoNewlineFile() throws WcException{
		String strArg = filePath + NO_NEWLINE_FILE;
		String [] args = {strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   0   3   31 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testOneWordFile() throws WcException{
		String strArg = filePath + ONE_WORD_FILE;
		String [] args = {strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   0   1   47 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testInvalidOption() throws WcException{
		String strArg = filePath + ONE_WORD_FILE;
		String [] args = {"-x", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   0   1   47 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testNewLineOption() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-l", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordOption() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-w", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testCharOption() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-m", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionSplit() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-l", "-w", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-w", "-l", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionTogether() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-lw", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionTogetherReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-wl", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionTogether() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-lm", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionTogetherReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-ml", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionSplit() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-l", "-m", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-m", "-l", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionTogether() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-wm", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionTogetherReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-mw", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionSplit() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-w", "-m", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-m", "-w", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordCharOptionTogether() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-lwm", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordCharOptionTogetherReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-mlw", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWOrdCharOptionSplit() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-l", "-w", "-m", strArg};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordCharOptionReorder() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args = {"-m", "-l", strArg, "-w"};
		wcApp.run(args, null, stdout);
		assertEquals("   11   10   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testMulitpleSameOption() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args1 = {"-llllll", strArg};
		wcApp.run(args1, null, stdout);
		assertEquals("   11 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String [] args2 = {"-wwwww", strArg};
		wcApp.run(args2, null, stdout);
		assertEquals("   10 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String [] args3 = {"-mmmmm", strArg};
		wcApp.run(args3, null, stdout);
		assertEquals("   65 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	
	@Test
	public void testMulitpleMixedOption() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args1 = {"-lllwmmll", strArg};
		wcApp.run(args1, null, stdout);
		assertEquals("   11   10   65 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String [] args2 = {"-wllwwwwllll", strArg};
		wcApp.run(args2, null, stdout);
		assertEquals("   11   10 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String [] args3 = {"-mmmllllm", strArg};
		wcApp.run(args3, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	@Test
	public void testMulitpleMixedOptionMulitpleParts() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args1 = {"-lllwmmll", strArg, "-mmmmmmlll"};
		wcApp.run(args1, null, stdout);
		assertEquals("   11   10   65 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String [] args2 = {"-wllwwwwllll", strArg, "-wwwwlllw"};
		wcApp.run(args2, null, stdout);
		assertEquals("   11   10 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String [] args3 = {"-mmmllllm", strArg, "-mmmlllll"};
		wcApp.run(args3, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	@Test
	public void testValidAndInvalidOption() throws WcException{
		String strArg = filePath + WC_FILE;
		String [] args1 = {"-lllmmll", strArg, "-mmmnmmmllwl"};
		wcApp.run(args1, null, stdout);
		assertEquals("   11   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testMulitpleFile() throws WcException{
		String strArg = filePath + WC_FILE;
		
		stdout = new ByteArrayOutputStream();
		String [] args1 = {"-lwm", strArg, strArg};
		wcApp.run(args1, null, stdout);
		String expectedResults1 = "   11   10   65 " + strArg + System.lineSeparator() + "   11   10   65 " + strArg + System.lineSeparator()
			+ "   22   20   130 total" + System.lineSeparator();
		assertEquals(expectedResults1, stdout.toString());
				
		stdout = new ByteArrayOutputStream();
		String [] args2 = {"-l", strArg, strArg};
		wcApp.run(args2, null, stdout);
		String expectedResults2 = "   11 " + strArg + System.lineSeparator() + "   11 " + strArg + System.lineSeparator()
			+ "   22 total" + System.lineSeparator();
		assertEquals(expectedResults2, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		String [] args3 = {"-w", strArg, strArg};
		wcApp.run(args3, null, stdout);
		String expectedResults3 = "   10 " + strArg + System.lineSeparator() + "   10 " + strArg + System.lineSeparator()
			+ "   20 total" + System.lineSeparator();
		assertEquals(expectedResults3, stdout.toString());
	}
	
	@Test
	public void testMulitpleFileUnorder() throws WcException{
		String strArg = filePath + WC_FILE;
		
		stdout = new ByteArrayOutputStream();
		String [] args5 = {strArg, "-m", strArg, strArg};
		wcApp.run(args5, null, stdout);
		String expectedResults5 = "   65 " + strArg + System.lineSeparator() + "   65 " + strArg + System.lineSeparator()
		+ "   65 " + strArg + System.lineSeparator() + "   195 total" + System.lineSeparator();
		assertEquals(expectedResults5, stdout.toString());
	}
	
	@Test
	public void testEmptyStdin() throws WcException{
		stdin = new ByteArrayInputStream("".getBytes());
		wcApp.run(null, stdin, stdout);
		assertEquals("   0   0   0" + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testStdin() throws WcException{
		String inStream1 = "hello world" + System.lineSeparator();
		stdin = new ByteArrayInputStream(inStream1.getBytes());
		wcApp.run(null, stdin, stdout);
		assertEquals("   1   2   13" + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String inStream2 = "hello world";
		stdin = new ByteArrayInputStream(inStream2.getBytes());
		wcApp.run(null, stdin, stdout);
		assertEquals("   0   2   11" + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	/*
	 * Test that printCharacterCountInFile output the char count of the file
	 */
	@Test
	public void testprintCharacterCountInFile() {
		String actual = wcApp.printCharacterCountInFile(data);
		assertEquals("   65",actual);
		
	}
	/*
	 * Test that printWordCountInFile output the word count of the file
	 */
	@Test
	public void testprintWordCountInFile() {
		String actual = wcApp.printWordCountInFile(data);
		assertEquals("   10",actual);
		
	}
	
	/*
	 * Test that printNewlineCountInFile output the newline count of the file
	 */
	@Test
	public void testprintNewlineCountInFile() {
		String actual = wcApp.printNewlineCountInFile(data);
		assertEquals("   11", actual);
		
	}
	
	/*
	 * Test that printAllCountInFile output the newline, word & char count of the file
	 */
	@Test
	public void testprintAllCountsInFile() {
		String actual = wcApp.printAllCountsInFile(data);
		assertEquals("   11   10   65", actual);
		
	}
	
	/*
	 * Test that printCharacterCountInStdin output the char count of the file
	 */
	@Test
	public void testprintCharacterCountInStdin() {
		String actual = wcApp.printCharacterCountInStdin(data);
		assertEquals("   65",actual);
		
	}
	
	/*
	 * Test that printWordCountInStdin output the word count of the file
	 */
	@Test
	public void testprintWordCountInStdin() {
		String actual = wcApp.printWordCountInStdin(data);
		assertEquals("   10",actual);
		
	}
	
	/*
	 * Test that printNewlineCountInStdin output the newline count of the file
	 */
	@Test
	public void testprintNewlineCountInStdin() {
		String actual = wcApp.printNewlineCountInStdin(data);
		assertEquals("   11", actual);
		
	}
	
	/*
	 * Test that printAllCountInStdin output the newline, word & char count of the file
	 */
	@Test
	public void testprintAllCountsInStdin() {
		String actual = wcApp.printAllCountsInStdin(data);
		assertEquals("   11   10   65", actual);
	}
	
	@After
	public void tearDown(){
		wcApp = null;
		wcCheckRead = null;
	}
}
