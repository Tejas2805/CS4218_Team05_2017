package bf.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.EchoException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationCat {

	ShellImpl shellImpl;
	
	@Before
    public void setUp() {
		shellImpl = new ShellImpl();
	}

	@Test
	public void testArgumentIsValid() throws AbstractApplicationException, ShellException {
		String input = "cat Tests\\catFiles\\catTestSource1.txt";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		String expected = "hello world" + System.lineSeparator();
		
		assertEquals(expected, stdout.toString());
	}

}
