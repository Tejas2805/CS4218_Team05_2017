package bf.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationEcho {

	ShellImpl shellImpl;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
	}

	@Test
    public void testArgIsWord() throws AbstractApplicationException, ShellException{
		String input = "echo lol";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		assertEquals("lol"+System.lineSeparator() , stdout.toString());
    }
	
	@Test
    public void testArgIsWords() throws AbstractApplicationException, ShellException{
		String input = "echo lol pop";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		assertEquals("lol pop"+System.lineSeparator() , stdout.toString());
    }
	
	
	@Test
    public void testArgIsEmpty() throws AbstractApplicationException, ShellException{
		String input = "echo";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		assertEquals("" , stdout.toString());
    }
}
