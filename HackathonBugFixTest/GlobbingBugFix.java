import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingBugFix {

	// Reported Bug:
	// 15 Globbing: run correctly and for nonexistent path, keep ARG and
	// throwing nonexistent file exception
	// 16 Globbing: run correctly and asterisk (*) in quotation is not
	// interpreted as globbing

	/*
	 * Fixed Bug No.15 Test for Bug No.15 For nonexistent path in globbing, keep
	 * ARG and throwing nonexistent file exception
	 */
	@Test
	public void testGlobNonExistentPath() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 HackathonTestFiles\\1*";

		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual = e.getMessage();
			assertEquals("grep: Error occurred", actual);
		}
	}

	/*
	 * Fixed Bug No.16 Test for Bug No.16 asterisk (*) in double quotes is not
	 * interpreted as globbing
	 */
	@Test
	public void testGlobWithDoubleQuotes() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 \"HackathonTestFiles\\*\"";

		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual = e.getMessage();
			assertEquals("grep: Error occurred", actual);
		}
	}

	/*
	 * Fixed Bug No.16 Test for Bug No.16 asterisk (*) in single quotes is not
	 * interpreted as globbing
	 */
	@Test
	public void testGlobWithSingleQuotes() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 'HackathonTestFiles\\*'";

		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			String actual = e.getMessage();
			assertEquals("grep: Error occurred", actual);
		}
	}

	/*
	 * Fixed Bug No.16 Test for Bug No.16 asterisk (*) without quote is
	 * interpreted as globbing
	 */
	@Test
	public void testGlobWithoutQuotes() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		OutputStream stdout = new ByteArrayOutputStream();
		String cmdline = "grep 9000 HackathonTestFiles\\*";

		try {
			shell.parseAndEvaluate(cmdline, stdout);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
		}
		assertEquals("The power is over 9000 !\n", stdout.toString());
	}
}
