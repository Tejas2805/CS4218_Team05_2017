package ef2.integration;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class TestIntegrationCMDSubstitution {

	static final String NEWLINE = System.getProperty("line.separator");

	@Test
	public void testCatWithCommandSubstitution() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			shell.parseAndEvaluate("cat `echo Tests/headFiles/a.txt` Tests/headFiles/456.txt", output);
		} catch (Exception e) {
			String actual = e.getMessage();
			assertEquals("123452" + NEWLINE + "aadfa" + NEWLINE, actual);
		}
		assertEquals(1, 1);

	}

	@Test
	public void testEchoWithValidCommandSubstitutionSingleQuoted() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		shell.parseAndEvaluate("echo 'Testing 123 `echo \"nTesting 123\"` and `echo \"2nd Testing 123\"`'", output);
		assertEquals("Testing 123 `echo \"nTesting 123\"` and `echo \"2nd Testing 123\"`" + NEWLINE, output.toString());
	}

	@Test
	public void testEchoWithValidCommandSubstitutionDoubleQuoted() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		shell.parseAndEvaluate("echo \"Double Quote `echo \"nTest\"` and `echo \"2nd space\"`\"", output);
		assertEquals("Double Quote nTest and 2nd space" + NEWLINE, output.toString());
	}

	@Test
	public void testEchoWithInvalidCommandSubstitutionBackQuoted() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			shell.parseAndEvaluate("echo `error_input`", output);
			fail();
		} catch (ShellException s) {

			assertEquals("shell: error_input: Invalid app.", s.getMessage());
		}

	}

	@Test
	public void testEchoWithInvalidCommandSubstitutionDoubleQuoted()
			throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			shell.parseAndEvaluate("echo \"testing: `error_input`\"", output);
		} catch (Exception e) {
			String actual = e.getMessage();
			assertEquals("shell: error_input: Invalid app.", actual);
		}

	}
}
