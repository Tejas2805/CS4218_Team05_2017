package bf.integration;

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

public class TestIntegrationPwd {

	ShellImpl shellImpl;
	OutputStream stdout;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
	}
	
	/*
	 * Test that "pwd" should print out the current directory with a newline
	 * "pwd" will not take in an argument
	 */
	@Test
    public void testPwdWithNoArg() throws AbstractApplicationException, ShellException{
		String input = "pwd";
		stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		assertEquals(Environment.currentDirectory + System.lineSeparator(), stdout.toString());
    }
	
	/*
	 * Test that "pwd args" should throw an error message
	 * "pwd" will not take in an argument
	 */
	@Test (expected =  AbstractApplicationException.class)
    public void testPwdWithArg() throws AbstractApplicationException, ShellException{
		String input = "pwd test";
		stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
    }
	
	@After
	public void tearDown() throws Exception {
		shellImpl = null;
	}

}
