

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestFixedBug {

	
	private final static String NEWLINE = System.lineSeparator();
	private final static String TESTMESSAGE = "test"+NEWLINE+"string";
	
	@Test
	public void testHeadNegativeLineValues() throws ShellException{
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String testStr = "31423" + NEWLINE + "1" + NEWLINE;

		try {
			shell.parseAndEvaluate("head -n -5 Tests/headFiles/123.txt ", stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("Head: Invalid Format"+NEWLINE+"Line Number must be non-negative value",actual);
		}
		
	}
	
	@Test
	public void testTailNegativeLineValues() throws ShellException{
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String testStr = "31423" + NEWLINE + "1" + NEWLINE;

		try {
			shell.parseAndEvaluate("tail -n -5 Tests/headFiles/123.txt ", stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual=e.getMessage();
			assertEquals("Tail: Invalid Format"+NEWLINE+"Line Number must be non-negative value",actual);
		}
		
	}

}
