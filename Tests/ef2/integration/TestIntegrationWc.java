package ef2.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.wc.WcCheckRead;

public class TestIntegrationWc {

	ShellImpl shellImpl;
	OutputStream stdout;
	WcCheckRead wcCheckRead;
	String filePath = "tests" + File.separator + "wcFiles" + File.separator;
	static final String WC_FILE = "wc.txt";
	static final String ONE_WORD_FILE = "1word.txt";
	static final String EMPTY_FILE = "empty.txt";
	static final String NO_NEWLINE_FILE = "noNewLine.txt";
	String data = "";
	
	@Before
    public void setUp() throws WcException {
		shellImpl = new ShellImpl();
		wcCheckRead = new WcCheckRead();
		data = wcCheckRead.readFileStdin(filePath + WC_FILE, null);
		stdout = new ByteArrayOutputStream();
	}
	
	
	
	@Test
	public void testEmptyFile() throws AbstractApplicationException, ShellException{
		String strArg = filePath + EMPTY_FILE;
		
		String cmdline = "wc " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);

		assertEquals("   0   0   0 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testNoNewlineFile() throws AbstractApplicationException, ShellException{
		String strArg = filePath + NO_NEWLINE_FILE;
		String cmdline = "wc " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   31   3   0 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testOneWordFile() throws AbstractApplicationException, ShellException{
		String strArg = filePath + ONE_WORD_FILE;
		String cmdline = "wc " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   47   1   0 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testInvalidOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + ONE_WORD_FILE;
		String cmdline = "wc -x " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   47   1   0 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testNewLineOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -l " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -w " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testCharOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -m " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionSplit() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -l -w " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -w -l " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionTogether() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -lw " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordOptionTogetherReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -wl " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionTogether() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -lm " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionTogetherReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -ml " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionSplit() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -l -m " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineCharOptionReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -m -l " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionTogether() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -wm " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionTogetherReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -mw " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionSplit() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -w -m " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testWordCharOptionReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -m -w " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordCharOptionTogether() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -lwm " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordCharOptionTogetherReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -mlw " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWOrdCharOptionSplit() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -l -w -m " + strArg;
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testLineWordCharOptionReorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -m -l " + strArg + " -w";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   10   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testMulitpleSameOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline1 = "wc -llllll " + strArg;
		shellImpl.parseAndEvaluate(cmdline1, stdout);
		assertEquals("   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String cmdline2 = "wc -wwwww " + strArg;
		shellImpl.parseAndEvaluate(cmdline2, stdout);
		assertEquals("   10 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String cmdline3 = "wc -mmmmm " + strArg;
		shellImpl.parseAndEvaluate(cmdline3, stdout);
		assertEquals("   65 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	
	@Test
	public void testMulitpleMixedOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
	
		String cmdline1 = "wc -lllwmmll " + strArg;
		shellImpl.parseAndEvaluate(cmdline1, stdout);
		assertEquals("   65   10   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String cmdline2 = "wc -wllwwwwllll " + strArg;
		shellImpl.parseAndEvaluate(cmdline2, stdout);
		assertEquals("   10   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String cmdline3 = "wc -mmmllllm " + strArg;
		shellImpl.parseAndEvaluate(cmdline3, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	@Test
	public void testMulitpleMixedOptionMulitpleParts() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
	
		String cmdline1 = "wc -lllwmmll " + strArg + " -mmmmmmlll";
		shellImpl.parseAndEvaluate(cmdline1, stdout);
		assertEquals("   65   10   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String cmdline2 = "wc -wllwwwwllll " + strArg + " -wwwwlllw";
		shellImpl.parseAndEvaluate(cmdline2, stdout);
		assertEquals("   10   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
		
		String cmdline3 = "wc -mmmllllm " + strArg + " -mmmlllll";
		shellImpl.parseAndEvaluate(cmdline3, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
		stdout = new ByteArrayOutputStream();
	}
	
	@Test
	public void testValidAndInvalidOption() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		String cmdline = "wc -lllmmll " + strArg + " -mmmnmmmllwl";
		shellImpl.parseAndEvaluate(cmdline, stdout);
		assertEquals("   65   12 " + strArg + System.lineSeparator(), stdout.toString());
	}
	
	@Test
	public void testMulitpleFile() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		
		stdout = new ByteArrayOutputStream();

				
		stdout = new ByteArrayOutputStream();
		String cmdline2 = "wc -m " + strArg + " " + strArg;
		shellImpl.parseAndEvaluate(cmdline2, stdout);
		String expectedResults2 = "   65 " + strArg + System.lineSeparator() + "   65 " + strArg + System.lineSeparator()
			+ "   130 total" + System.lineSeparator();
		assertEquals(expectedResults2, stdout.toString());
		
		stdout = new ByteArrayOutputStream();
		String cmdline3 = "wc -w " + strArg + " " + strArg;
		shellImpl.parseAndEvaluate(cmdline3, stdout);
		String expectedResults3 = "   10 " + strArg + System.lineSeparator() + "   10 " + strArg + System.lineSeparator()
			+ "   20 total" + System.lineSeparator();
		assertEquals(expectedResults3, stdout.toString());
		
	}
	
	@Test
	public void testMulitpleFileUnorder() throws AbstractApplicationException, ShellException{
		String strArg = filePath + WC_FILE;
		
		stdout = new ByteArrayOutputStream();
		String cmdline1 = "wc " + strArg + " -m " + strArg + " " + strArg;
		shellImpl.parseAndEvaluate(cmdline1, stdout);
		
		String expectedResults5 = "   65 " + strArg + System.lineSeparator() + "   65 " + strArg + System.lineSeparator()
		+ "   65 " + strArg + System.lineSeparator() + "   195 total" + System.lineSeparator();
		assertEquals(expectedResults5, stdout.toString());
	}
	
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}
	
}
