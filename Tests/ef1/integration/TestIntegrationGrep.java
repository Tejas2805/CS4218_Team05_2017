package ef1.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationGrep {

	ShellImpl shellImpl;

	@Before
	public void setUp() {
		shellImpl = new ShellImpl();
	}

	@Test
	public void testRunWithOnlyOneFile() throws AbstractApplicationException, ShellException {
		String input = "grep pattern Tests\\grepFiles\\grepTestSource1.txt";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		String expected = "pattern\n";

		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testRunWithMultipleFiles() throws AbstractApplicationException, ShellException {
		String input = "grep pattern Tests\\grepFiles\\grepTestSource1.txt Tests\\grepFiles\\grepTestSource2.txt";
		OutputStream stdout = new ByteArrayOutputStream();
		shellImpl.parseAndEvaluate(input, stdout);
		String expected = "pattern\npattern\n";

		assertEquals(expected, stdout.toString());
	}
}
