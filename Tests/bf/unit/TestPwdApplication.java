package bf.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;
import sg.edu.nus.comp.cs4218.impl.app.PwdApplication;

public class TestPwdApplication {

	PwdApplication pwdApp;
	ByteArrayOutputStream stdout;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		pwdApp = new PwdApplication();
		stdout = new ByteArrayOutputStream();
	}

	/*
	 * Test that "pwd" should print out the current directory with a newline
	 * "pwd" will not take in an argument
	 */
	@Test
	public void testPwdWithNoArg() throws PwdException {
		pwdApp.run(new String[] {}, null, stdout);
		assertEquals(Environment.currentDirectory + System.lineSeparator(), stdout.toString());
	}

	/*
	 * Test that "pwd args" should throw an error message "pwd" will not take in
	 * an argument
	 */
	@Test
	public void testPwdWithArg() throws PwdException {
		thrown.expect(PwdException.class);
		thrown.expectMessage("Pwd: Invalid Arguments");
		pwdApp.run(new String[] { "test.txt" }, null, stdout);
	}

	@After
	public void tearDown() {
		pwdApp = null;
	}
}
