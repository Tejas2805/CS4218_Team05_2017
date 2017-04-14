import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CommandSubstitutionBugFix {
	private final static String NEWLINE = System.lineSeparator();// "\n";

	// Reported Bug:
	// 4 Command substitution: Cat does not work with echo

	/*
	 * Fixed Bug No.4 Test for Bug No. 4 Command substitution of Cat and Echo
	 * will run correctly
	 */
	@Test
	public void testCommandSubstitutionCatAndEcho() {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		try {
			shell.parseAndEvaluate("cat `echo \"Tests/headFiles/123.txt\"`", outStream);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Head: File Not Exists");
		}

		String testStr = "31423" + NEWLINE + "1" + NEWLINE + "15ew" + NEWLINE + "afg" + NEWLINE + "gaqwtq345" + NEWLINE
				+ "tqtqt" + NEWLINE + "c592859v" + NEWLINE + "gasgsad";
		assertEquals(testStr, outStream.toString());
	}

}
